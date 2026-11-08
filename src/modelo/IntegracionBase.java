package modelo;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public abstract class IntegracionBase implements IntegracionApi {
    private final String id;
    private String nombre;
    private String proveedor;
    private double latenciaPromedioMs;
    private EstadoConexion estadoConexion;
    private final Map<String, String> parametros;

    protected IntegracionBase(String id, String nombre, String proveedor,
            double latencia, EstadoConexion estado, Map<String, String> parametros) {
        this.id = texto(id, "Identificador");
        setNombre(nombre);
        setProveedor(proveedor);
        setLatenciaPromedioMs(latencia);
        setEstadoConexion(estado);
        Objects.requireNonNull(parametros, "Parámetros requeridos");
        Map<String, String> copia = new LinkedHashMap<>();
        parametros.forEach((clave, valor) ->
                copia.put(texto(clave, "Clave"), texto(valor, "Valor")));
        this.parametros = Collections.unmodifiableMap(copia);
    }

    @Override public final String getId() { return id; }
    @Override public String getNombre() { return nombre; }
    @Override public String getProveedor() { return proveedor; }
    @Override public double getLatenciaPromedioMs() { return latenciaPromedioMs; }
    @Override public EstadoConexion getEstadoConexion() { return estadoConexion; }
    @Override public Map<String, String> getParametrosConfiguracion() { return parametros; }

    public void setNombre(String nombre) { this.nombre = texto(nombre, "Nombre"); }
    
    public void setProveedor(String proveedor) { this.proveedor = texto(proveedor, "Proveedor"); }
    
    public void setLatenciaPromedioMs(double latencia) {
        if (!Double.isFinite(latencia) || latencia < 0) {
            throw new IllegalArgumentException("Latencia inválida.");
        }
        latenciaPromedioMs = latencia;
    }
    
    public void setEstadoConexion(EstadoConexion estado) {
        estadoConexion = Objects.requireNonNull(estado, "Estado requerido");
    }

    protected static String texto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " es obligatorio.");
        }
        return valor.strip();
    }
    
    protected static double positivo(double valor) {
        if (!Double.isFinite(valor) || valor <= 0) {
            throw new IllegalArgumentException("El valor debe ser positivo y finito.");
        }
        return valor;
    }
    
    protected void verificarDisponible() {
        if (estadoConexion == EstadoConexion.INACTIVA) {
            throw new IllegalStateException("Integración inactiva: " + nombre);
        }
    }
    
    protected static String xml(String valor) {
        String limpio = texto(valor, "Referencia");
        boolean valido = limpio.codePoints().allMatch(c -> c == 9 || c == 10 || c == 13
                || (c >= 32 && c <= 0xD7FF) || (c >= 0xE000 && c <= 0xFFFD)
                || (c >= 0x10000 && c <= 0x10FFFF));
        if (!valido) {
            throw new IllegalArgumentException("Texto no válido para XML.");
        }
        return limpio.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&apos;");
    }
    @Override public int compareTo(IntegracionApi otra) {
        int orden = Double.compare(latenciaPromedioMs, otra.getLatenciaPromedioMs());
        return orden == 0 ? id.compareTo(otra.getId()) : orden;
    }
    @Override public final boolean equals(Object otro) {
        return this == otro || otro instanceof IntegracionBase base && id.equals(base.id);
    }
    @Override public final int hashCode() { return id.hashCode(); }
    @Override public String toString() {
        return String.format(Locale.ROOT, "%s | %s | %s | %.2f ms | %s",
                id, nombre, proveedor, latenciaPromedioMs, estadoConexion);
    }
}

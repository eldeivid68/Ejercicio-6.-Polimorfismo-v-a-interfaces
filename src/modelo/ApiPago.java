package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class ApiPago extends IntegracionBase implements Transaccional, Auditable {
    private final List<String> bitacora = new ArrayList<>();

    protected ApiPago(String id, String nombre, String proveedor, double latencia,
            EstadoConexion estado, Map<String, String> parametros) {
        super(id, nombre, proveedor, latencia, estado, parametros);
    }
    @Override public String cobrar(double monto) { return cobrar(monto, "GTQ"); }
    @Override public abstract String cobrar(double monto, String moneda);
    @Override public String reintegrar(double monto) {
        verificarDisponible();
        positivo(monto);
        String resultado = String.format(Locale.ROOT,
                "Reintegro simulado por %s: %.2f GTQ.", getNombre(), monto);
        registrarEvento(resultado);
        return resultado;
    }
    
    protected String validarMoneda(String moneda) {
        String codigo = texto(moneda, "Moneda").toUpperCase(Locale.ROOT);
        if (!codigo.equals("GTQ") && !codigo.equals("USD")) {
            throw new IllegalArgumentException("Moneda no admitida; use GTQ o USD.");
        }
        return codigo;
    }
    @Override public void registrarEvento(String evento) {
        bitacora.add(LocalDateTime.now() + " | " + texto(evento, "Evento"));
    }
    @Override public List<String> getBitacora() {
        return Collections.unmodifiableList(bitacora);
    }
}

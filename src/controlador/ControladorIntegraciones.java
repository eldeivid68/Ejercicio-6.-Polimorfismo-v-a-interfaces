package controlador;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import modelo.Consultable;
import modelo.Documentable;
import modelo.EstadoConexion;
import modelo.IntegracionApi;
import modelo.RepositorioIntegraciones;
import modelo.Transaccional;

public class ControladorIntegraciones {
    private final RepositorioIntegraciones repositorio;

    public ControladorIntegraciones(RepositorioIntegraciones repositorio) {
        this.repositorio = Objects.requireNonNull(repositorio, "Repositorio requerido");
    }
    
    public List<IntegracionApi> listar() { return repositorio.getTodas(); }
    
    public Optional<IntegracionApi> buscarPorId(String id) { return repositorio.buscarPorId(id); }
    
    public void ordenarPorLatencia() { repositorio.ordenarPorLatencia(); }

    public String procesarCobro(IntegracionApi api, double monto) {
        String rechazo = verificar(api);
        if (rechazo != null) { return rechazo; }
        if (api instanceof Transaccional transaccional) {
            return ejecutar(() -> transaccional.cobrar(monto));
        }
        return noSoportada(api);
    }
    
    public String procesarCobro(IntegracionApi api, double monto, String moneda) {
        String rechazo = verificar(api);
        if (rechazo != null) { return rechazo; }
        if (api instanceof Transaccional transaccional) {
            return ejecutar(() -> transaccional.cobrar(monto, moneda));
        }
        return noSoportada(api);
    }
    
    public String procesarReintegro(IntegracionApi api, double monto) {
        String rechazo = verificar(api);
        if (rechazo != null) { return rechazo; }
        if (api instanceof Transaccional transaccional) {
            return ejecutar(() -> transaccional.reintegrar(monto));
        }
        return noSoportada(api);
    }
    
    public String generarDocumento(IntegracionApi api, String referencia) {
        String rechazo = verificar(api);
        if (rechazo != null) { return rechazo; }
        if (api instanceof Documentable documentable) {
            return ejecutar(() -> documentable.generarDocumento(referencia));
        }
        return noSoportada(api);
    }
    
    public String consultar(IntegracionApi api, String referencia) {
        String rechazo = verificar(api);
        if (rechazo != null) { return rechazo; }
        if (api instanceof Consultable consultable) {
            return ejecutar(() -> referencia == null || referencia.isBlank()
                    ? consultable.consultarEstado() : consultable.consultarEstado(referencia));
        }
        return noSoportada(api);
    }
    
    private String verificar(IntegracionApi api) {
        if (api == null) { return "API inexistente: operación ignorada."; }
        if (api.getEstadoConexion() == EstadoConexion.INACTIVA) {
            return "Operación rechazada: " + api.getNombre() + " está INACTIVA.";
        }
        return null;
    }
    
    private String noSoportada(IntegracionApi api) {
        return "Operación no soportada por " + api.getNombre() + ": ignorada";
    }
    
    private String ejecutar(Supplier<String> operacion) {
        try {
            return operacion.get();
        } catch (IllegalArgumentException | IllegalStateException excepcion) {
            return "Operación rechazada: " + excepcion.getMessage();
        }
    }
    @Override public String toString() { return "ControladorIntegraciones{" + repositorio + "}"; }
}

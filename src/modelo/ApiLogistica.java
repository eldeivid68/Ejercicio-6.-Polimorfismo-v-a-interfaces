package modelo;

import java.util.Map;

public abstract class ApiLogistica extends IntegracionBase implements Consultable, Documentable {
    
    protected ApiLogistica(String id, String nombre, String proveedor, double latencia,
            EstadoConexion estado, Map<String, String> parametros) {
        super(id, nombre, proveedor, latencia, estado, parametros);
    }
    @Override public String consultarEstado() {
        verificarDisponible();
        return getNombre() + ": servicio " + getEstadoConexion();
    }
    @Override public String generarDocumento(String referencia) {
        verificarDisponible();
        return "<guia simulada=\"true\" integracion=\"" + xml(getId())
                + "\" servicio=\"" + xml(getNombre()) + "\" referencia=\""
                + xml(referencia) + "\"/>";
    }
}

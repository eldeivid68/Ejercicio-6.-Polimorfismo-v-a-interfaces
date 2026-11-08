package modelo;

import java.util.Map;

public interface IntegracionApi extends Comparable<IntegracionApi> {

    String getId();
    
    String getNombre();
    
    String getProveedor();
    
    double getLatenciaPromedioMs();
    
    EstadoConexion getEstadoConexion();
    
    Map<String, String> getParametrosConfiguracion();
}

package modelo;

import java.util.List;

public interface Auditable {

    void registrarEvento(String evento);
    
    List<String> getBitacora();
}

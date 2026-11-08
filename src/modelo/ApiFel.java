package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class ApiFel extends IntegracionBase implements Documentable, Auditable {
    private final List<String> bitacora = new ArrayList<>();

    protected ApiFel(String id, String nombre, String proveedor, double latencia,
            EstadoConexion estado, Map<String, String> parametros) {
        super(id, nombre, proveedor, latencia, estado, parametros);
    }
    @Override public void registrarEvento(String evento) {
        bitacora.add(LocalDateTime.now() + " | " + texto(evento, "Evento"));
    }
    @Override public List<String> getBitacora() {
        return Collections.unmodifiableList(bitacora);
    }
}

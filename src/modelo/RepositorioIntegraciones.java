package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RepositorioIntegraciones {
    private final List<IntegracionApi> integraciones = new ArrayList<>();

    public void agregar(IntegracionApi api) {
        Objects.requireNonNull(api, "Integración requerida");
        if (api.getId() == null || api.getId().isBlank()) {
            throw new IllegalArgumentException("Identificador requerido.");
        }
        if (buscarPorId(api.getId()).isPresent()) {
            throw new IllegalArgumentException("Identificador repetido: " + api.getId());
        }
        integraciones.add(api);
    }
    
    public List<IntegracionApi> getTodas() {
        return Collections.unmodifiableList(integraciones);
    }
    
    public Optional<IntegracionApi> buscarPorId(String id) {
        if (id == null) { return Optional.empty(); }
        return integraciones.stream().filter(api -> api.getId().equals(id.strip())).findFirst();
    }
    
    public void ordenarPorLatencia() { Collections.sort(integraciones); }
    @Override public String toString() {
        return "RepositorioIntegraciones{cantidad=" + integraciones.size() + "}";
    }
}

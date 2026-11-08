package modelo;

import java.util.Map;

public class LogisticaInternacional extends ApiLogistica {
    
    public LogisticaInternacional(String id, String nombre, String proveedor, double latencia,
            EstadoConexion estado, String aduana, double tarifaPorKg) {
        super(id, nombre, proveedor, latencia, estado,

                Map.of("aduana", texto(aduana, "Aduana"),
                        "tarifaPorKgGTQ", Double.toString(positivo(tarifaPorKg))));
    }

    @Override public String consultarEstado(String referencia) {
        verificarDisponible();
        return "Envío internacional " + texto(referencia, "Referencia")
                + ": revisión aduanera simulada en " + getParametrosConfiguracion().get("aduana");
    }
    
    @Override public double cotizar(double monto) {
        verificarDisponible();
        double total = positivo(monto)
                * Double.parseDouble(getParametrosConfiguracion().get("tarifaPorKgGTQ"));
        return positivo(total);
    }
    @Override public String toString() {
        return "LogisticaInternacional{" + super.toString() + "; " + getParametrosConfiguracion() + "}";
    }
}

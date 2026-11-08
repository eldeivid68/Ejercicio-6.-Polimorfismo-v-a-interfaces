package modelo;

import java.util.Map;

public class LogisticaNacional extends ApiLogistica {
    
    public LogisticaNacional(String id, String nombre, String proveedor, double latencia,
            EstadoConexion estado, String cobertura, double tarifaBase) {
        super(id, nombre, proveedor, latencia, estado,

                Map.of("cobertura", texto(cobertura, "Cobertura"),
                        "tarifaBaseGTQ", Double.toString(positivo(tarifaBase))));
    }

    @Override public String consultarEstado(String referencia) {
        verificarDisponible();
        return "Envío nacional " + texto(referencia, "Referencia")
                + ": en distribución (simulado). Cobertura: "
                + getParametrosConfiguracion().get("cobertura");
    }
    
    @Override public double cotizar(double monto) {
        verificarDisponible();
        positivo(monto);
        return Double.parseDouble(getParametrosConfiguracion().get("tarifaBaseGTQ"));
    }
    @Override public String toString() {
        return "LogisticaNacional{" + super.toString() + "; " + getParametrosConfiguracion() + "}";
    }
}

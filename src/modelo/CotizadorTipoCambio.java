package modelo;

import java.util.Map;

public class CotizadorTipoCambio extends IntegracionBase implements Consultable {
    
    public CotizadorTipoCambio(String id, String nombre, String proveedor, double latencia,
            EstadoConexion estado, double tasaGtqPorUsd) {
        super(id, nombre, proveedor, latencia, estado,

                Map.of("monedaOrigen", "USD", "monedaDestino", "GTQ",
                        "tasaGtqPorUsd", Double.toString(positivo(tasaGtqPorUsd))));
    }

    @Override public String consultarEstado(String referencia) {
        verificarDisponible();
        return consultarEstado() + "; consulta: " + texto(referencia, "Referencia");
    }
    @Override public String consultarEstado() {
        verificarDisponible();
        return getNombre() + ": " + getEstadoConexion() + "; tasa simulada USD/GTQ = "
                + getParametrosConfiguracion().get("tasaGtqPorUsd");
    }
    
    @Override public double cotizar(double monto) {
        verificarDisponible();
        return positivo(positivo(monto)
                * Double.parseDouble(getParametrosConfiguracion().get("tasaGtqPorUsd")));
    }
    @Override public String toString() {
        return "CotizadorTipoCambio{" + super.toString() + "; " + getParametrosConfiguracion() + "}";
    }
}

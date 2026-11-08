package modelo;

import java.util.Map;
import java.util.Locale;

public class PasarelaTarjetaCredito extends ApiPago {
    
    public PasarelaTarjetaCredito(String id, String nombre, String proveedor, double latencia,
            EstadoConexion estado, double comisionPorcentaje, String merchantId) {
        super(id, nombre, proveedor, latencia, estado,

                Map.of("comisionPorcentaje", Double.toString(comisionPorcentaje),
                        "merchantId", texto(merchantId, "Comercio")));
        if (!Double.isFinite(comisionPorcentaje) || comisionPorcentaje < 0 || comisionPorcentaje > 100) {
            throw new IllegalArgumentException("La comisión debe estar entre 0 y 100.");
        }
    }

    @Override public String cobrar(double monto, String moneda) {
        verificarDisponible();
        positivo(monto);
        String codigo = validarMoneda(moneda);
        double porcentaje = Double.parseDouble(getParametrosConfiguracion().get("comisionPorcentaje"));
        double comision = monto * (porcentaje / 100.0);
        String resultado = String.format(Locale.ROOT,
                "Tarjeta: cobro simulado %.2f %s; comisión al comercio %.2f %s; neto %.2f %s.",
                monto, codigo, comision, codigo, monto - comision, codigo);
        registrarEvento(resultado);
        return resultado;
    }
    @Override public String toString() {
        return "PasarelaTarjetaCredito{" + super.toString() + "; " + getParametrosConfiguracion() + "}";
    }
}

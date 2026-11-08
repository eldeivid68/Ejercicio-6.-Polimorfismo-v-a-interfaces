package modelo;

import java.util.Map;
import java.util.Locale;

public class BilleteraDigital extends ApiPago {
    
    public BilleteraDigital(String id, String nombre, String proveedor, double latencia,
            EstadoConexion estado, String app, double limitePorTransaccion) {
        super(id, nombre, proveedor, latencia, estado,

                Map.of("app", texto(app, "Aplicación"),
                        "limitePorTransaccionGTQ", Double.toString(positivo(limitePorTransaccion))));
    }

    @Override public String cobrar(double monto, String moneda) {
        verificarDisponible();
        positivo(monto);
        if (!validarMoneda(moneda).equals("GTQ")) {
            return "Billetera rechazada: este servicio solo admite GTQ.";
        }
        double limite = Double.parseDouble(getParametrosConfiguracion().get("limitePorTransaccionGTQ"));
        if (monto > limite) {
            registrarEvento("Cobro rechazado por exceder el límite.");
            return "Cobro rechazado: excede el límite de " + limite + " GTQ.";
        }
        String resultado = String.format(Locale.ROOT,
                "Billetera %s: débito simulado de %.2f GTQ.",
                getParametrosConfiguracion().get("app"), monto);
        registrarEvento(resultado);
        return resultado;
    }
    @Override public String toString() {
        return "BilleteraDigital{" + super.toString() + "; " + getParametrosConfiguracion() + "}";
    }
}

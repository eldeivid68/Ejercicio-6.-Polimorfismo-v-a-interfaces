package modelo;

import java.util.Map;
import java.util.Locale;

public class TransferenciaBancaria extends ApiPago {
    
    public TransferenciaBancaria(String id, String nombre, String proveedor, double latencia,
            EstadoConexion estado, String banco, String cuentaEnmascarada) {
        super(id, nombre, proveedor, latencia, estado,

                Map.of("banco", texto(banco, "Banco"),
                        "cuentaDestino", texto(cuentaEnmascarada, "Cuenta")));
        if (!cuentaEnmascarada.matches("\\*{4,}[0-9]{4}")) {
            throw new IllegalArgumentException("Use una cuenta enmascarada: ****1234.");
        }
    }

    @Override public String cobrar(double monto, String moneda) {
        verificarDisponible();
        positivo(monto);
        String codigo = validarMoneda(moneda);
        if (!codigo.equals("GTQ")) {
            return "Transferencia rechazada: este servicio solo admite GTQ.";
        }
        String resultado = String.format(Locale.ROOT,
                "Transferencia simulada de %.2f GTQ a %s, cuenta %s; pendiente de acreditación.",
                monto, getParametrosConfiguracion().get("banco"),
                getParametrosConfiguracion().get("cuentaDestino"));
        registrarEvento(resultado);
        return resultado;
    }
    @Override public String toString() {
        return "TransferenciaBancaria{" + super.toString() + "; " + getParametrosConfiguracion() + "}";
    }
}

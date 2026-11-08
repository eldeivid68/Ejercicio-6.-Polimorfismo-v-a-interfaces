package modelo;

import java.util.Map;

public class CertificadorFel extends ApiFel {
    
    public CertificadorFel(String id, String nombre, String proveedor, double latencia,
            EstadoConexion estado, String nitCertificador, String ambiente) {
        super(id, nombre, proveedor, latencia, estado,

                Map.of("nitCertificador", texto(nitCertificador, "NIT"),
                        "ambiente", texto(ambiente, "Ambiente")));
        if (!ambiente.equals("pruebas") && !ambiente.equals("producción")) {
            throw new IllegalArgumentException("Ambiente permitido: pruebas o producción.");
        }
    }

    @Override public String generarDocumento(String referencia) {
        verificarDisponible();
        String documento = "<facturaFEL simulada=\"true\" referencia=\"" + xml(referencia)
                + "\" nitCertificador=\"" + xml(getParametrosConfiguracion().get("nitCertificador"))
                + "\" ambiente=\"" + xml(getParametrosConfiguracion().get("ambiente")) + "\"/>";
        registrarEvento("Factura simulada generada: " + texto(referencia, "Referencia"));
        return documento;
    }
    @Override public String toString() {
        return "CertificadorFel{" + super.toString() + "; " + getParametrosConfiguracion() + "}";
    }
}

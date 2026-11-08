package modelo;

public final class CargaInicial {
    
    private CargaInicial() { }

    public static void cargar(RepositorioIntegraciones repositorio) {
        repositorio.agregar(new PasarelaTarjetaCredito("API-001", "Tarjeta Rápida", "Pago Uno",
                80, EstadoConexion.ACTIVA, 3.5, "COM-001"));
        repositorio.agregar(new PasarelaTarjetaCredito("API-002", "Tarjeta Regional", "Pago Dos",
                350, EstadoConexion.DEGRADADA, 2.8, "COM-002"));
        repositorio.agregar(new PasarelaTarjetaCredito("API-003", "Tarjeta Reserva", "Pago Tres",
                900, EstadoConexion.INACTIVA, 4, "COM-003"));
        repositorio.agregar(new TransferenciaBancaria("API-004", "Transferencia Local", "Banco A",
                120, EstadoConexion.ACTIVA, "Banco A", "****1234"));
        repositorio.agregar(new TransferenciaBancaria("API-005", "Transferencia Alterna", "Banco B",
                650, EstadoConexion.INACTIVA, "Banco B", "****5678"));
        repositorio.agregar(new BilleteraDigital("API-006", "Billetera Móvil", "Pagos Móviles",
                60, EstadoConexion.ACTIVA, "MiBilletera", 1500));
        repositorio.agregar(new BilleteraDigital("API-007", "Billetera Regional", "Pagos Regionales",
                280, EstadoConexion.DEGRADADA, "Bolsillo", 800));
        repositorio.agregar(new LogisticaNacional("API-008", "Envíos Capital", "Mensajería A",
                100, EstadoConexion.ACTIVA, "Ciudad de Guatemala", 25));
        repositorio.agregar(new LogisticaNacional("API-009", "Envíos Nacionales", "Mensajería B",
                450, EstadoConexion.DEGRADADA, "Departamentos de Guatemala", 45));
        repositorio.agregar(new LogisticaInternacional("API-010", "Envíos Globales", "Carga Global",
                700, EstadoConexion.ACTIVA, "Aduana Central", 65));
        repositorio.agregar(new CertificadorFel("API-011", "FEL Pruebas", "Certificador A",
                180, EstadoConexion.ACTIVA, "1234567-8", "pruebas"));
        repositorio.agregar(new CertificadorFel("API-012", "FEL Producción", "Certificador B",
                500, EstadoConexion.DEGRADADA, "7654321-0", "producción"));
        repositorio.agregar(new CotizadorTipoCambio("API-013", "Cambio Referencial", "Divisas Demo",
                40, EstadoConexion.ACTIVA, 7.65));
    }
    @Override public String toString() { return "CargaInicial{13 integraciones ficticias}"; }
}

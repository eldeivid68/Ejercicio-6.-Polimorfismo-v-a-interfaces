package vista;

import controlador.ControladorIntegraciones;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import modelo.IntegracionApi;

public class VistaConsola {
    private final ControladorIntegraciones controlador;
    private final Scanner entrada = new Scanner(System.in);

    public VistaConsola(ControladorIntegraciones controlador) {
        this.controlador = Objects.requireNonNull(controlador, "Controlador requerido");
    }
    
    public void iniciar() {
        try {
            boolean continuar = true;
            while (continuar) {
                System.out.println("\nCATÁLOGO DE INTEGRACIONES");
                System.out.println("1. Listar integraciones");
                System.out.println("2. Buscar API por identificador");
                System.out.println("3. Ordenar por latencia");
                System.out.println("4. Simular checkout");
                System.out.println("5. Salir");
                switch (leerOpcion(1, 5)) {
                    case 1 -> mostrarTabla(controlador.listar());
                    case 2 -> buscar();
                    case 3 -> {
                        controlador.ordenarPorLatencia();
                        mostrarTabla(controlador.listar());
                    }
                    case 4 -> checkout();
                    case 5 -> continuar = false;
                    default -> throw new IllegalStateException("Opción no validada.");
                }
            }
            System.out.println("Programa finalizado.");
        } catch (NoSuchElementException excepcion) {
            System.out.println("\nEntrada finalizada. Programa cerrado.");
        }
    }
    
    private void mostrarTabla(List<IntegracionApi> integraciones) {
        System.out.printf("%-10s %-25s %-22s %12s %-10s%n",
                "ID", "NOMBRE", "PROVEEDOR", "LATENCIA ms", "ESTADO");
        System.out.println("-".repeat(85));
        for (IntegracionApi api : integraciones) {
            System.out.printf(Locale.ROOT, "%-10s %-25.25s %-22.22s %12.2f %-10s%n",
                    api.getId(), api.getNombre(), api.getProveedor(),
                    api.getLatenciaPromedioMs(), api.getEstadoConexion());
        }
    }
    
    private void buscar() {
        IntegracionApi api = seleccionarApi();
        if (api == null) { return; }
        System.out.println(api);
        System.out.printf("%-28s %s%n", "PARÁMETRO", "VALOR");
        api.getParametrosConfiguracion().forEach((clave, valor) ->
                System.out.printf("%-28s %s%n", clave, valor));
    }
    
    private IntegracionApi seleccionarApi() {
        String id = leerTexto("Identificador (ej. API-001): ").toUpperCase(Locale.ROOT);
        IntegracionApi api = controlador.buscarPorId(id).orElse(null);
        if (api == null) { System.out.println("No existe una API con ese identificador."); }
        return api;
    }
    
    private void checkout() {
        IntegracionApi api = seleccionarApi();
        if (api == null) { return; }
        System.out.println("1. Cobrar  2. Reintegrar  3. Generar documento  4. Consultar estado");
        String resultado;
        switch (leerOpcion(1, 4)) {
            case 1 -> {
                double monto = leerMonto();
                String moneda = leerTexto("Moneda (Enter = GTQ; GTQ o USD): ");
                resultado = moneda.isBlank() ? controlador.procesarCobro(api, monto)
                        : controlador.procesarCobro(api, monto, moneda);
            }
            case 2 -> resultado = controlador.procesarReintegro(api, leerMonto());
            case 3 -> resultado = controlador.generarDocumento(api, leerTexto("Referencia: "));
            case 4 -> resultado = controlador.consultar(api,
                    leerTexto("Referencia (Enter = estado general): "));
            default -> throw new IllegalStateException("Opción no validada.");
        }
        System.out.println(resultado);
    }
    
    private int leerOpcion(int minimo, int maximo) {
        while (true) {
            String linea = leerTexto("Opción: ");
            try (Scanner lector = new Scanner(linea)) {
                int opcion = lector.nextInt();
                if (lector.hasNext()) { throw new InputMismatchException(); }
                if (opcion >= minimo && opcion <= maximo) { return opcion; }
                System.out.println("Seleccione una opción entre " + minimo + " y " + maximo + ".");
            } catch (NoSuchElementException excepcion) {
                
                System.out.println("Entrada inválida: escriba un número entero.");
            }
        }
    }
    
    private double leerMonto() {
        while (true) {
            String linea = leerTexto("Monto positivo (sin separadores de miles): ").replace(',', '.');
            try {
                double monto = Double.parseDouble(linea);
                if (Double.isFinite(monto) && monto > 0) { return monto; }
            } catch (NumberFormatException excepcion) {
                
            }
            System.out.println("Monto inválido: ingrese un número positivo y finito.");
        }
    }
    
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine().strip();
    }
    @Override public String toString() { return "VistaConsola{" + controlador + "}"; }
}

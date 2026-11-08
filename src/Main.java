import controlador.ControladorIntegraciones;
import modelo.CargaInicial;
import modelo.RepositorioIntegraciones;
import vista.VistaConsola;

public final class Main {
    
    private Main() { }
    
    public static void main(String[] argumentos) {
        RepositorioIntegraciones repositorio = new RepositorioIntegraciones();
        CargaInicial.cargar(repositorio);
        ControladorIntegraciones controlador = new ControladorIntegraciones(repositorio);
        VistaConsola vista = new VistaConsola(controlador);
        vista.iniciar();
    }
    @Override public String toString() { return "Main{driver MVC}"; }
}

package modelo;

public interface Consultable {

    String consultarEstado(String referencia);
    
    String consultarEstado();
    
    double cotizar(double monto);
}

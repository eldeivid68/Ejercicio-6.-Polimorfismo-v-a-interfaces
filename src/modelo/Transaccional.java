package modelo;

public interface Transaccional {

    String cobrar(double monto);
    
    String cobrar(double monto, String moneda);
    
    String reintegrar(double monto);
}

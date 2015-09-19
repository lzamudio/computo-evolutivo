package practica2;

/**
 *
 * @author luisenriquezamudiocervantes
 */
public class Xi {
    
    private final int longitud;
    private final double rangoMinimo;
    private final double rangoMaximo;

    public Xi(int longitud, double rangoMinimo, double rangoMaximo) {
        this.longitud = longitud;
        this.rangoMinimo = rangoMinimo;
        this.rangoMaximo = rangoMaximo;
    }

    public int getLongitud() {
        return longitud;
    }

    public double getRangoMinimo() {
        return rangoMinimo;
    }

    public double getRangoMaximo() {
        return rangoMaximo;
    }
    
    
    
}

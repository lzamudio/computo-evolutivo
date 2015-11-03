/**
 *
 * @author Luis Enrique Zamudio Cervantes
 */
public abstract class GPNode {
    protected String nombre;
    protected int aridad, altura, tamanio,terminales;
    protected InNode padre;

    /**
     *
     * @param nombre
     */
    public GPNode(String nombre){
        this.nombre=nombre;
        padre=null;
        setAridad();
        tamanio=1;
    }
    
    public GPNode(GPNode nodo){
        this.nombre=nodo.nombre;
        this.padre=null;
        this.aridad=nodo.aridad;
        this.tamanio=nodo.tamanio;
        this.altura=nodo.altura;
        this.terminales=nodo.terminales;
    }
    
    /**
     *
     */
    protected void setAridad(){
        switch(nombre){
            case "AND":
            case "OR":
            case "XOR":
            case "+":
            case "-":
            case "*":
            case "/": aridad=2; break;
            case "NOT":
            case "SQRT":
            case "SIN":
            case "COS":
            case "EXP":
            case "LN": aridad=1; break;
            default: aridad=0;        
        }
     }
    
    /**
     *
     * @return
     */
    public int getAltura(){
        return altura;
    }

    /**
     *
     * @return
     */
    public abstract double eval();
    
    public double eval(double d){
        if (this instanceof TerminalNode)
            return ((TerminalNode)this).eval(d);
        else 
            return eval();
    }
    
    /**
     *
     * @param terminales
     * @param cadenas
     * @return
     */
    public abstract boolean boolean_eval(String [] terminales, String cadenas);
    
    /**
     *
     * @param i
     * @return
     */
    public abstract GPNode getNode(int i);
    
    public abstract void setNode(GPNode n, int lugar);

    public abstract void actualizaTamanio();

    public abstract void actualizaAltura();

    public abstract void actualizaTerminales();
    
    
}

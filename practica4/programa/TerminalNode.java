import java.util.Random;

/**
 *
 * @author Luis Enrique Zamudio Cervantes
 */
public class TerminalNode extends GPNode {
    protected double numeric_val;
    protected final int altura=1;
    protected final int tamanio=1;
    
    public TerminalNode(String nombre) {
        super(nombre);
        terminales=1;
        try{
            numeric_val= Double.parseDouble(nombre);
        }catch(NumberFormatException nfe){
            Random r= new Random();
            numeric_val=r.nextDouble();
        }
    }
    
    public TerminalNode(TerminalNode n){
        super (n);
        terminales=1;
        numeric_val=n.numeric_val;
    }
    
    @Override
    public double eval(){
        return numeric_val;
    }
    
   
    public double eval(double x){
        if(nombre.equals("X"))
            numeric_val=x;
        return x;
    }
    
    @Override
    public boolean boolean_eval(String []terminales, String cadena){
        int i=0;
        for (i = 0; i < terminales.length; i++) {
            if (terminales[i].equals(nombre))
                break;
        }
       boolean boolean_val= cadena.charAt(i)!='0';
       
       return boolean_val;
    }
    
   
    @Override
    public GPNode getNode(int i) {
        return new TerminalNode(this);
    }
    
     @Override
    public String toString(){
        return nombre=="R"?"R="+numeric_val:nombre;
    }

    @Override
    public void setNode(GPNode nodo, int lugar) {
        if(padre!=null){
            if (padre.hizq.equals(this))
                padre.hizq=nodo;
            else 
                padre.hder=nodo;
            nodo.padre=padre;
            nodo.actualizaTamanio();
            nodo.actualizaTerminales();
            nodo.actualizaAltura();
        }
    }

    @Override
    public void actualizaTamanio() {
        padre.actualizaTamanio();
    }

    @Override
    public void actualizaAltura() {
        padre.actualizaAltura();
    }

    @Override
    public void actualizaTerminales() {
        padre.actualizaTerminales();
    }

}

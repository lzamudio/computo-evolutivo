/**
 * @author Luis Enrique Zamudio Cervantes
 */
public class InNode extends GPNode{
    GPNode hizq, hder;
    
    public InNode (String nombre){
        super(nombre);
    }
    
    
    public InNode(String nombre, GPNode i){
        super(nombre);
        i.padre=this;
        hizq=i;
        setAridad();
        hder=null;
        terminales=i.terminales;
        tamanio=1+i.tamanio;
        altura=1+i.altura;
    }
    
    public InNode(String nombre, GPNode i, GPNode d) {
        super(nombre);
        i.padre=this;
        hizq = i;
        d.padre=this;
        hder = d;
        setAridad();
        terminales=i.terminales+d.terminales;
        actualizaAltura();
        tamanio=1+i.tamanio+d.tamanio;
        
    }
    
    public InNode(InNode nodo){
        super(nodo);
        GPNode hijo1 = nodo.hizq, hijo2 = nodo.hder;
        
        if(hijo1!=null){
            if(hijo1 instanceof TerminalNode)
                hijo1=new TerminalNode((TerminalNode)hijo1);
            else
                hijo1=new InNode((InNode)hijo1);
            hijo1.padre=this;
        }
        hizq = hijo1;
        
        
        if(hijo2!=null){
            if(hijo2 instanceof TerminalNode)
                hijo2=new TerminalNode((TerminalNode)hijo2);
            else
                hijo2=new InNode((InNode)hijo2);
            hijo2.padre=this;
        }
        hder = hijo2;
        
    }
    
    @Override
    public double eval(){
        switch(nombre){
            case "SQRT":
                return Math.sqrt(Math.abs(hizq.eval()));
            case "SIN":
                return Math.sin(hizq.eval());
            case "COS":
                return Math.cos(hizq.eval());
            case "EXP":
                double y=hizq.eval();
                y=Math.exp(y);
                y=y>Math.pow(2,1000)?Math.pow(2,100):y;
                //System.out.println(this+"\nLa eval es:" +y+"la del hiz es: "+hizq.eval()+"\n\n");
                return y;
            case "LN": 
                double x = Math.abs(hizq.eval());
                x += x==0? 0.000000001:0;
                return Math.log(x);
            case "+":
                return hizq.eval() + hder.eval();
            case "-":
                return hizq.eval() - hder.eval();
            case "*":
                return hizq.eval() * hder.eval();
            case "/": 
                double evalder=hder.eval();
                return evalder==0? 0: hizq.eval() /evalder;
            default: return 0;        
        }
    }
    
    @Override 
    public boolean boolean_eval(String [] terminales, String cadenas){
        if(aridad==1)
            return !hizq.boolean_eval(terminales, cadenas);
        
        switch(nombre){
            case "NEG":
                return !hizq.boolean_eval(terminales, cadenas);
            case "AND": 
                return hizq.boolean_eval(terminales, cadenas) && hder.boolean_eval(terminales, cadenas);
            case "OR":
                return hizq.boolean_eval(terminales, cadenas) || hder.boolean_eval(terminales, cadenas);
            case "XOR":
                return hizq.boolean_eval(terminales, cadenas) ^ hder.boolean_eval(terminales, cadenas);
            default: return false;        
        }
    }
    
    public void setHijoIzq(GPNode hizq){
        this.hizq=hizq;
        hizq.padre=this;
        actualizaTamanio();
        hizq.actualizaTerminales();
        actualizaAltura();
    }
    
    public void setHijoDer(GPNode hder){
        hder.padre=this;
        this.hder=hder;
        actualizaTamanio();
        actualizaTerminales();
        actualizaAltura();
    }
    
    @Override
    public GPNode getNode(int i){
        GPNode nuevo;
        if(hizq.tamanio==i-1)
            nuevo=this;
        else if(hizq.tamanio>=i)
            nuevo = hizq.getNode(i);
        else
            nuevo =hder.getNode(i-(hizq.tamanio+1));
        
        if(nuevo instanceof InNode)
            nuevo= new InNode((InNode)nuevo);
        else
            nuevo= new TerminalNode((TerminalNode)nuevo);
        
        return nuevo;
    }
    
    @Override
    public void setNode(GPNode n, int lugar){
        if(hizq.tamanio==lugar-1)
            cambia(n);
        else if(hizq.tamanio>=lugar)
             hizq.setNode(n,lugar);
        else
            hder.setNode(n,lugar-(hizq.tamanio+1));
    }
    
    
    public void cambia(GPNode nodo){
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
    
    public int getAridad(){
        return aridad;
    }
    
    @Override
    public void actualizaTamanio() {
        hder = aridad==1? null: hder;
        tamanio = 1;
        tamanio += hizq != null ? hizq.tamanio : 0;
        tamanio += hder != null ? hder.tamanio : 0;
        if(padre!=null)
            padre.actualizaTamanio();
    }
    
    @Override
    public void actualizaAltura(){
        int alt_izq = hizq != null ? hizq.altura : 0;
        int alt_der = hder != null ? hder.altura : 0;
        altura=1+Math.max(alt_izq,alt_der);
        
        if(padre!=null)
           padre.actualizaAltura();
    }
    
    @Override
    public void actualizaTerminales(){
        int izq = hizq != null ? hizq.terminales : 0;
        int der = hder != null ? hder.terminales : 0;
        terminales=izq+der;
        
        if(padre!=null)
           padre.actualizaTerminales();
    }
    
    @Override
    public String toString(){
        if(aridad==1)
           return "("+nombre+" " +hizq +")";
        else
           return  "("+nombre+" " +hizq + " "+ hder+ ")";
        
    }
    
  
}

import java.util.Random;

/**
 *
 * @author Luis Enrique Zamudio Cervantes
 */
class GPTree implements Comparable{
    GPNode raiz;
    double fitness;
    
    static Random r= new Random();
    GPTree(GPNode n){
        raiz=n;
        raiz.padre=null;
    }
    
    GPTree(GPTree t){
        raiz=t.raiz;
        raiz.padre=null;
    }
    
    public double eval(double d){
        return raiz.eval(d);
    }
    
    public double eval(){
       return raiz.eval();
    }
    
    public boolean [] boolean_eval(String [] terminales, String [] cadenas){
        boolean [] evaluaciones= new boolean[cadenas.length];
        for (int i = 0; i < cadenas.length; i++) {
            evaluaciones[i]=raiz.boolean_eval(terminales, cadenas[i]);
        }
        return evaluaciones;
    }
   
    public GPTree[] cruzaNodoAleatorio(GPTree padre, double pcruza) {
        GPTree[] hijos = new GPTree[2];
        GPNode aux= raiz, aux2= padre.raiz;
        GPNode corte1,corte2;
        int nodo1, nodo2;
        if (r.nextDouble() <= pcruza) {
   
            nodo2 = 1 + r.nextInt(aux2.tamanio);
            corte1 = aux2.getNode(nodo2);

            nodo1 = 1 + r.nextInt(aux.tamanio);
            corte2 = aux.getNode(nodo1);
            
            /*System.out.println("Árbol1:\nAltura: "+aux.altura+", Tamanio:"+ aux.tamanio+" Terminales: "+aux.terminales);
            System.out.println(aux);
            System.out.println("Árbol2:\nAltura: "+aux2.altura+", Tamanio:"+ aux2.tamanio+" Terminales: "+aux2.terminales);
            System.out.println(aux2);
            System.out.println("Corte 1: "+ nodo1+"\n"+corte1);
            System.out.println("Corte 2: "+ nodo2+"\n"+corte2);*/
            aux2.setNode(corte2, nodo2);
            aux.setNode(corte1, nodo1);
            
            /*System.out.println("Cruzados:");
            System.out.println(aux.tamanio+" "+aux2.tamanio);
            System.out.println(aux);
            System.out.println(aux2);
            System.out.println("\n\n");*/
            
        } 
            hijos[0] = new GPTree(aux);
            hijos[1] = new GPTree(aux2);
        
        
        return hijos;
    }
    
    public void mutacionTamJusto(String []funciones , String[]terminales){
        int nodo=1+r.nextInt(raiz.tamanio);
        GPNode node=raiz.getNode(nodo);
        //System.out.println(raiz+"\nTamanio: "+raiz.tamanio+", corte:"+nodo+"\n"+node);
        GPNode nuevo= ProgGen.makeGrowNode(node.altura,funciones,terminales);
        raiz.setNode(nuevo,nodo);
        //System.out.println("Nuevo: "+nuevo+"\nTamanio: "+raiz.tamanio+" Mutado:\n"+raiz+"\n\n");
 
    }

    @Override
    public String toString(){
        return raiz.toString();
    }
    
    public void setFitness(double fit){
        fitness=fit;
    }
    

    @Override
    public int compareTo(Object o) {
        if(this.fitness==((GPTree)o).fitness)
            return 0;
        return fitness>((GPTree)o).fitness?-1:1;
    }
    
}


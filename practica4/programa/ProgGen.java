import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Luis Enrique Zamudio Cervantes
 */
public class ProgGen {
   static Random r = new Random();
    
   static GPTree[] makeFullPopulation(int pop_size, int max_high, String[] funciones, String [] terminals) {
        GPTree [] poblacion= new GPTree[pop_size];
        for (int i = 0; i < pop_size; i++) {
            poblacion[i]=makeFullTree(max_high, funciones, terminals);
        }

        return poblacion;
    }
    
    static GPNode [] getTerminals(String[] terminals, int tamanio){
        int cuantos=(int) Math.pow(2, tamanio-1);
        GPNode [] lista= new GPNode[cuantos];
        for (int i = 0; i < cuantos; i++) {
            lista[i]=new TerminalNode(terminals[r.nextInt(terminals.length)]);
        }
        return lista;
    }

    static GPTree makeFullTree(int max_high, String[] funciones, String [] terminals) {
        GPNode [] terminales= getTerminals(terminals, max_high);
        GPNode [] nodes= terminales;
        InNode bin_temp;
        while (terminales.length > 1) {
            nodes = new GPNode[nodes.length / 2];
            for (int i = 0; i < nodes.length; i++) {
                GPNode temp;
                do {
                    temp = new InNode(funciones[r.nextInt(funciones.length)]);
                } while (temp.aridad != 2);
                GPNode hizq = terminales[i * 2];
                GPNode hder = terminales[i * 2 + 1];
                bin_temp = new InNode(temp.nombre,hizq, hder);
                nodes[i] = bin_temp;
            }
            terminales = nodes;
        }
        GPTree nuevo = new GPTree(terminales[0]);
        return nuevo;
    }
    
    static GPTree[] makeGrowPopulation(int pop_size, int max_high, String[] funciones, String [] terminals) {
        GPTree[] poblacion= new GPTree[pop_size];
        for (int i = 0; i < pop_size; i++) {
            poblacion[i]=makeGrowTree(max_high,funciones,terminals);
        }
        return poblacion;
    }
    
    static GPTree makeGrowTree(int max_high, String[] funciones, String [] terminals){
        GPNode raiz=makeGrowNode(max_high,funciones,terminals);
        GPTree arbol= new GPTree(raiz);
        return arbol;
    }
    
    static GPNode makeGrowNode(int max_high, String[] funciones, String [] terminals){
        if(max_high<=0){
            TerminalNode terminal=new TerminalNode(terminals[r.nextInt(terminals.length)]);
            return terminal;
        }
        int tamanio= r.nextInt(max_high);
        
        InNode nodo=new InNode(funciones[r.nextInt(funciones.length)]);
        nodo.setHijoIzq(makeGrowNode(tamanio,funciones,terminals));
        
        if(nodo.aridad==2){
            nodo.setHijoDer(makeGrowNode(tamanio,funciones,terminals));
        }
        return nodo;
    }
    
    static GPTree makeRampedTree(int max_high, String[] funciones, String [] terminals){
        GPNode raiz= makeNode(max_high,funciones,terminals);
        if(raiz instanceof InNode)
            setRampedChilds((InNode)raiz,max_high,funciones,terminals);
        return new GPTree(raiz);
    }
    
    static GPTree [] makeRampedPopulation(int pop_size,int max_high, String[] funciones, String [] terminals){
        GPTree[] population= new GPTree[pop_size];
        for (int i = 0; i < pop_size; i++) {
            population[i]=makeRampedTree(max_high,funciones,terminals);
        }
        
        return population;
    }
    
    static void setRampedChilds(InNode nodo, int high, String[] functions, String []terms){
        GPNode h1=makeNode(high,functions,terms), h2;
        InNode in1, in2;
        if (h1 instanceof InNode) {
            in1 = (InNode) h1;
            setRampedChilds(in1, high - 1, functions, terms);
        }
        if (nodo.getAridad() == 2) {
            h2 = makeNode(high-2, functions, terms);
            if (h2 instanceof InNode) {
                in2 = (InNode) h2;
                setRampedChilds(in2, high - 2, functions, terms);
            }

            if (r.nextInt(2) == 0) {
                GPNode temp = h2;
                h2 = h1;
                h1 = temp;
            }
            nodo.setHijoIzq(h1);
            nodo.setHijoDer(h2);
        }else
            nodo.setHijoIzq(h1);

    }
    
    static GPNode makeNode(int high, String[] functions, String[] terms){
        if(high>0)
            return new InNode(functions[r.nextInt(functions.length)]);
        return new TerminalNode(terms[r.nextInt(terms.length)]);
    }
    
    static String [] generaCadenasBooleanas(int n){
        String []cadenas = new String[(int)Math.pow(2,n)];
        String s="";
        for (int i = 0; i < cadenas.length; i++) {
            s=Integer.toBinaryString(i);
            while(s.length()<n){
                s="0"+s;
            }
            cadenas[i]=s;
        }
        return cadenas;
    }
    
    static void mutar(GPTree [] poblacion,String [] funciones, String [] terminales,double p_mutacion){
        for (int i = 0; i < poblacion.length; i++) {
            if(r.nextDouble()<=p_mutacion)
                poblacion[i].mutacionTamJusto(funciones, terminales);
        }
    }
    
    static GPTree[] elitismoK(GPTree[] poblacion, GPTree[] mejores, int k) {
        Arrays.sort(poblacion);
        Arrays.sort(mejores);
        GPTree[] nuevos = new GPTree[k];
        int p = poblacion.length - 1, m = mejores.length - 1;
        GPTree ind_p, ind_m;

        for (int i = k - 1; i >= 0; i--) {
            ind_p = poblacion[p];
            ind_m = mejores[m];
            if (p < 0 ) {
                nuevos[i] = ind_m;
                m--;

            } else if (m < 0) {
                nuevos[i] = ind_p;
                p--;
            } else {
                if (ind_p.compareTo(ind_m) == 1) {
                    nuevos[i] = ind_p;
                    p--;
                } else if(ind_p.compareTo(ind_m)==0) {
                    if(ind_p.raiz.tamanio<ind_m.raiz.tamanio)
                        nuevos[i]=ind_p;
                    else
                        nuevos[i]=ind_m;
                    
                }else{
                    nuevos[i] = ind_m;
                    m--;
                }

            }

        }
        return nuevos;
    }
    
    static GPTree[][] agVasconcelos(GPTree[] poblacion, GPTree[] mejores, 
            String[]funciones, String[] terminales, double p_cruza, double p_mutacion) {
    
        poblacion = seleccionVasconcelos(poblacion,p_cruza);
        mejores = elitismoK(poblacion, mejores, poblacion.length);
        mutar(poblacion,funciones,terminales,p_mutacion);
        
        GPTree[][] r = {poblacion, mejores};
        return r;

    }
    
    
    static GPTree[] seleccionVasconcelos(GPTree [] poblacion, double p_cruza){
        int n= poblacion.length;
        GPTree[] nueva_poblacion = new GPTree[n], hijos;
        Arrays.sort(poblacion);
        int agregados = 0;
        for (int i = 0; i < (n / 2); i++) {
            hijos = poblacion[i].cruzaNodoAleatorio(poblacion[n - i - 1], p_cruza);
            nueva_poblacion[agregados] = hijos[0];
            agregados++;
            if (agregados < n) {
                nueva_poblacion[agregados] = hijos[1];
                agregados++;
            }
        }
        return nueva_poblacion;
    }
    
    
    
}

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 *
 * @author Luis Enrique Zamudio Cervantes
 */
public class Pruebas {
    
    
    static void imprimeResultadosBool(GPTree individuo, boolean[] resultados, String[] cadenas) {
        if (individuo.fitness == 0) {
            System.out.println(individuo);
            for (int k = 0; k < resultados.length; k++) {
                System.out.println(individuo + " " + cadenas[k] + " " + resultados[k]);
            }
        }
    }
    
    static boolean paridadPar(String cadena){
        int par=0;
        for (int i = 0; i < cadena.length(); i++) {
            par+=cadena.charAt(i)=='1'? 1:0;
        }
        return par%2==0;
    }
    
    static double fitnessSumaA10(double eval){
        return Math.abs(10-eval);
    }
    
    static double fitnessParidadPar(boolean resultados[], String [] cadenas){
        double fitness = 0;
            for (int k = 0; k < resultados.length; k++) {
                fitness += resultados[k] == paridadPar(cadenas[k]) ? 0 : 1;
                //System.out.println(poblacion[i]+" "+cadenas[k]+" "+resultados[k]);
            }
         return fitness;
    }
    
    static double fitnessRegression(double[][]tabla, GPTree individuo){
        double fitness = 0,eval;
        for (double[] num : tabla) {
            eval = individuo.eval(num[0]);
            fitness += Math.abs(eval - num[1]);
        }
        individuo.setFitness(fitness);
        return fitness;
    }
    
    static String tablaToString(double [][] tabla){
        String s="";
        for (double[] tabla1 : tabla) {
            for (double num:tabla1) {
                s+=num+",";
            }
            s+="\n";
        }
        return s;
    }
    
    private static double[][] generaTabla() {
        Random r= new Random();
        double [][] tabla= new double [40][2];
        for (int i = 0; i < tabla.length; i++) {
            tabla[i][0]=i;
            tabla[i][1]=r.nextDouble();
        }
        
        return tabla;
    }
    
    static Object[] evaluaRegression(GPTree [] poblacion, double[][]tabla){
        double fitness;
        Object[] valores= new Object[5];
        int total_nodos = 0,nodos_terminales=0;
        double best_fitness = 10000,fitness_promedio=0;
        GPTree mejor = poblacion[0];
        for (GPTree individuo : poblacion) {
            total_nodos += individuo.raiz.tamanio;
            nodos_terminales+=individuo.raiz.terminales;
            fitness = fitnessRegression(tabla,individuo);
            individuo.setFitness(fitness);
            fitness_promedio += fitness;
            if (fitness < best_fitness) {
                best_fitness = fitness;
                mejor = individuo;
            }
        }
        valores[0]=total_nodos;
        valores[1]=fitness_promedio;
        valores[2]=best_fitness;
        valores[3]=nodos_terminales;
        valores[4]=mejor;
                
        
        return valores;
    }
    
    
    static Object[] evaluaPoblacion(GPTree [] poblacion){
        double fitness;
        Object[] valores= new Object[5];
        int total_nodos = 0,nodos_terminales=0;
        double best_fitness = 10000,fitness_promedio=0;
        GPTree mejor = poblacion[0];
        for (GPTree individuo : poblacion) {
            total_nodos += individuo.raiz.tamanio;
            nodos_terminales+=individuo.raiz.terminales;
            fitness = fitnessSumaA10(individuo.raiz.eval());
            individuo.setFitness(fitness);
            fitness_promedio += fitness;
            if (fitness < best_fitness) {
                best_fitness = fitness;
                mejor = individuo;
            }
        }
        valores[0]=total_nodos;
        valores[1]=fitness_promedio;
        valores[2]=best_fitness;
        valores[3]=nodos_terminales;
        valores[4]=mejor;
                
        
        return valores;
        
    }
    static Object [] evaluaPoblacionBool(GPTree[] poblacion, String [] terminales) {
        String [] cadenas= ProgGen.generaCadenasBooleanas(terminales.length);
        Object[] valores= new Object[5];
        double fitness_promedio = 0, best_fitness = 100000,fitness;
        int total_nodos = 0,nodos_terminales=0;
        boolean [] resultados;
        GPTree mejor = poblacion[0];
        for (GPTree individuo : poblacion) {
            total_nodos += individuo.raiz.tamanio;
            nodos_terminales+=individuo.raiz.terminales;
            resultados = individuo.boolean_eval(terminales, cadenas);
            fitness = fitnessParidadPar(resultados, cadenas);
            individuo.setFitness(fitness);
            imprimeResultadosBool(individuo,resultados,cadenas);
            fitness_promedio += fitness;
            if (fitness <= best_fitness) {
                best_fitness = fitness;
                mejor = individuo.raiz.tamanio < mejor.raiz.tamanio ? individuo : mejor;
            }
        }
        valores[0]=total_nodos;
        valores[1]=fitness_promedio;
        valores[2]=best_fitness;
        valores[3]=nodos_terminales;
        valores[4]=mejor;
        
        return valores;

    }
    
    static void ejecucion(int prueba,int generaciones, double p_cruza, double p_mutacion,
            String[] terminales, String[] funciones, GPTree[] poblacion, 
            String nombreArchivo){
        
        GPTree[] mejores = new GPTree[poblacion.length];
        System.arraycopy(poblacion, 0, mejores, 0, mejores.length);
        GPTree[][] aux;
        double best_fitness=Double.MAX_VALUE, fitness_promedio;
        int total_nodos, nodos_terminales;
        String datos = "";
        GPTree mejor,mejor_global=poblacion[0];
        mejor_global.setFitness(1000000000);
        double [][]tabla= generaTabla();
        int min_generaciones=0;
        for (int j = 0; j < generaciones; j++) {
            //System.out.println("***************************"+j+"***************************");
            Object[] eval = prueba==0? evaluaPoblacion(poblacion):
                    prueba==1? evaluaPoblacionBool(poblacion,terminales): 
                    evaluaRegression(poblacion,tabla);
            total_nodos=(int) eval[0];
            fitness_promedio=(double)eval[1];
            best_fitness=(double)eval[2];
            nodos_terminales=(int)eval[3];
            mejor=(GPTree)eval[4];
            fitness_promedio /= poblacion.length;
            if(mejor.fitness < mejor_global.fitness){
                mejor_global=mejor;
                min_generaciones=j;
            }
            
            
            datos += j + " " + String.format("%.3f", fitness_promedio) + " " +String.format("%.3f", best_fitness)+ " " 
                    + total_nodos + " "+ nodos_terminales +" "+min_generaciones+" " + mejor + "\n";
            aux = ProgGen.agVasconcelos(poblacion, mejores, funciones, terminales, p_cruza, p_mutacion);
            poblacion = aux[0];
            mejores = aux[1];
            
            
        }
        try (PrintWriter pw = new PrintWriter(nombreArchivo)) {
            datos+=mejor_global + " " + mejor_global.fitness + "\n";
            datos+=prueba!=0 && prueba!=1?tablaToString(tabla):"";
            pw.write(datos);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pruebas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        double p_cruza = 0.8, p_mutacion = 0.2;
        int tamanio = 100, generaciones = 500, altura_max = 5;
        Scanner entrada=new Scanner(System.in);

        while(true){
            System.out.println("Escribe el número de opción que deseas generar\n"+
                "1.- Suma a 10 con +,*\n"+
                "2.- Suma a 10 con +\n"+
                "3.- Paridad par con 2 variables\n"+
                "4.- Paridad par con 3 variables\n"+
                "5.- Paridad par con 4 variables\n"+
                "6.- Paridad par con 7 variables\n"+
                "7.- Paridad par con 10 variables\n"+
                "8.- Regresión numerica \n");
            
            int opcion=entrada.nextInt();
            System.out.println("Iniciando proceso . . .");
            switch(opcion){
                case 1:
                    String[] terminales = {"0", "1", "2", "4"}, funciones = {"*", "+"};
                    GPTree[] poblacion = ProgGen.makeRampedPopulation(tamanio, altura_max, funciones, terminales);
                    ejecucion(0, generaciones, p_cruza, p_mutacion, terminales, funciones, poblacion, "SumaA10+*.txt");
                break;
                case 2:
                    String[] terminales2 = {"0", "1", "2", "4"}, funciones2 = {"+"};
                    GPTree[] poblacion2 = ProgGen.makeRampedPopulation(tamanio, altura_max, funciones2, terminales2);
                    ejecucion(0, generaciones, p_cruza, p_mutacion, terminales2, funciones2, poblacion2, "SumaA10+.txt");
                break;
                case 3:
                    String[] terminales3 = {"A", "B"}, funciones3 = {"AND", "NOT", "OR", "XOR"};
                    GPTree[] poblacion3 = ProgGen.makeRampedPopulation(tamanio, altura_max, funciones3, terminales3);
                    ejecucion(1, generaciones, p_cruza, p_mutacion, terminales3, funciones3, poblacion3, "Paridadpar2var.txt");
                break;
                case 4:
                    String[] terminales4 = {"A", "B", "C"}, funciones4 = {"AND", "NOT", "OR", "XOR"};
                    GPTree[] poblacion4 = ProgGen.makeRampedPopulation(tamanio, altura_max, funciones4, terminales4);
                    ejecucion(1, generaciones, p_cruza, p_mutacion, terminales4, funciones4, poblacion4, "Paridadpar3var.txt");
                break;
                case 5:
                    String[] terminales5 = {"A", "B", "C","D"}, funciones5 = {"AND", "NOT", "OR", "XOR"};
                    GPTree[] poblacion5 = ProgGen.makeRampedPopulation(tamanio, altura_max, funciones5, terminales5);
                    ejecucion(1, generaciones, p_cruza, p_mutacion, terminales5, funciones5, poblacion5, "Paridadpar4var.txt");
                break;
                case 6:
                    String[] terminales6 = {"A", "B", "C","D", "E", "F", "G"}, funciones6 = {"AND", "NOT", "OR", "XOR"};
                    GPTree[] poblacion6 = ProgGen.makeRampedPopulation(tamanio, altura_max, funciones6, terminales6);
                    ejecucion(1, generaciones, p_cruza, p_mutacion, terminales6, funciones6, poblacion6, "Paridadpar7var.txt");
                break;
                case 7:
                    String[] terminales7 = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"}, funciones7 = {"AND", "NOT", "OR", "XOR"};
                    GPTree[] poblacion7 = ProgGen.makeRampedPopulation(tamanio, altura_max, funciones7, terminales7);
                    ejecucion(1, generaciones, p_cruza, p_mutacion, terminales7, funciones7, poblacion7, "Paridadpar10var.txt");
                break;
                case 8:
                    String[] terminales8 = {"X", "R", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
                             funciones8 = {"+", "-", "*", "/", "SQRT", "SIN", "COS", "EXP", "LN"};
                    GPTree[] poblacion8 = ProgGen.makeRampedPopulation(tamanio, altura_max, funciones8, terminales8);
                    ejecucion(2, generaciones, p_cruza, p_mutacion, terminales8, funciones8, poblacion8, "Regresion.txt");
                break;
            }
            System.out.println("Proceso finalizado.\n");
        }
    }

}

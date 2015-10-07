
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luisenriquezamudiocervantes
 */
public class AlgoritmoGenetico {

    private int cantidadAcciones = 16;
    private final int numPoblacion;
    private Individuo[] poblacion;
    private Individuo[] poblacionNueva;
    private final double probMutacion = 0.01;
    private final double probCruza = 0.9;
    private static int contadorFile = 2831;

    public AlgoritmoGenetico() {

        this.numPoblacion = 100;
        poblacion = new Individuo[this.numPoblacion];
        poblacionNueva = new Individuo[this.numPoblacion];
        
    }

    public void cambiaGeneracion() {
        this.poblacion = this.poblacionNueva;
        poblacionNueva = new Individuo[this.numPoblacion];
    }

    public void obtenPoblacion() {
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/generacion.txt")));

            String line = "";
            int i = 0;
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokens = new StringTokenizer(line, "|");
                int e = 0;
                
                
                int[] tmp = new int[tokens.countTokens()];
                while (tokens.hasMoreTokens()) {
                    tmp[e++] = Integer.parseInt(tokens.nextToken());
                }
                
                int[] genes = new int[cantidadAcciones*2];
                for (int j = 0; j < genes.length; j++) {
                    genes[j] = tmp[j];
                }
                poblacion[i++] = new Individuo(cantidadAcciones, genes, tmp[tmp.length-1]);
            }

        } catch (IOException ex) {
            Logger.getLogger(AlgoritmoGenetico.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(AlgoritmoGenetico.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    public void seleccionVasconcelos(){
        Arrays.sort(poblacion);
        
        poblacionNueva[0] = getIndividuoMejor();
        
        int cont = 1;
        while (cont < numPoblacion) {
            for (int i = 0; i < poblacion.length / 2; i++) {
                Individuo ind1 = poblacion[i];
                Individuo ind2 = poblacion[poblacion.length - (i + 1)];

                ind1.cruzaMuta(ind2, probCruza, probMutacion);

                if (cont < numPoblacion && ind1.getHijo1() != null) {
                    poblacionNueva[cont++] = ind1.getHijo1();
                }
                if (cont < numPoblacion && ind1.getHijo2() != null) {
                    poblacionNueva[cont++] = ind1.getHijo2();
                }
            }
        }
        
        String nueva = "";
        String nuevaTxt = "";
        for (Individuo p : poblacionNueva) {
            nueva += p.toString()+"\n";
        }
        for (Individuo p : poblacion) {
            nuevaTxt += p.toString()+p.getFitness()+"\n";
        }
        
        escribeArchivo(nueva,  System.getProperty("user.dir") + "/genes.txt", false);
        escribeArchivo(nuevaTxt,  System.getProperty("user.dir") + "/generaciones/generacion_"+(contadorFile++)+".txt", false);
        
        
    }
    
    public void escribeArchivo(String text, String file, boolean append){
        FileWriter fichero = null;
        PrintWriter pw;
        try {
            File f = new File(file);
            
            fichero = new FileWriter(f, append);
            pw = new PrintWriter(fichero);
            pw.print(text);
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
    
    public Individuo getIndividuoMejor(){
        Arrays.sort(poblacion);
        
        return poblacion[poblacion.length - 1];
    }
    
    public void poblacionInicial() {

        FileWriter fichero = null;
        PrintWriter pw;
        Random rd = new Random();
        try {
            fichero = new FileWriter("genes.txt");
            pw = new PrintWriter(fichero);

            for (int e = 0; e < numPoblacion; e++) {
                int[] genes = new int[cantidadAcciones * 2];

                for (int i = 0; i < genes.length / 2; i++) {
                    int opcion = rd.nextInt(10);

                    switch (opcion) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            genes[i] = opcion;
                            genes[i + cantidadAcciones] = rd.nextInt(361);
                            break;
                        case 6:
                        case 7:
                            genes[i] = opcion;
                            genes[i + cantidadAcciones] = rd.nextInt(801);
                            break;
                        case 8:
                            genes[i] = opcion;
                            genes[i + cantidadAcciones] = rd.nextInt(4);
                            break;
                        case 9:
                            genes[i] = opcion;
                            genes[i + cantidadAcciones] = -1;
                            break;
                    }
                }
                String gen = "";
                for (int i = 0; i < genes.length; i++) {
                    gen += genes[i] + "|";
                }

                pw.println(gen);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

}

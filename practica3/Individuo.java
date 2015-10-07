
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author luisenriquezamudiocervantes
 */
public class Individuo implements Comparable<Individuo> {

    private int cantidadAcciones;
    private int[] genes;
    private int fitness;
    private Individuo hijo1;
    private Individuo hijo2;

    public Individuo(int cantidadAcciones, int[] genes, int fitness) {
        this.cantidadAcciones = cantidadAcciones;
        this.genes = genes;
        this.fitness = fitness;
    }

    public Individuo(int cantidadAcciones) {
        this.cantidadAcciones = cantidadAcciones;
    }

    public void cruzaMuta(Individuo ind, double probCruza, double probMutacion) {
        Random rand = new Random();

        if (rand.nextDouble() > probCruza) {
            return;
        }

        int ptoCruce = rand.nextInt(cantidadAcciones - 1);
        if (ptoCruce == 0) {
            ptoCruce = 1;
        }

        this.hijo1 = new Individuo(cantidadAcciones);
        this.hijo2 = new Individuo(cantidadAcciones);

        int[] genesHijo1 = new int[cantidadAcciones * 2];
        int[] genesHijo2 = new int[cantidadAcciones * 2];

        for (int i = 0; i < ptoCruce; i++) {
            genesHijo1[i] = genes[i];
            genesHijo1[i + cantidadAcciones] = genes[i + cantidadAcciones];

            genesHijo2[i] = ind.genes[i];
            genesHijo2[i + cantidadAcciones] = ind.genes[i + cantidadAcciones];
        }
        for (int i = ptoCruce; i < cantidadAcciones; i++) {
            genesHijo1[i] = ind.genes[i];
            genesHijo1[i + cantidadAcciones] = ind.genes[i + cantidadAcciones];

            genesHijo2[i] = genes[i];
            genesHijo2[i + cantidadAcciones] = genes[i + cantidadAcciones];
        }

        this.hijo1.setIndividuo(genesHijo1);
        this.hijo1.muta(probMutacion);
        
        this.hijo2.setIndividuo(genesHijo2);
        this.hijo2.muta(probMutacion);

    }
    
    public Individuo getHijo1(){
        return this.hijo1;
    }
    
    public Individuo getHijo2(){
        return this.hijo2;
    }
    
    public void setIndividuo(int[] individuo) {

        this.genes = individuo;
    }

    public void muta(double probMutacion) {
        Random rand = new Random();
        double prob;
        for (int i = 0; i < genes.length / 2; i++) {
            prob = rand.nextDouble();
            if (prob <= probMutacion) {
                genes[i] = (genes[i] + 1) % 2;

                int opcion = rand.nextInt(10);

                switch (opcion) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        genes[i] = opcion;
                        genes[i + cantidadAcciones] = rand.nextInt(361);
                        break;
                    case 6:
                    case 7:
                        genes[i] = opcion;
                        genes[i + cantidadAcciones] = rand.nextInt(801);
                        break;
                    case 8:
                        genes[i] = opcion;
                        genes[i + cantidadAcciones] = rand.nextInt(4);
                        break;
                    case 9:
                        genes[i] = opcion;
                        genes[i + cantidadAcciones] = -1;
                        break;
                }
            }
        }
    }

    public int getFitness() {
        return fitness;
    }
    
    @Override
    public String toString() {
        String genotipo = "";
        for (int e = 0; e < genes.length; e++) {
            genotipo += genes[e] +"|";
        }

        return genotipo;
    }

    @Override
    public int compareTo(Individuo o) {
        Integer obj1 = this.getFitness();
        Integer obj2 = o.getFitness();

        return obj1.compareTo(obj2);
    }

}

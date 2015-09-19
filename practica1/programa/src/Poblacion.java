
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author luisenriquezamudiocervantes
 */
public class Poblacion  {
    
    private  Individuo[] poblacion;
    private  Individuo[] poblacionNueva;
    private final int numPoblacion ;
    private final int longitudX;
    private final int longitudY;
    private final double rangoMinimoX;
    private final double rangoMaximoX;
    private final double rangoMinimoY;
    private final double rangoMaximoY;
    private String tipoFuncion;
    
    private final double probMutacion = 0.01;
    private final double probCruza = 0.9;
   
    public Poblacion(int numPoblacion, int longitudX, int longitudY, double rangoMinimoX, double rangoMaximoX, double rangoMinimoY, double rangoMaximoY, String tipoFuncion) {
        this.numPoblacion = numPoblacion;
        this.longitudX = longitudX;
        this.longitudY = longitudY;
        this.rangoMinimoX = rangoMinimoX;
        this.rangoMaximoX = rangoMaximoX;
        this.rangoMinimoY = rangoMinimoY;
        this.rangoMaximoY = rangoMaximoY;
        
        this.tipoFuncion = tipoFuncion;
        
        poblacion = new Individuo[this.numPoblacion];
        poblacionNueva = new Individuo[this.numPoblacion];
        
        this.generaPoblacion();
    }
    
    public void reset(){
        poblacion = new Individuo[this.numPoblacion];
        poblacionNueva = new Individuo[this.numPoblacion];
        
        this.generaPoblacion();
    }
    
    private void generaPoblacion(){
        for(int i = 0 ; i < poblacion.length; i++){
            poblacion[i] = new Individuo(longitudX, longitudY, rangoMinimoX, rangoMaximoX, rangoMinimoY, rangoMaximoY, tipoFuncion);
            poblacion[i].generaAleatorio();
        }
    }
    
    public void cambiaGeneracion(){
        
        this.poblacion = this.poblacionNueva;
        poblacionNueva = new Individuo[this.numPoblacion];
        
    }
    
    public Individuo getIndividuoMejor(){
        Arrays.sort(poblacion);
        
        return poblacion[poblacion.length - 1];
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
        
    }
    
    public void seleccionCuatroTorneo(){
        Random rand = new Random();
        
        poblacionNueva[0] = getIndividuoMejor();
        int cont = 1;
        while (cont < numPoblacion) {
            Individuo[] torneo = new Individuo[4];
            torneo[0] = poblacion[rand.nextInt(poblacion.length)];
            torneo[1] = poblacion[rand.nextInt(poblacion.length)];
            torneo[2] = poblacion[rand.nextInt(poblacion.length)];
            torneo[3] = poblacion[rand.nextInt(poblacion.length)];
            
            Arrays.sort(torneo);
            
            Individuo[] torneo2 = new Individuo[4];
            torneo2[0] = poblacion[rand.nextInt(poblacion.length)];
            torneo2[1] = poblacion[rand.nextInt(poblacion.length)];
            torneo2[2] = poblacion[rand.nextInt(poblacion.length)];
            torneo2[3] = poblacion[rand.nextInt(poblacion.length)];
            
            Arrays.sort(torneo2);
            
            torneo[3].cruzaMuta(torneo2[3], probCruza, probMutacion);

            if (cont < numPoblacion && torneo[3].getHijo1() != null) {
                poblacionNueva[cont++] = torneo[3].getHijo1();
            }
            if (cont < numPoblacion && torneo[3].getHijo2() != null) {
                poblacionNueva[cont++] = torneo[3].getHijo2();
            }
        }
    }
    
    public void seleccionEstocasticoUniversal(){
        Arrays.sort(poblacion);
        
        double fitnessPoblacion = getFitnessPoblacion();
        double fitnessInverso = getFitnessInversoPoblacion();
        
        poblacionNueva[0] = getIndividuoMejor();
        int cont = 1;
        
        int index = this.EstocasticoUniversalAuxiliar(fitnessPoblacion, fitnessInverso);
        Individuo ind1;
        Individuo ind2;
        
        
        while (cont < numPoblacion) {
            
            if(index == -1 ){
                index = this.EstocasticoUniversalAuxiliar(fitnessPoblacion, fitnessInverso);
                continue;
            }
            
            ind1 = poblacion[ (index % numPoblacion) ];
            ind2 = poblacion[ ( (index + 1) % numPoblacion) ];
            
            index += 2;
            
            ind1.cruzaMuta(ind2, probCruza, probMutacion);

            if (cont < numPoblacion && ind1.getHijo1() != null) {
                poblacionNueva[cont++] = ind1.getHijo1();
            }
            if (cont < numPoblacion && ind1.getHijo2() != null) {
                poblacionNueva[cont++] = ind1.getHijo2();
            }
            
        }
    }
    
    private int EstocasticoUniversalAuxiliar(double fitnessPoblacion, double fitnessInverso){
        Random rand = new Random();
        
        double anterior = 0;
        double actual = 0;
        double prob = rand.nextDouble();
        
        for (int i = 0; i < poblacion.length; i++) {
            actual += (fitnessPoblacion - poblacion[i].getFitness()) / fitnessInverso;

            if (anterior <= prob && prob <= actual) {
                return i;
                
            }else{
                anterior += (fitnessPoblacion - poblacion[i].getFitness()) / fitnessInverso;
            }
        }
        
        return -1;
    }
   
    public void seleccionRuleta(){
        
        Arrays.sort(poblacion);
        
        double fitnessPoblacion = getFitnessPoblacion();
        double fitnessInverso = getFitnessInversoPoblacion();
        
        poblacionNueva[0] = getIndividuoMejor();
        int cont = 1;
        
        while (cont < numPoblacion) {
            Individuo ind1 = this.ruletaAuxiliar(fitnessPoblacion, fitnessInverso);
            Individuo ind2 = this.ruletaAuxiliar(fitnessPoblacion, fitnessInverso);
            
            if(ind1 == null || ind2 == null){
                continue;
            }

            ind1.cruzaMuta(ind2, probCruza, probMutacion);

            if (cont < numPoblacion && ind1.getHijo1() != null) {
                poblacionNueva[cont++] = ind1.getHijo1();
            }
            if (cont < numPoblacion && ind1.getHijo2() != null) {
                poblacionNueva[cont++] = ind1.getHijo2();
            }
            
        }
    }
    
    private Individuo ruletaAuxiliar(double fitnessPoblacion, double fitnessInverso){
        Random rand = new Random();
        
        double anterior = 0;
        double actual = 0;
        double prob = rand.nextDouble();
        
        for (Individuo individuo : poblacion) {
            
            actual += (fitnessPoblacion - individuo.getFitness()) / fitnessInverso;
            
            if (anterior <= prob && prob <= actual) {
                return individuo;
                
            }else{
                anterior += (fitnessPoblacion - individuo.getFitness()) / fitnessInverso;
            }
        }
        
        return null;
    }
    
    public double getFitnessPoblacion(){
        double fitness = 0;
        
        for (Individuo individuo : poblacion) {
            fitness += individuo.getFitness();
        }
        
        return fitness;
    }    
    
    private double getFitnessInversoPoblacion(){
        
        double totalFitness = this.getFitnessPoblacion();
        double fitness = 0;
        
        for (Individuo individuo : poblacion) {
            fitness += totalFitness - individuo.getFitness();
        }
        
        return fitness;
    }    
    
    public double getFitnessPromedioPoblacion(){
        
        return getFitnessPoblacion() / numPoblacion;
    }   
    
    public double getDistancia(double x1, double y1, double x2, double y2){
        
        
        return Math.sqrt(Math.pow((x2-x1),2) + Math.pow((y2-y1),2));
    }
    
    public double getDistanciaPromedioOptimo(double x, double y){
        
        double distancia = 0;
        
        for (Individuo individuo : poblacion) {
            distancia += getDistancia(individuo.getValorX(), individuo.getValorY() , x, y);
        }
        
        return distancia / numPoblacion;
    }
    
    public double getDistanciaPromedioOptimoGeneracion(){
        
        double distancia = 0;
        
        Individuo mejorInd = getIndividuoMejor();
        
        for (int i = 0; i < poblacion.length - 1; i++) {
            distancia += getDistancia(poblacion[i].getValorX(), poblacion[i].getValorY() , mejorInd.getValorX(), mejorInd.getValorY());
        }
        
        return distancia / numPoblacion - 1;
    }
    
    
}

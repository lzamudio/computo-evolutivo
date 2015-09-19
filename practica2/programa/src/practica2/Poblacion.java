package practica2;

import java.util.Arrays;

public class Poblacion  {
    
    private  Individuo[] poblacion;
    private  Individuo[] poblacionNueva;
    private String tipoFuncion;
    private final int numPoblacion ;
    private Xi[] xis ;
    
    private final double probMutacion = 0.009;
    private final double probCruza = 0.9;
    
    public Poblacion(int numPoblacion, Xi[] xis, String tipoFuncion) {
        this.numPoblacion = numPoblacion;
        this.xis = xis;
        
        
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
            poblacion[i] = new Individuo(xis, tipoFuncion);
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
    
    public double getFitnessPromedioPoblacion(){
        
        return getFitnessPoblacion() / numPoblacion;
    }   
    
    public double getFitnessPoblacion(){
        double fitness = 0;
        
        for (Individuo individuo : poblacion) {
            fitness += individuo.getFitness();
        }
        
        return fitness;
    }    
}
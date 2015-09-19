import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author luisenriquezamudiocervantes
 */
public class Individuo implements Comparable<Individuo> {

    private final double rangoMinimoX;
    private final double rangoMaximoX;
    private final double rangoMinimoY;
    private final double rangoMaximoY;
    
    private final int longitudX;
    private final int longitudY;
    private int[] individuo;
    
    private String tipoFuncion;
    private boolean ackley;
    private boolean rosenbrock;
    
    private Individuo hijo1;
    private Individuo hijo2;
    
    private double fitness ;

    public Individuo(int longitudX, int longitudY, double rangoMinimoX, double  rangoMaximoX,double rangoMinimoY,  double rangoMaximoY, String tipoFuncion) {
        this.longitudX = longitudX;
        this.longitudY = longitudY;
        this.rangoMaximoX = rangoMaximoX;
        this.rangoMinimoX = rangoMinimoX;
        this.rangoMaximoY = rangoMaximoY;
        this.rangoMinimoY = rangoMinimoY;
        
        this.tipoFuncion = tipoFuncion;
        
        if(this.tipoFuncion == "ackley"){
            this.ackley = true;
            this.rosenbrock = false;
            
        }else if(this.tipoFuncion == "rosenbrock"){
            this.ackley = false;
            this.rosenbrock = true;
        }
        
        this.hijo1 = null;
        this.hijo2 = null;
        
        individuo = new int[this.longitudX + this.longitudY];
        
    }

    public void generaAleatorio() {
        Random rand = new Random();
        
        for (int i = 0; i < individuo.length; i++) {
            individuo[i] = rand.nextInt(2);
        }
        
        this.generaFitness();
    }
    
    public void cruzaMuta(Individuo ind, double probCruza, double probMutacion){
        Random rand = new Random();
        
        if(rand.nextDouble() > probCruza ){
            return;
        }
        
        int ptoCruce = rand.nextInt(individuo.length ) ;
        if(ptoCruce == 0){
            ptoCruce = 1;
        }
        
        this.hijo1 = new Individuo(longitudX, longitudY, rangoMinimoX, rangoMaximoX, rangoMinimoY, rangoMaximoY, this.tipoFuncion);
        this.hijo2 = new Individuo(longitudX, longitudY,rangoMinimoX, rangoMaximoX, rangoMinimoY, rangoMaximoY, this.tipoFuncion);
        
        int[] genesHijo1 = new int[longitudX+longitudY];
        int[] genesHijo2 = new int[longitudX+longitudY];
        
        
        // Generamos hijo 1
        int[] tmp1 = Arrays.copyOfRange(individuo, 0, ptoCruce);
        int[] tmp2 = Arrays.copyOfRange(ind.getIndividuo(), ptoCruce, ind.getIndividuo().length);
        System.arraycopy(tmp1, 0, genesHijo1, 0, tmp1.length);
        System.arraycopy(tmp2, 0, genesHijo1, tmp1.length, tmp2.length);
        
        this.hijo1.setIndividuo(genesHijo1);
        this.hijo1.muta(probMutacion);
        this.hijo1.generaFitness();
        
        // Generamos hijo 2
        tmp1 = Arrays.copyOfRange(individuo, ptoCruce, individuo.length);
        tmp2 = Arrays.copyOfRange(ind.getIndividuo(), 0, ptoCruce);
        System.arraycopy(tmp1, 0, genesHijo2, 0, tmp1.length);
        System.arraycopy(tmp2, 0, genesHijo2, tmp1.length, tmp2.length);
        
        this.hijo2.setIndividuo(genesHijo2);
        this.hijo2.muta(probMutacion);
        this.hijo1.generaFitness();
        
    }
    
    public Individuo getHijo1(){
        return this.hijo1;
    }
    
    public Individuo getHijo2(){
        return this.hijo2;
    }
    
    public void muta(double probMutacion){
        Random rand = new Random();
        double prob;
        for (int i = 0; i < individuo.length; i++) {
            prob = rand.nextDouble();
             if(prob <= probMutacion ){
                 individuo[i] = (individuo[i] +1 ) % 2;
             }
        }
    }
 
    public int[] getIndividuo() {

        return individuo;
    }
    
    public void setIndividuo(int[] individuo) {

        this.individuo = individuo;
    }
    
    public void generaFitness(){
        
        if(ackley){
            fitness = calculaAckley(getValorX(), getValorY());
            
        }else if(rosenbrock){
            fitness = calculaRosenbrock(getValorX(), getValorY());
        }else{
            fitness = 0;
        }
        
        
        
    }
    
    public double getFitness(){
        return fitness;
    }
    
    
    
    private double calculaRosenbrock(double x, double y){
        return  Math.pow( (1-x) , 2) + (100 * Math.pow( ( y - Math.pow(x, 2)), 2)) ;
    }
    
    private double calculaAckley(double x, double y){
        
        double n = 2;
        double c1 = 1;
        double c2 = 0.2;
        double c3 = 2* Math.PI;
        
        return (-1 * c1) * Math.exp((-1 * c2) * Math.sqrt( (1 / n) * (Math.pow(x, 2) + Math.pow(y, 2)) ))  - Math.exp( ((1/n) * (Math.cos(c3*x) + Math.cos(c3*y)))) + c1 + Math.E;
        
    }
    
    public String getPunto(){
        return "("+getValorX()+","+getValorY()+")";
    }

    public double getValorX() {
        String valorX = "";

        for (int i = 0; i < this.longitudX; i++) {
            valorX += individuo[i];
        }

        return rangoMinimoX + (Integer.parseInt(valorX, 2) * ((rangoMaximoX - rangoMinimoX) / (Math.pow(2, longitudX) - 1)));
    }

    public double getValorY() {

        String valorY = "";

        for (int i = longitudX; i < individuo.length; i++) {
            valorY += individuo[i];
        }

        return rangoMinimoY + (Integer.parseInt(valorY, 2) * ((rangoMaximoY - rangoMinimoY) / (Math.pow(2, longitudY) - 1)));

    }

    public String getTipoFuncion() {
        return tipoFuncion;
    }
    
    @Override
    public String toString() {
        String genotipo = "";
        for (int e = 0; e < individuo.length; e++) {
            genotipo += individuo[e];
        }

        return genotipo;
    }

    @Override
    public int compareTo(Individuo o) {
        Double obj1 = this.getFitness();
        Double obj2 = o.getFitness();
        
        return obj2.compareTo(obj1);
    }

}

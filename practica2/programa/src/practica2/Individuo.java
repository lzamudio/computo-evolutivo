package practica2;

import java.util.Random;


/**
 *
 * @author luisenriquezamudiocervantes
 */
public class Individuo implements Comparable<Individuo> {
    
    private Individuo hijo1;
    private Individuo hijo2;

    private Xi[] xis;
    private int[] individuo;
    private String tipoFunction ;
    private double[][] matriz;
    
    private double fitness ;
    
    
    public Individuo(Xi[] xis, String tipoFunction) {
        
        this.tipoFunction = tipoFunction;
        this.xis = xis;
        int totalLong = 0;
        
        for (Xi xi : xis) {
            totalLong += xi.getLongitud();
        }
         
        this.individuo = new int[totalLong];
        
        if(this.tipoFunction == "trincherasShekel​"){
            Random rand = new Random();

            matriz = new double[2][25];
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    matriz[i][j] = rand.nextDouble();
                }
            }
        }
        
    }
     
    public void cruzaMuta(Individuo ind, double probCruza,double probMutacion ){
        Random rand = new Random();
        
        if(rand.nextDouble() > probCruza ){
            return;
        }
        
        int ptoCruce1 = rand.nextInt(this.individuo.length);
        int ptoCruce2 = rand.nextInt(this.individuo.length);

        if (ptoCruce1 >= ptoCruce2) {
            int t = ptoCruce1;
            ptoCruce1 = ptoCruce2;
            ptoCruce2 = t;
        }
        
        
        this.hijo1 = new Individuo(this.xis, this.tipoFunction );
        this.hijo2 = new Individuo(this.xis, this.tipoFunction);

        int[] genesHijo1 = new int[this.individuo.length];
        int[] genesHijo2 = new int[this.individuo.length];
  
        // Generamos hijo 1
        System.arraycopy(this.individuo, 0, genesHijo1, 0, ptoCruce1);
        System.arraycopy(ind.getIndividuo(), ptoCruce1, genesHijo1, ptoCruce1, (ptoCruce2- ptoCruce1));
        System.arraycopy(this.individuo, ptoCruce2, genesHijo1, ptoCruce2, (this.individuo.length -ptoCruce2));
        
        
        this.hijo1.setIndividuo(genesHijo1);
        this.hijo1.muta(probMutacion);
        this.hijo1.generaFitness();
        

        // Generamos hijo 2
        System.arraycopy(ind.getIndividuo(), 0, genesHijo2, 0, ptoCruce1);
        System.arraycopy(this.individuo, ptoCruce1, genesHijo2, ptoCruce1, (ptoCruce2- ptoCruce1));
        System.arraycopy(ind.getIndividuo(), ptoCruce2, genesHijo2, ptoCruce2, (ind.getIndividuo().length -ptoCruce2));
          
        this.hijo2.setIndividuo(genesHijo2);
        this.hijo2.muta(probMutacion);
        this.hijo2.generaFitness();
        
    }
    
    public int[] getIndividuo() {

        return individuo;
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
        for (int i = 0; i < this.individuo.length; i++) {
            prob = rand.nextDouble();
             if(prob <= probMutacion ){
                 this.individuo[i] = (this.individuo[i] +1 ) % 2;
             }
        }
    }
    
    public void setIndividuo(int[] individuo) {

        this.individuo = individuo;
    }
    
    public void generaFitness(){
        
        switch(tipoFunction){
            case "modeloEsferico":
                fitness = calculaModeloEsferico();
                break;
            case "rosenbrock":
                fitness = calculaRosenbrock();
                break;
            case "funcionPaso":
                fitness = calculaFuncionPaso();
                break;
            case "funcionCuartica":
                fitness = calculaFuncionCuartica();
                break;
            case "trincherasShekel​":
                fitness = calculaTrincherasShekel​();
                break;
            default:
                fitness = 0;
        }
        
    }
    
    public double getFitness(){
        
        return fitness;
    }
    
    private double calculaTrincherasShekel​(){
        double r = 0;
        
        for (int j = 0; j < 25; j++) {
            r += 1 /  ((j+1) + Math.pow((this.getValorXi(1)-matriz[0][j]),6) + Math.pow((this.getValorXi(2)-matriz[1][j]),6));
//            r += 1 /  (j+1);
        }
        
        r = Math.pow((0.002 + r), -1);
        
        return r;
    }
    
    private double calculaModeloEsferico(){
        double r = 0;
        
        for (int i = 0; i < xis.length; i++) {
            
            r += Math.pow(this.getValorXi( (i+1) ), 2);
        }
        
        return r;
    }
    
    private double calculaRosenbrock(){
        
        return  (100 * Math.pow(Math.pow(this.getValorXi(1), 2) - this.getValorXi(2),2)) + (Math.pow(1 - this.getValorXi(1),2));
    }
    
    private double calculaFuncionPaso(){
        double r = 0;
        
        for (int i = 0; i < xis.length; i++) {
            
            r += Math.floor(this.getValorXi((i+1)));
        }
        
        return r;
    }
    
    private double calculaFuncionCuartica(){
        Random rand = new Random();
        double r = 0;
        
        for (int i = 0; i < xis.length; i++) {
            
            
            r += Math.pow(i*this.getValorXi((i+1)),4);
        }
        
//        return r+rand.nextGaussian();
        return r;
    }
    
    public void generaAleatorio() {
        Random rand = new Random();
        
        for (int i = 0; i < individuo.length; i++) {
            individuo[i] = rand.nextInt(2);
        }  
        
        this.generaFitness();
    }
    
    public double getValorXi(int i){
        String valorXi = "";
        int inicial = 0;
        
        for (int j = 0; j < xis.length; j++) {
            if(j< (i-1)){
                inicial += xis[j].getLongitud();
            }else{
                break;
            }
        }
        
        for (int j = inicial; j < (inicial+xis[(i-1)].getLongitud()); j++) {
            valorXi += individuo[j];    
        }
        
        
        return xis[(i-1)].getRangoMinimo() + (Integer.parseInt(valorXi, 2) * ((xis[(i-1)].getRangoMaximo() - xis[(i-1)].getRangoMinimo()) / (Math.pow(2, xis[(i-1)].getLongitud()) - 1)));
        
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

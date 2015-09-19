package practica2;

import java.util.Scanner;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;



/**
 *
 * @author luisenriquezamudiocervantes
 */
public class Practica2 {

    Poblacion poblacion ;
    Individuo individuo;
    DefaultCategoryDataset data1;
    DefaultCategoryDataset data2;
    
    public static void main(String[] args) {
        Practica2 p2 = new Practica2();
        boolean menu = true;
        
        while(menu){
            System.out.println("Escribe el número de la opción que deseas:");
            System.out.println("1.- Modelo Esférico");
            System.out.println("2.- Función de Rosenbrock generalizada");
            System.out.println("3.- Función de paso");
            System.out.println("4.- Funcioón cuártica con ruido");
            System.out.println("5.- Trincheras de Shekel");
            System.out.println("6.- Salir");
            System.out.print("> ");
            
            Scanner s = new Scanner(System.in);
            
            switch(s.nextInt()){
                case 1:
                    System.out.println(" Generando gráfica . . .");
                    p2.modeloEsferico();
                    System.out.println(" Gráfica creada");
                    break;
                case 2:
                    System.out.println(" Generando gráfica . . .");
                    p2.rosenbrock();
                    System.out.println(" Gráfica creada");
                    break;
                case 3:
                    System.out.println(" Generando gráfica . . .");
                    p2.funcionPaso();
                    System.out.println(" Gráfica creada");
                    break;
                case 4:
                    System.out.println(" Generando gráfica . . .");
                    p2.funcionCuartica();
                    System.out.println(" Gráfica creada");
                    break;
                case 5:
                    System.out.println(" Generando gráfica . . .");
                    p2.trincherasShekel();
                    System.out.println(" Gráfica creada");
                    break;
                case 6:
                    menu = false;
                    break;
            }
        }
    }
    
    public void grafica(String titulo, DefaultCategoryDataset data){
        
        final JFreeChart chart = ChartFactory.createLineChart(
            titulo,
            "Generación",
            "Fitness",
            data,                   
            PlotOrientation.VERTICAL,
            true,                    
            true,                    
            false
        );
        
        ChartFrame frame = new ChartFrame("Práctica 2", chart);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }
    
    public void modeloEsferico(){
        Xi x1 = new Xi(22,-15, 15);
        Xi x2 = new Xi(22,-15, 15);
        Xi x3 = new Xi(22,-15, 15);
        
        Xi[] xis = new Xi[3];
        xis[0] = x1;
        xis[1] = x2;
        xis[2] = x3;
        
        data1 = new  DefaultCategoryDataset();
        data2 = new  DefaultCategoryDataset();
        
        poblacion = new Poblacion(150, xis, "modeloEsferico");
        
        for (int i = 0; i < 500; i++) {
            poblacion.seleccionVasconcelos();
            
            individuo = poblacion.getIndividuoMejor();
            data1.setValue(individuo.getFitness(), "Valor del Fitness", ""+i);
            data2.setValue(poblacion.getFitnessPromedioPoblacion(), "Valor del Fitness", ""+i);
            poblacion.cambiaGeneracion();
        }
        
        grafica("Fitness de mejor individuo de cada generación: Modelo Esférico", data1);
        grafica("Fitness promedio de cada generación: Modelo Esférico", data2);
        
    }
    
    public void rosenbrock(){
        Xi x1 = new Xi(22,-15, 15);
        Xi x2 = new Xi(22,-15, 15);
        
        Xi[] xis = new Xi[2];
        xis[0] = x1;
        xis[1] = x2;
        
        data1 = new  DefaultCategoryDataset();
        data2 = new  DefaultCategoryDataset();
        
        poblacion = new Poblacion(150, xis, "rosenbrock");
        
        for (int i = 0; i < 500; i++) {
            poblacion.seleccionVasconcelos();
            
            individuo = poblacion.getIndividuoMejor();
            data1.setValue(individuo.getFitness(), "Valor del Fitness", ""+i);
            data2.setValue(poblacion.getFitnessPromedioPoblacion(), "Valor del Fitness", ""+i);
            poblacion.cambiaGeneracion();
        }
        
        grafica("Fitness de mejor individuo de cada generación: Función de Rosenbrock generalizada:", data1);
        grafica("Fitness promedio de cada generación: Función de Rosenbrock generalizada:", data2);
    }
    
    public void funcionPaso(){
        Xi x1 = new Xi(22,-15, 15);
        Xi x2 = new Xi(22,-15, 15);
        Xi x3 = new Xi(22,-15, 15);
        Xi x4 = new Xi(22,-15, 15);
        Xi x5 = new Xi(22,-15, 15);
        
        Xi[] xis = new Xi[5];
        xis[0] = x1;
        xis[1] = x2;
        xis[2] = x3;
        xis[3] = x4;
        xis[4] = x5;
        
        data1 = new  DefaultCategoryDataset();
        data2 = new  DefaultCategoryDataset();
        
        poblacion = new Poblacion(150, xis, "funcionPaso");
        
        for (int i = 0; i < 500; i++) {
            poblacion.seleccionVasconcelos();
            
            individuo = poblacion.getIndividuoMejor();
            data1.setValue(individuo.getFitness(), "Valor del Fitness", ""+i);
            data2.setValue(poblacion.getFitnessPromedioPoblacion(), "Valor del Fitness", ""+i);
            poblacion.cambiaGeneracion();
        }
        
        grafica("Fitness de mejor individuo de cada generación: Función de paso:", data1);
        grafica("Fitness promedio de cada generación: Función de paso:", data2);
    }
    
    public void funcionCuartica(){
        
        Xi[] xis = new Xi[30];
        
        for (int i = 0 ; i < xis.length; i++) {
            xis[i] = new Xi(22, -15, 15);
            
        }
        
        data1 = new  DefaultCategoryDataset();
        data2 = new  DefaultCategoryDataset();
        
        poblacion = new Poblacion(150, xis, "funcionCuartica");
        
        for (int i = 0; i < 500; i++) {
            poblacion.seleccionVasconcelos();
            
            individuo = poblacion.getIndividuoMejor();
            
            data1.setValue(individuo.getFitness(), "Valor del Fitness", ""+i);
            data2.setValue(poblacion.getFitnessPromedioPoblacion(), "Valor del Fitness", ""+i);
            poblacion.cambiaGeneracion();
        }
        
        grafica("Fitness de mejor individuo de cada generación: Función cuártica con ruido:", data1);
        grafica("Fitness promedio de cada generación: Función cuártica con ruido:", data2);
        
    }
    
    public void trincherasShekel​(){
        
        Xi[] xis = new Xi[30];
        
        for (int i = 0 ; i < xis.length; i++) {
            xis[i] = new Xi(22, -15, 15);
            
        }
        
        data1 = new  DefaultCategoryDataset();
        data2 = new  DefaultCategoryDataset();
        
        poblacion = new Poblacion(150, xis, "trincherasShekel​");
        
        for (int i = 0; i < 500; i++) {
            poblacion.seleccionVasconcelos();
            
            individuo = poblacion.getIndividuoMejor();
            data1.setValue(individuo.getFitness(), "Valor del Fitness", ""+i);
            data2.setValue(poblacion.getFitnessPromedioPoblacion(), "Valor del Fitness", ""+i);
            poblacion.cambiaGeneracion();
        }
        
        grafica("Fitness de mejor individuo de cada generación: Trincheras de Shekel​:", data1);
        grafica("Fitness promedio de cada generación: Trincheras de Shekel​:", data2);
        
    }
    
}

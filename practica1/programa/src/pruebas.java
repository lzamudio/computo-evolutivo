import java.util.Scanner;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class pruebas {
    
    Poblacion pob ;
    Individuo ind;
    DefaultCategoryDataset data1;
    DefaultCategoryDataset data2;
    DefaultCategoryDataset data3;
    DefaultCategoryDataset data4;
    DefaultCategoryDataset data5;

    public static void main(String[] args) { 
        
        pruebas p = new pruebas();
        
        boolean menu = true;
        
        while(menu){
            System.out.println("Escribe el número de la opción que deseas:");
            System.out.println("1.- Función Rosenbrock - Selección Ruleta");
            System.out.println("2.- Función Rosenbrock - Selección Estocástico Universal");
            System.out.println("3.- Función Rosenbrock - Selección Cuatro Torneo");
            System.out.println("4.- Función Rosenbrock - Selección Vasconcelos");
            System.out.println("5.- Función Ackley - Selección Ruleta");
            System.out.println("6.- Función Ackley - Selección Estocástico Universal");
            System.out.println("7.- Función Ackley - Selección Cuatro Torneo");
            System.out.println("8.- Función Ackley - Selección Vasconcelos");
            System.out.println("9.- Salir");
            System.out.print("> ");
            
            Scanner s = new Scanner(System.in);
            int opcion = s.nextInt();
            
            switch(opcion){
                case 1:
                case 2:
                case 3:
                case 4:    
                    System.out.println(" Generando gráfica . . .");
                    p.rosenbrock(opcion);
                    System.out.println(" Gráfica creada");
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    System.out.println(" Generando gráfica . . .");
                    p.ackley(opcion);
                    System.out.println(" Gráfica creada");
                    break;
                case 9:
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
    
    
    public void rosenbrock(int opcion ){
        data1 = new  DefaultCategoryDataset();
        data2 = new  DefaultCategoryDataset();
        data3 = new  DefaultCategoryDataset();
        data4 = new  DefaultCategoryDataset();
        data5 = new  DefaultCategoryDataset();
        
        pob = new Poblacion(100, 10, 10, -2, 2, -3, 1, "rosenbrock");
        
        String metodo = "";
        switch(opcion){
            case 1:
                metodo = "Selección Ruleta";
                break;
            case 2:
                metodo = "Selección Estocástico Universal";
                break;
            case 3:
                metodo = "Selección 4-Torneo";
                break;
            case 4:
                metodo = "Selección Vasconcelos";
                break;
                
        }
        
        for (int i = 1; i <= 500; i++) {
            switch (opcion) {
                case 1:
                    pob.seleccionRuleta();
                    break;
                case 2:
                    pob.seleccionEstocasticoUniversal();
                    break;
                case 3:
                    pob.seleccionCuatroTorneo();
                    break;
                case 4:
                    pob.seleccionVasconcelos();
                    break;

            }
            
            ind = pob.getIndividuoMejor();
            data1.setValue(ind.getFitness(), "Valor del Fitness", ""+i);
            data2.setValue(pob.getFitnessPromedioPoblacion(), "Valor del Fitness", ""+i);
            data3.setValue(pob.getDistancia(ind.getValorX(), ind.getValorY(), 1, 1), "Valor del Fitness", ""+i);
            data4.setValue(pob.getDistanciaPromedioOptimo(1, 1), "Valor del Fitness", ""+i);
            data5.setValue(pob.getDistanciaPromedioOptimoGeneracion(), "Valor del Fitness", ""+i);
            pob.cambiaGeneracion();
        }
        
        grafica("El fitness del mejor individuo de la población en cada generación - "+metodo, data1);
        grafica("El fitness promedio de la población en cada generación. Selección - "+metodo, data2);
        grafica("La distancia del mejor individuo de cada generación al óptimo - "+metodo, data3);
        grafica("La distancia promedio de la población al óptimo - "+metodo, data4);
        grafica("Distancia promedio de la población al mejor individuo de cada generación - "+metodo, data5);
        

    }
    
    public void ackley(int opcion ){
        data1 = new  DefaultCategoryDataset();
        data2 = new  DefaultCategoryDataset();
        data3 = new  DefaultCategoryDataset();
        data4 = new  DefaultCategoryDataset();
        data5 = new  DefaultCategoryDataset();
        
        pob = new Poblacion(100, 23, 23, -30, 30, -30, 30, "ackley");
        
        String metodo = "";
        switch(opcion){
            case 5:
                metodo = "Selección Ruleta";
                break;
            case 6:
                metodo = "Selección Estocástico Universal";
                break;
            case 7:
                metodo = "Selección 4-Torneo";
                break;
            case 8:
                metodo = "Selección Vasconcelos";
                break;
                
        }
        
        for (int i = 1; i <= 500; i++) {
            switch (opcion) {
                case 5:
                    pob.seleccionRuleta();
                    break;
                case 6:
                    pob.seleccionEstocasticoUniversal();
                    break;
                case 7:
                    pob.seleccionCuatroTorneo();
                    break;
                case 8:
                    pob.seleccionVasconcelos();
                    break;

            }
            
            ind = pob.getIndividuoMejor();
            data1.setValue(ind.getFitness(), "Valor del Fitness", ""+i);
            data2.setValue(pob.getFitnessPromedioPoblacion(), "Valor del Fitness", ""+i);
            data3.setValue(pob.getDistancia(ind.getValorX(), ind.getValorY(), 1, 1), "Valor del Fitness", ""+i);
            data4.setValue(pob.getDistanciaPromedioOptimo(1, 1), "Valor del Fitness", ""+i);
            data5.setValue(pob.getDistanciaPromedioOptimoGeneracion(), "Valor del Fitness", ""+i);
            pob.cambiaGeneracion();
        }
        
        grafica("El fitness del mejor individuo de la población en cada generación - "+metodo, data1);
        grafica("El fitness promedio de la población en cada generación. Selección - "+metodo, data2);
        grafica("La distancia del mejor individuo de cada generación al óptimo - "+metodo, data3);
        grafica("La distancia promedio de la población al óptimo - "+metodo, data4);
        grafica("Distancia promedio de la población al mejor individuo de cada generación - "+metodo, data5);
    }

}

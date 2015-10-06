import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.control.*;
import robocode.control.events.*;


 //
// Application that demonstrates how to run two sample robots in Robocode using the
// RobocodeEngine from the robocode.control package.
//
// @author Flemming N. Larsen
//
public class GeneraBatalla {

    public static void main(String[] args) {
        AlgoritmoGenetico ag = new AlgoritmoGenetico();
        ag.poblacionInicial();
        
        RobocodeEngine.setLogMessagesEnabled(false);
        RobocodeEngine engine = new RobocodeEngine();
        engine.addBattleListener(new Registros());
        engine.setVisible(false);

        for (int e  = 0; e < 90000; e++) {
            
            int numberOfRounds = 1;
            BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); 
            Random rd = new Random();
            String robots = "ag.Frankenstein*,ag.Frankenstein*,ag.Frankenstein*,ag.Frankenstein*";
            switch(rd.nextInt(5)){
                case 0:
                    robots +=",sample.Corners";
                    break;
                case 1:
                    robots +=",sample.Crazy";
                    break;
                case 2:
                    robots +=",sample.Fire";
                    break;
                case 3:
                    robots +=",sample.TrackFire";
                    break;
                case 4:
                    robots +=",sample.Walls";
                    break;
            }
            System.out.println(robots);
            for (int i = 1; i <= 25; i++) {
                RobotSpecification[] selectedRobots = engine.getLocalRepository(robots);
                BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
                engine.runBattle(battleSpec, true); 
            }
            ag.obtenPoblacion();
            ag.seleccionVasconcelos();
            ag.escribeArchivo("",System.getProperty("user.dir") + "/generacion.txt", false);
        }
        
        engine.close();
        System.exit(0);
    }
}

class Registros extends BattleAdaptor {

    
    public void onBattleCompleted(BattleCompletedEvent e) {
        System.out.println("-- Battle has completed --");

        for (robocode.BattleResults result : e.getSortedResults()) {
            buscaRobot(result.getTeamLeaderName(), result.getScore());
        }
        
        escribeArchivo("", System.getProperty("user.dir") + "/resultados.txt", false);
    }

    public void onBattleMessage(BattleMessageEvent e) {
//        System.out.println("Msg > " + e.getMessage());
    }

    public void onBattleError(BattleErrorEvent e) {
        System.out.println("Err > " + e.getError());
    }
    
    public void buscaRobot(String name, int score){
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/resultados.txt")));
            String line = "";
            while((line = reader.readLine()) != null){
                if(line.contains(name)){
                    line = line.replace(name, Integer.toString(score));
                    escribeArchivo(line+"\n", System.getProperty("user.dir") + "/generacion.txt", true);
                    break;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Registros.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(Registros.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
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
}

package ag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.DeathEvent;
import robocode.Robot;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class Frankenstein extends Robot {

    private int[] acciones;
    private int cantidadAcciones = 16;

    public Frankenstein() {

        acciones = new int[cantidadAcciones * 2];
        String line = getAndRemoveFirstLine();
//        String line = 9|1|5|5|8|4|0|9|5|9|9|9|6|3|7|1|-1|10|218|73|3|63|12|-1|63|-1|-1|-1|754|283|74|195;
        StringTokenizer tokens = new StringTokenizer(line, "|");
        int e = 0;
        while (tokens.hasMoreTokens() && e < acciones.length) {
            acciones[e++] = Integer.parseInt(tokens.nextToken());
        }
    }

    public void run() {
        
        while (true) {
            for (int i = 0; i < 4; i++) {
                ejecutaAccion(acciones[i], acciones[i + cantidadAcciones]);
            }
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        for (int i = 4; i < 8; i++) {
            ejecutaAccion(acciones[i], acciones[i + cantidadAcciones]);
        }
    }

    public void onHitByBullet(HitByBulletEvent e) {
        for (int i = 8; i < 12; i++) {
            ejecutaAccion(acciones[i], acciones[i + cantidadAcciones]);
        }
    }

    public void onHitWall(HitWallEvent e) {
        for (int i = 12; i < 16; i++) {
            ejecutaAccion(acciones[i], acciones[i + cantidadAcciones]);
        }
    }

    public void onDeath(DeathEvent event) {
        escribeResultados();
    }
    
    public void onWin(WinEvent event){
        escribeResultados();
    }
    
    private void escribeResultados(){
        String tmp = "";
        for (int a : acciones) {
            tmp += a+"|";
        }
        tmp += this.getName()+"\n";
        escribeArchivo(tmp, System.getProperty("user.dir") + "/resultados.txt", true);
    }
    
    private void ejecutaAccion(int accion, int parametro) {
        switch (accion) {
            case 0:
                turnLeft(parametro);
                break;
            case 1:
                turnRight(parametro);
                break;
            case 2:
                turnRadarRight(parametro);
                break;
            case 3:
                turnRadarLeft(parametro);
                break;
            case 4:
                turnGunLeft(parametro);
                break;
            case 5:
                turnGunRight(parametro);
                break;
            case 6:
                ahead(parametro);
                break;
            case 7:
                back(parametro);
                break;
            case 8:
                fire(parametro);
                break;
            case 9:
                doNothing();
                break;
        }
    }
    
    private void escribeArchivo(String text, String file, boolean append){
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

    private String getAndRemoveFirstLine() {

        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/genes.txt")));

            String line = "";
            String lineReturn = "";
            String nueva = "";
            int cont = 1;
            while((line = reader.readLine()) != null){
                
                if(cont == 1){
                    lineReturn = line;
                }else{
                    if(line.length() > 0){
                        nueva += line+"\n";
                    }
                    
                }
                cont++;
            }
            
            escribeArchivo(nueva, System.getProperty("user.dir") + "/genes.txt", false);

            return lineReturn;
        } catch (IOException ex) {
            Logger.getLogger(Frankenstein.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(Frankenstein.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
        return "";
    }

}

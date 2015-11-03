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
import java.util.UUID;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class Frankenstein extends Robot {

    private static int[] acciones;
    private int cantidadAcciones = 12;
    private static boolean inicializado = false;


    private void init(){
        acciones = new int[cantidadAcciones * 2];
        String line = getAndRemoveFirstLine();
        StringTokenizer tokens = new StringTokenizer(line, "|");
        int e = 0;
        while (tokens.hasMoreTokens() && e < acciones.length) {
            acciones[e++] = Integer.parseInt(tokens.nextToken());
        }
        inicializado = true;
    }

    public void run() {
        if (!inicializado){
            init();
        }
        
        while (true) {
            for (int i = 0; i < (cantidadAcciones/4)*1; i++) {
                ejecutaAccion(acciones[i], acciones[i + cantidadAcciones]);
            }
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        for (int i = (cantidadAcciones/4)*1; i < (cantidadAcciones/4)*2; i++) {
            ejecutaAccion(acciones[i], acciones[i + cantidadAcciones]);
        }
    }

    public void onHitByBullet(HitByBulletEvent e) {
        for (int i = (cantidadAcciones/4)*2; i < (cantidadAcciones/4)*3; i++) {
            ejecutaAccion(acciones[i], acciones[i + cantidadAcciones]);
        }
    }

    public void onHitWall(HitWallEvent e) {
        for (int i = (cantidadAcciones/4)*3; i < (cantidadAcciones/4)*4; i++) {
            ejecutaAccion(acciones[i], acciones[i + cantidadAcciones]);
        }
    }

    public void onDeath(DeathEvent event) {
        escribeResultados();
    }
    
    public void onWin(WinEvent event){
        escribeResultados();
    }


    public void trackFire() {
        double absoluteBearing = getHeading();
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

        if (Math.abs(bearingFromGun) <= 3) {
            turnGunRight(bearingFromGun);
            
            if (getGunHeat() == 0) {
                fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
            }
        } 
        
        else {
            turnGunRight(bearingFromGun);
        }
        
        if (bearingFromGun == 0) {
            scan();
        }
    }
    
    private void escribeResultados(){

        if((getNumRounds() - 1) == getRoundNum()){
            String tmp = "";
            for (int a : acciones) {
                tmp += a+"|";
            }
            tmp += this.getName()+"\n";
            escribeArchivo(tmp, System.getProperty("user.dir") + "/resultados.txt", true);
        }
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
package ag;
import robocode.*;
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
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * RobotLuis1 - a robot by (your name here)
 */
public class RobotLuis1 extends Robot
{
	private static int[] acciones;
    private int cantidadAcciones = 12;
    private static boolean inicializado = false;


    private void init(){
        acciones = new int[cantidadAcciones * 2];
        //String line = getAndRemoveFirstLine();
        String line = "5|5|5|8|8|9|5|9|1|1|4|3|165|141|39|3|2|-1|-2|-1|150|267|310|-263";
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
        //escribeResultados();
    }
    
    public void onWin(WinEvent event){
        //escribeResultados();
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
                fire(1);
                break;
        }
    }
    
   
}

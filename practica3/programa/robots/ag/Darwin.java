/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ag;


import robocode.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;


//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Darwin - a robot by (your name here)
 */
public class Darwin extends AdvancedRobot
{   
        double valor = 0;
        Individuo individuo = new Individuo();
        String[] arregloFunciones;
        String parametros; 

        public Darwin(){        
            individuo.iniciaCuerpo(513);
            individuo.cuerpo = "010111111101101011110010010101111101110101000001011101100100100000110110011000011001111100011010111011110101100000011110110111011100011000100011101000100001000000011101101011000111100011011101011101011110011001100100100001001101101101100111000010100001100000111001100101111110111010000011011000001101101111100011110001010011100111000001110110100100110101111110101010010100100101100001110011111011100111100010111100011111011110110100000111000111010001111110100011001010000001111111101111000110110011101001111111001010110001";
            individuo.codicacionFuncionesPrincipales();
            arregloFunciones = individuo.arregloFunciones11(individuo.getCadena11Funciones(individuo.funcionRun));
            parametros = individuo.getCadenaParametros(individuo.funcionRun);
        }
                
	public void run() {        
         while(true) {
                 for(int i = 0; i < arregloFunciones.length ;i++){
                    String param = "";
                    int fire = 0;
                    switch(arregloFunciones[i]){
                        case "0001":
                            for(int j = 0;j<13;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(100,600,13,param);
                                ahead(valor);
                            break;
                        case "0010":
                            for(int j = 13;j<26;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(100,600,13,param);
                                back(valor);
                            break;
                        case "0011":
                                doNothing();
                            break;
                        case "0100":
                            for(int j = 26;j<28;j++){
                                param+=parametros.charAt(j);
                            }
                            fire = Integer.parseInt(param,2);
                                fire(fire);
                            break;
                        case "0101":
                                scan();
                            break;
                        case "0110":
                            for(int j = 28;j<37;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunLeft(valor);
                            break;
                        case "0111":
                            for(int j = 37;j<46;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunRight(valor);
                            break;
                        case "1000":
                            for(int j = 46;j<55;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnLeft(valor);
                            break;
                        case "1001":
                            for(int j = 55;j<64;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnRight(valor);
                            break;
                        case "1010":
                            for(int j = 64;j<73;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnRadarRight(valor);
                            break;
                        case "1011":
                            for(int j = 73;j<82;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnRadarLeft(valor);
                            break;
                        case "1100":
                            for(int j = 26;j<28;j++){
                                param+=parametros.charAt(j);
                            }
                            fire = Integer.parseInt(param,2);
                                fire(fire);
                            break;
                        case "1101":
                                scan();
                            break;
                        case "1110":
                            for(int j = 28;j<37;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunLeft(valor);
                            break;
                        case "1111":
                            for(int j = 37;j<46;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunRight(valor);
                            break;
                   }
               }       
            }   
    }

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {                
                           for(int i = 0; i < arregloFunciones.length ;i++){
                    String param = "";
                    int fire = 0;
                    switch(arregloFunciones[i]){
                        case "0001":
                            for(int j = 0;j<13;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(100,600,13,param);
                                ahead(valor);
                            break;
                        case "0010":
                            for(int j = 13;j<26;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(100,600,13,param);
                                back(valor);
                            break;
                        case "0011":
                                doNothing();
                            break;
                        case "0100":
                            for(int j = 26;j<28;j++){
                                param+=parametros.charAt(j);
                            }
                            fire = Integer.parseInt(param,2);
                                fire(fire);
                            break;
                        case "0101":
                                scan();
                            break;
                        case "0110":
                            for(int j = 28;j<37;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunLeft(valor);
                            break;
                        case "0111":
                            for(int j = 37;j<46;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunRight(valor);
                            break;
                        case "1000":
                            for(int j = 46;j<55;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnLeft(valor);
                            break;
                        case "1001":
                            for(int j = 55;j<64;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnRight(valor);
                            break;
                        case "1010":
                            for(int j = 64;j<73;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnRadarRight(valor);
                            break;
                        case "1011":
                            for(int j = 73;j<82;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnRadarLeft(valor);
                            break;
                        case "1100":
                            for(int j = 26;j<28;j++){
                                param+=parametros.charAt(j);
                            }
                            fire = Integer.parseInt(param,2);
                                fire(fire);
                            break;
                        case "1101":
                                scan();
                            break;
                        case "1110":
                            for(int j = 28;j<37;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunLeft(valor);
                            break;
                        case "1111":
                            for(int j = 37;j<46;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunRight(valor);
                            break;
                   }
               }       
    }
	

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {        
                                    for(int i = 0; i < arregloFunciones.length ;i++){
                    String param = "";
                    int fire = 0;
                    switch(arregloFunciones[i]){
                        case "0001":
                            for(int j = 0;j<13;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(100,600,13,param);
                                ahead(valor);
                            break;
                        case "0010":
                            for(int j = 13;j<26;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(100,600,13,param);
                                back(valor);
                            break;
                        case "0011":
                                doNothing();
                            break;
                        case "0100":
                            for(int j = 26;j<28;j++){
                                param+=parametros.charAt(j);
                            }
                            fire = Integer.parseInt(param,2);
                                fire(fire);
                            break;
                        case "0101":
                                scan();
                            break;
                        case "0110":
                            for(int j = 28;j<37;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunLeft(valor);
                            break;
                        case "0111":
                            for(int j = 37;j<46;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunRight(valor);
                            break;
                        case "1000":
                            for(int j = 46;j<55;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnLeft(valor);
                            break;
                        case "1001":
                            for(int j = 55;j<64;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnRight(valor);
                            break;
                        case "1010":
                            for(int j = 64;j<73;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnRadarRight(valor);
                            break;
                        case "1011":
                            for(int j = 73;j<82;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnRadarLeft(valor);
                            break;
                        case "1100":
                            for(int j = 26;j<28;j++){
                                param+=parametros.charAt(j);
                            }
                            fire = Integer.parseInt(param,2);
                                fire(fire);
                            break;
                        case "1101":
                                scan();
                            break;
                        case "1110":
                            for(int j = 28;j<37;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunLeft(valor);
                            break;
                        case "1111":
                            for(int j = 37;j<46;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunRight(valor);
                            break;
                   }
               }  
            }
	
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {          
                                            for(int i = 0; i < arregloFunciones.length ;i++){
                    String param = "";
                    int fire = 0;
                    switch(arregloFunciones[i]){
                        case "0001":
                            for(int j = 0;j<13;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(100,600,13,param);
                                ahead(valor);
                            break;
                        case "0010":
                            for(int j = 13;j<26;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(100,600,13,param);
                                back(valor);
                            break;
                        case "0011":
                                doNothing();
                            break;
                        case "0100":
                            for(int j = 26;j<28;j++){
                                param+=parametros.charAt(j);
                            }
                            fire = Integer.parseInt(param,2);
                                fire(fire);
                            break;
                        case "0101":
                                scan();
                            break;
                        case "0110":
                            for(int j = 28;j<37;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunLeft(valor);
                            break;
                        case "0111":
                            for(int j = 37;j<46;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunRight(valor);
                            break;
                        case "1000":
                            for(int j = 46;j<55;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnLeft(valor);
                            break;
                        case "1001":
                            for(int j = 55;j<64;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnRight(valor);
                            break;
                        case "1010":
                            for(int j = 64;j<73;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnRadarRight(valor);
                            break;
                        case "1011":
                            for(int j = 73;j<82;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnRadarLeft(valor);
                            break;
                        case "1100":
                            for(int j = 26;j<28;j++){
                                param+=parametros.charAt(j);
                            }
                            fire = Integer.parseInt(param,2);
                                fire(fire);
                            break;
                        case "1101":
                                scan();
                            break;
                        case "1110":
                            for(int j = 28;j<37;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunLeft(valor);
                            break;
                        case "1111":
                            for(int j = 37;j<46;j++){
                                param+=parametros.charAt(j);
                            }
                            valor = individuo.decodificar(40,360,9,param);
                                turnGunRight(valor);
                            break;
                   }
               }  
	}	
   
}


package ag;

import static java.lang.Math.*;
import java.util.*;     
import java.io.IOException;
import java.io.FileWriter; 

	
public class Individuo {

	
	public String pm_binario = ""; 
	public String pc_binario = "";
	public String numero_hijos_binario = "";
	public String cuerpo = "";
        public String funcionRun = "";
        public String funcionOnScannedRobot = "";
        public String funcionOnHitByBullet = "";
        public String funcionOnHIitWall = "";
        public String parametros = "";  
        
	public Float pm_decimal = 0f;
	public Float pc_decimal = 0f;
	public Integer numero_hijos_decimal = 0;
	public Float cuerpo_decimal = 0f;
	public Float fitness = 0f;
	
	public Integer numero_genes = 0;
	public Integer porcion_pastel = 0;

	public Individuo(){
            for (int i = 0; i < 3; i++) {

                //asigna la probabilidad de mutación en bits
		int bit = (Math.random() < 0.3) ? 0 : 1;
		String bit_string = Integer.toString(bit);
		this.pm_binario += bit_string;
		//asigna la probabilidad de cruza en bits
		bit = (Math.random() < 0.6) ? 0 : 1;
		bit_string = Integer.toString(bit);
		this.pc_binario += bit_string;		
		//asigna el numero de hijos maximo que tendra el individuo, que tenga probabilidad alta no significa que tendrá muchos hijos
		//eso se vera a la hora de la cruza ya con el fitness poblacional calculado
		bit = (Math.random() < 0.1) ? 0 : 1;
		bit_string = Integer.toString(bit);
		this.numero_hijos_binario += bit_string;		
            }

		Float c = 0.1f;
		this.pm_decimal = Integer.parseInt(pm_binario,2) * c;
		this.pc_decimal = Integer.parseInt(pc_binario,2) * c;
		this.numero_hijos_decimal = Integer.parseInt(this.numero_hijos_binario,2);		
		
	}


        
        //Asigna a las variables con su respectivos parametros y sus 11 funciones del robot
        public void codicacionFuncionesPrincipales(){
            int contador = 0;
            // aqui creamos al resto del individuo
            for (int i = 0; i <= this.cuerpo.length() ;i++) {
                
                if(i>= 9 && i<135){
                    this.funcionRun += this.cuerpo.charAt(i); 
                }
                if(i>=135 && i<261){
                    this.funcionOnScannedRobot += this.cuerpo.charAt(i);
                }
                if(i>=261 && i<387){
                    this.funcionOnHitByBullet += this.cuerpo.charAt(i);
                }
                if(i>=387 && i<513){
                    this.funcionOnHIitWall += this.cuerpo.charAt(i);
                }
            }
            
        }
        
        //Obtiene la cadena de parametros de cada funcion de la batalla(Run,OnScannedRobot,OnHitByBulet,OnHitWall)
        //para las las funciones que necesiten el parametro
        public String getCadenaParametros(String funcion){
            String param = "";
            for(int i = 0; i<funcion.length();i++){
                if(i>43){
                    param += funcion.charAt(i);
                }
            }
            return param;
        }
        
        
        //Obtiene la cadena que tiene las 11 funciones
        public String getCadena11Funciones(String funcion){
            String funciones111 = "";
            for(int i = 0; i<funcion.length();i++){
                if(i<44){
                    funciones111 += funcion.charAt(i);
                }
            }
            return funciones111;
        }
        
        //Regresa un arreglo donde cada posición viene la función del robot 
        //codificado en binario
        public String[] arregloFunciones11(String funcion){
            String [] funciones11 = new String[11];
            String aux = "";
            int contador = 0;
            
            for(int i = 0; i< funcion.length();i++){
                aux+=funcion.charAt(i);
                if((i+1)%4 == 0 && i !=0){
                    funciones11[contador] = aux;
                    int aux1 = contador+1;
                    //System.out.println("Funcion "  + aux1 +": "+ aux);
                    aux = "";
                    contador++;
                }
            }
            
            return funciones11;
        }

        public ArrayList<String> funcionesParametros(String[] arregloFunciones, String parametros){
            //String [] funcParam = new String[11];
                ArrayList<String> funciones = new ArrayList<String>();
            double valor = 0;
        for(int i = 0; i < arregloFunciones.length ;i++){
            String param = "";
            int fire = 0;
            switch(arregloFunciones[i]){
                case "0001":
                    for(int j = 0;j<13;j++){
                        param+=parametros.charAt(j);
                    }
                    valor = decodificar(100,600,13,param);
                    funciones.add("ahead("+valor+")");
                    break;
                case "0010":
                    for(int j = 13;j<26;j++){
                        param+=parametros.charAt(j);
                    }
                    valor = decodificar(100,600,13,param);
                    funciones.add("back("+valor+")");
                    break;
                case "0011":
                    funciones.add("doNothing()");
                    break;
                case "0100":
                    for(int j = 26;j<28;j++){
                        param+=parametros.charAt(j);
                    }
                    fire = Integer.parseInt(param,2);
                    funciones.add("fire("+fire+")");
                    break;
                case "0101":
                    funciones.add("scan()");
                    break;
                case "0110":
                    for(int j = 28;j<37;j++){
                        param+=parametros.charAt(j);
                    }
                    valor = decodificar(40,360,9,param);
                    funciones.add("turnGunLeft("+valor+")");
                    break;
                case "0111":
                    for(int j = 37;j<46;j++){
                        param+=parametros.charAt(j);
                    }
                    valor = decodificar(40,360,9,param);
                    funciones.add("turnGunRight("+valor+")");
                    break;
                case "1000":
                    for(int j = 46;j<55;j++){
                        param+=parametros.charAt(j);
                    }
                    valor = decodificar(40,360,9,param);
                    funciones.add("turnLeft("+valor+")");
                    break;
                case "1001":
                    for(int j = 55;j<64;j++){
                        param+=parametros.charAt(j);
                    }
                    valor = decodificar(40,360,9,param);
                    funciones.add("turnRight("+valor+")");
                    break;
                case "1010":
                    for(int j = 64;j<73;j++){
                        param+=parametros.charAt(j);
                    }
                    valor = decodificar(40,360,9,param);
                    funciones.add("turnRadarRigth("+valor+")");
                    break;
                case "1011":
                    for(int j = 73;j<82;j++){
                        param+=parametros.charAt(j);
                    }
                    valor = decodificar(40,360,9,param);
                    funciones.add("turnRadarLeft("+valor+")");
                    break;
                case "1100":
                    for(int j = 26;j<28;j++){
                        param+=parametros.charAt(j);
                    }
                    fire = Integer.parseInt(param,2);
                    funciones.add("fire("+fire+")");
                    break;
                case "1101":
                    funciones.add("scan()");
                    break;
                case "1110":
                    for(int j = 28;j<37;j++){
                        param+=parametros.charAt(j);
                    }
                    valor = decodificar(40,360,9,param);
                    funciones.add("turnGunLeft("+valor+")");
                    break;
                case "1111":
                    for(int j = 37;j<46;j++){
                        param+=parametros.charAt(j);
                    }
                    valor = decodificar(40,360,9,param);
                    funciones.add("turnGunRight("+valor+")");
                    break;
            }
            }
            return funciones;
        }
        
	public void iniciaCuerpo(Integer numero_genes){
		this.cuerpo = "";
		this.numero_genes = numero_genes;

		//adjuntamos al cuerpo la probabilidad de cruza mas la probabilidad de mutación mas el numero de hijos
		this.cuerpo += pc_binario;
		this.cuerpo += pm_binario;
		this.cuerpo += numero_hijos_binario;
                
       
		// aqui creamos al resto del individuo
		for (int i = 0; i < numero_genes ;i++) {
			int bit = (Math.random() < 0.5) ? 0 : 1;
			String bit_string = Integer.toString(bit);
			this.cuerpo += bit_string;
		}
	}



        public void imprimeAtributos(){

    		System.out.println(this.cuerpo + "<= cuerpo binario y decimal");
    		System.out.println(this.pm_binario + "<= probabilidad mutación en binario, flotante =>" +this.pm_decimal);
    		System.out.println(this.pc_binario + "<= probabilidad mutación en cruza, flotante =>" + this.pc_decimal);
     		System.out.println(this.numero_hijos_binario + "numero de hijos binario,decimal =>" + this.numero_hijos_decimal);
    		System.out.println(this.fitness + "<= Fitness");
                
            System.out.println("FUNCION RUN");
            //System.out.println("\nFuncion Run: " + this.funcionRun +"\n"+"Tamaño: " + this.funcionRun.length());
            //System.out.println("11 Funciones: " + getCadena11Funciones(this.funcionRun) + "TamañoF: "+ getCadena11Funciones(this.funcionRun).length() + "\n Parametros: "+ getCadenaParametros(this.funcionRun));
            /*Aqui está el error en la funcion arregloeFunciones11()*/
            String [] funRun = arregloFunciones11(getCadena11Funciones(this.funcionRun));
            /*Que funciones contiene y con sus parametros*/
            System.out.println("Funciones y sus parametros");
            funcionesParametros(funRun,getCadenaParametros(this.funcionRun));
            
            System.out.println("\nFUNCION ONSCANNEDROBOT");
            //System.out.println("\n\nFuncion OnScannedRobot: " + this.funcionOnScannedRobot +"\n"+"Tamaño: " + this.funcionOnScannedRobot.length());
            //System.out.println("11 Funciones: " + getCadena11Funciones(this.funcionOnScannedRobot) + "\n Parametros: "+ getCadenaParametros(this.funcionOnScannedRobot));
            String [] funOnSca = arregloFunciones11(getCadena11Funciones(this.funcionOnScannedRobot));
            /*Que funciones contiene y con sus parametros*/
            System.out.println("Funciones y sus parametros");
            funcionesParametros(funOnSca,getCadenaParametros(this.funcionOnScannedRobot));
            
            System.out.println("\nFUNCION ONHITBYBULLET");
            //System.out.println("\nFuncion OnHitByBulet: " + this.funcionOnHitByBullet +"\n"+"Tamaño: " + this.funcionOnHitByBullet.length());
            //System.out.println("11 Funciones: " + getCadena11Funciones(this.funcionOnHitByBullet) + "\n Parametros: "+ getCadenaParametros(this.funcionOnHitByBullet));
            String [] funOnHit = arregloFunciones11(getCadena11Funciones(this.funcionOnHitByBullet));
            /*Que funciones contiene y con sus parametros*/
            System.out.println("Funciones y sus parametros");
            funcionesParametros(funOnHit,getCadenaParametros(this.funcionOnHitByBullet));
            
            System.out.println("\nFUNCION ONHITWALL");
            //System.out.println("\nFuncion OnHitWall: " + this.funcionOnHIitWall +"\n"+"Tamaño: " + this.funcionOnHIitWall.length());
            //System.out.println("11 Funciones: " + getCadena11Funciones(this.funcionOnHIitWall) + "\n Parametros: "+ getCadenaParametros(this.funcionOnHIitWall));
            String [] funOnHitW = arregloFunciones11(getCadena11Funciones(this.funcionOnHIitWall));
            /*Que funciones contiene y con sus parametros*/
            System.out.println("Funciones y sus parametros");
            funcionesParametros(funOnHitW,getCadenaParametros(this.funcionOnHIitWall));
        }
        
        public double decodificar(int a, int b, int noBits, String binario){
            int conversion = Integer.parseInt(binario,2);
            //System.out.print("C: "+ conversion);
            return (a + (conversion*(b-a)/((Math.pow(2, noBits))-1)));
        }
        
        public void creaArchivos() throws Exception{
            
            FileWriter run = new FileWriter("C:\\Users\\kira\\Desktop\\robocode\\robots\\sample\\Darwin.data");
            FileWriter scan = new FileWriter("C:\\Users\\kira\\Desktop\\robocode\\robots\\sample\\Darwin.data");
            FileWriter hit = new FileWriter("C:\\Users\\kira\\Desktop\\robocode\\robots\\sample\\Darwin.data");
            FileWriter hitw = new FileWriter("C:\\Users\\kira\\Desktop\\robocode\\robots\\sample\\Darwin.data");
            StringBuilder sb = new StringBuilder();    

           
            //System.out.println("FUNCION RUN");
            String [] funRun = arregloFunciones11(getCadena11Funciones(this.funcionRun));
            ArrayList<String> funcion_run = funcionesParametros(funRun,getCadenaParametros(this.funcionRun));
            funRun = funcion_run.toArray(new String[funcion_run.size()]);
                
            for (String element : funRun) {
                sb.append(element);
                sb.append(",");   
            }
            run.write(sb.toString());
            sb.delete(0,sb.length());
            
            //System.out.println("\nFUNCION ONSCANNEDROBOT");
            String [] funOnSca = arregloFunciones11(getCadena11Funciones(this.funcionOnScannedRobot));
            ArrayList<String> funcion_on_sca = funcionesParametros(funOnSca,getCadenaParametros(this.funcionOnScannedRobot));
            funOnSca = funcion_on_sca.toArray(new String[funcion_on_sca.size()]);
            
            for (String element : funOnSca) {
                sb.append(element);
                sb.append(",");   
            }
            scan.write(sb.toString());
             sb.delete(0,sb.length());

            //System.out.println("\nFUNCION ONHITBYBULLET");
            String [] funOnHit = arregloFunciones11(getCadena11Funciones(this.funcionOnHitByBullet));
            ArrayList<String> funcion_on_hit = funcionesParametros(funOnHit,getCadenaParametros(this.funcionOnHitByBullet));
            funOnHit = funcion_on_hit.toArray(new String[funcion_on_hit.size()]);

            for (String element : funOnHit) {
                sb.append(element);
                sb.append(",");   
            }
            hit.write(sb.toString());
            sb.delete(0,sb.length());

            //System.out.println("\nFUNCION ONHITWALL");
            String [] funOnHitW = arregloFunciones11(getCadena11Funciones(this.funcionOnHIitWall));
            ArrayList<String> funcion_on_hit_w = funcionesParametros(funOnHitW,getCadenaParametros(this.funcionOnHIitWall));
            funOnHitW = funcion_on_hit_w.toArray(new String[funcion_on_hit_w.size()]);
      
            for (String element : funOnHitW) {
                sb.append(element);
                sb.append(",");   
            }
            hitw.write(sb.toString());
            sb.delete(0,sb.length());

            run.close();
            scan.close();
            hit.close();
            hitw.close();
        }

        public static void main(String[] args) {
             Individuo individuo = new Individuo();
           String[] arregloFunciones;
           String parametros; 
           individuo.iniciaCuerpo(513);
            individuo.codicacionFuncionesPrincipales();
            arregloFunciones = individuo.arregloFunciones11(individuo.getCadena11Funciones(individuo.funcionRun));
            parametros = individuo.getCadenaParametros(individuo.funcionRun);
              System.out.println(individuo.cuerpo);
        }

        
}
package ag;
import robocode.*;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * RobotJose - a robot by (your name here)
 */
public class RobotJose extends Robot
{

boolean movingForward;

public void run(){
		reverseDirection();
		moverBody(580);
		dispararEstrella(0);
		subRutinia2(-42);
		subRutinia1(580);
		moverAlCentro();
		reverseDirection();
	}

	public void onHitWall(HitWallEvent e){
		moverBody(-133);
		moverBody(-133);
		subRutinia3(0);
		subRutinia3(0);
		reverseDirection();
		dispara(0);
		
	

	}

	public void onScannedRobot(ScannedRobotEvent e){
		moverAlCentro();
		
		dispara(0);
		dispararEstrella(0);
		reverseDirection();
		
		dispararEstrella(0);
	
	
	}

	public void OnBulletHit(BulletHitEvent e){
		dispara(0);
		dispararCruz(0);
		moverBody(-118);
		dispara(0);
		subRutinia1(1032);
	
	

	}


	public void OnBulletByHit(BulletHitEvent e){
		reverseDirection();
		subRutinia2(222);
		moverAlCentro();
		moverEsquina(0);
		moverEsquinaCercana();
		reverseDirection();
		dispararEstrella(0);


	}
	public void onHitRobot(HitRobotEvent e) {
        	       
			
			if (e.isMyFault()) {
				reverseDirection();
			}
	}

public void scan(){
            System.out.println("Escaneando en:"+this.getRadarHeading());
            this.turnRadarRight(10);
        }
		
private double normalRelativeAngle(double angle) {
		double relativeAngle = angle % 360;
		if (relativeAngle <= -180)
		return 180 + (relativeAngle % 180);
		else if (relativeAngle > 180)
		return -180 + (relativeAngle % 180);
		else
		return relativeAngle;
	}
	
private double absoluteBearing(double xOrig,double yOrig, double xDest,double yDest) {
		return Math.toDegrees(Math.atan2(xDest - xOrig, yDest -yOrig));
	}
	public void moverAlPunto(double x,double y){
		double distance = distancia(this.getX(),this.getY(),x,y);
		double angle = normalRelativeAngle(absoluteBearing(this.getX(),this.getY(), x,y) - getHeading());
		if (Math.abs(angle) > 90.0) {
			distance *= -1.0;
			if (angle > 0.0) {
				angle -= 180.0;
			}
			else {
				angle += 180.0;
			}
		}
		this.turnRight(angle);
		this.ahead(distance);
	}
	
	private double distancia(double xOrig,double yOrig,double xDest,double yDest){
		return Math.sqrt( Math.pow(xOrig-xDest,2)+Math.pow(yOrig-yDest,2) );
	}
	
	public void moverEsquina(int n){
		switch(n){
			case 1:
				moverAlPunto(0,0);
				break;
			case 2:
				moverAlPunto(0,super.getBattleFieldHeight());
				break;
			case 3:
				moverAlPunto(super.getBattleFieldWidth(),super.getBattleFieldHeight());
				break;
			case 4:
				moverAlPunto(super.getBattleFieldWidth(),0);
				break;
		}
	}



	public void moverEsquinaCercana(){
		double x = super.getBattleFieldWidth();
		double y = super.getBattleFieldHeight();

		double pos1 = distancia(this.getX(),this.getY(),0,0);
		double pos2 = distancia(this.getX(),this.getY(),0,y);
		double pos3 = distancia(this.getX(),this.getY(),x,y);
		double pos4 = distancia(this.getX(),this.getY(),x,0);

		if(pos1<=pos2 && pos1<=pos3 && pos1<=pos4){
			moverEsquina(1);
		}else if(pos2<=pos1 && pos2<=pos3 && pos2<=pos4){
			moverEsquina(2);
		}else if(pos3<=pos1 && pos3<=pos2 && pos3<=pos4){
			moverEsquina(3);
		}else
			moverEsquina(4);
	}
	

       	public void moverAlCentro(){
		double altura=super.getBattleFieldHeight()/2;
		double ancho=super.getBattleFieldWidth()/2;
		moverAlPunto(altura,ancho);
	}
	
        public void dispararEstrella(double intencidad){
            for(int i=0;i<=8;i++){
                System.out.println("Disparando en :"+this.getGunHeading());
                this.fire(1);
                this.turnGunRight(45);
            }
        }
		
	public void reverseDirection() {
		if (movingForward) {
			back(40000);
			movingForward = false;
		} else {
			ahead(40000);
			movingForward = true;
		}
	}

	public void subRutinia1(double param){
		if(param>0)
			ahead(param);
		else
			back(param);
	}

	public void moverBody(double param){
		if(param>0)
			turnRight(param);
		else
			turnLeft(param);
	}

	public void dispara(int intencidad){
                //SI es 0 no disparamos
		if(intencidad==1)
			fire(1);
		if(intencidad==2)
			fire(2);
		if(intencidad==3)
			fire(3);
	}

	public void subRutinia2(double param){
		if(param>0)
			turnGunRight(param);
		else
			turnGunLeft(param);
	}


	public void subRutinia3(double param){
		if(param>0)
			turnRadarRight(param);
		else
			turnRadarLeft(param);
	}


	public void dispararCruz(double intencidad){
            for(int i=0;i<=4;i++){
                System.out.println("Disparando en :"+this.getGunHeading());
                this.fire(1);
                this.turnGunRight(90);
            }
	}
}

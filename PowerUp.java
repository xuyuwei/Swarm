//PowerUp class
import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;


public class PowerUp {
	private int n,x,y;
	private Image [] pics,pics2 ;
    public PowerUp(int n,int x, int y) { //constructor for powerups
    	pics =new Image [4];
    	pics2=new Image[4];
    	this.n=n;
    	this.x=x;
    	this.y=y;
    	try{ //loads the images for the powerups 
    		pics[0]= ImageIO.read(new File("powerups/wallrepair.png"));
    		pics[1]= ImageIO.read(new File("powerups/slowdown.png"));
    		pics[2]= ImageIO.read(new File("powerups/speedup.png"));
    		pics[3]=ImageIO.read(new File("powerups/bigbullets.png"));
    		pics2[0]= ImageIO.read(new File("powerups/wallrepair2.png"));
    		pics2[1]= ImageIO.read(new File("powerups/slowdown2.png"));
    		pics2[2]= ImageIO.read(new File("powerups/speedup2.png"));
    		pics2[3]=ImageIO.read(new File("powerups/bigbullets2.png"));
    	}
    	catch (IOException e){}
    }
    public int getNum(){
    	return n;
    }
    public Image getPic(int x){ //returns the picture based on an integer, there are two pictures
    	if (x<10){
    		return pics[n];
    	}
    	return pics2[n];
    }
    public int getX(){
    	return x;
    }
  	public int getY(){
    	return y;
    }
    public void move(){
    	y+=3;
    } 
    public boolean collide (Canon c){ //checks collision with a Canon
    	if (c.getX()>(x-20) && c.getX()<(x+15) && c.getY()<y+20){
    		return true;
    	}
    	return false;
    }
}
//fire class

import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class Fire { 

  	private int x,y;
	private Image fire;
    public Fire(int x,int y)  { //constructor for fire
    	this.x=x;
    	this.y=y;
    	try{
    		fire = ImageIO.read(new File("images/fire1.png"));
    	}
    	catch (IOException ex){
    		
    	}
    }

    public Image getPic(){ //returns the picture
    	return fire;
    }
    public void move(){//moves the fire
    	y+=2;
    }
    //returns x,y
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public boolean collide(Wall w){ //checks collision between itself and a wall
    	if (w.getX()>(x-38) && w.getX()<(x+13) && (w.getY())<y+28){
    		return true;
    	}
    	return false;
    }
    public boolean collide (Canon c){ //checks collision with a Canon
    	if (c.getX()>(x-17) && c.getX()<(x+10) && c.getY()<y+18){
    		return true;
    	}
    	return false;
    }
}
//Bullet Class
import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class Bullet { 
	private int x,y,size,speed;
	private Image bullet,bigbullet;
    public Bullet(int x,int y,int size)  { //constructor for bullet
    	this.x=x; 
    	this.y=y;
    	this.size=size;
    	try{ //loads the images for the bullets
    		bullet = ImageIO.read(new File("bullet.png"));
    		bigbullet= ImageIO.read(new File("images/bigbullet.png"));
    	}
    	catch (IOException ex){
    		
    	}
    	if (size==0){
    		speed=3;
    	}
    	if (size==1){
    		speed=6;
    	}
    }

    public Image getPic(){ //returns the picture
    	if (size==0){
    		return bullet;
    	}
    	else{
    		return bigbullet;
    	}
    }
    public void move(){ //moves the bullet
    	y-=speed;
    }
    
    //returns x,y values
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public boolean collide(Invader i){ //checks collision with an invader, different between the big and small bullets
    	if (size==0){
	    	if (i.getX()<(x+5) && i.getX()>(x-22) && i.getY()<y+1 && (i.getY()+27)>y){
	    		return true;
	    	}
	    }
	    if (size==1){
	    	if (i.getX()<x+11 && i.getX()>(x-22) && i.getY()<y+20 && (i.getY()+27)>y){
	    		return true;
	    	}
	    }
	    return false;
	    
    }
	public boolean collide(Wall w){ //checks collision with a wall, different between the big and small bullet
		if (size==0){
	    	if (w.getX()<(x+5) && w.getX()>(x-47) && w.getY()<y+1 && (w.getY()+28)>y){
	    		return true;
	    	}
		}
    	if (size==1){ 
    		if (w.getX()<(x+11) && w.getX()>(x-47) && w.getY()<y+20 && (w.getY()+28)>y){
    			return true;
    		}		
    	}
    	return false;
    	
    }
}
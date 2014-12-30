//Canon class
import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;


public class Canon {
	private int x, y;
	private int lives, bigbullets;
	private Image canon,icon;
	private int speed;
    public Canon() throws IOException { //constructor for the canon
    	bigbullets=0;
    	lives=3;
        x=235;
	    y=452;
	    speed=2;
		canon = ImageIO.read(new File("images/canon.png"));
		icon= ImageIO.read(new File("images/lives.png"));
	
		
    }
    public Image getPic(){ //returns the picture of the canon
    	return canon;
    }
    public void die(){ //when the canon dies
    	lives-=1;
    	x=235;
    	y=452;
    	speed=2;
    }
    public void changeSpeed(int n){//changes the movement speed of the canon
    	speed+=n;
    }
    public int getLives(){//returns the number of lives
    	return lives;
    }
    public Image getIcon(){ //returns the icon
    	return icon;
    }
    public int getSpeed(){ //returns speed
    	return speed;
	}
	public void addBigBullets(){ //adds 5 big bullets
		bigbullets+=5;
	}
	public int getBigBullets(){ //returns number of big bullets
		return bigbullets;
	}
	public void useBigBullet(){ //uses a bigbullet
		bigbullets-=1;
	}
	//returns x,y
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	//moves canon
	public void move(int n){
		x+=n;
	}

}

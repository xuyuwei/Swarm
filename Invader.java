//invader class
import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.Random;
public class Invader{
	private int x, y,speed;
	private Image bug;
	private PowerUp pow;
	private Random r;
	public Invader(int speed, int x,int y) throws IOException{ //constructor for an invader
		r=new Random();
		this.speed=speed;
		if (r.nextDouble()<0.2){
			int a=r.nextInt(4);
			if (a==0){
				if (r.nextDouble()<0.5){
					a=r.nextInt(2)+1;
				}
			}
			pow=new PowerUp(a,0,0);
		}
		this.x=x;
    	this.y=y;
    	bug = ImageIO.read(new File("bug.png"));
    	
    }
    public void moveLeft(){
    	x-=speed;
    }
    public void moveRight(){
    	x+=speed;
    }
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public Image getPic(){
    	return bug;
    }
    public PowerUp getPowerUp(){
    	return pow;
    }
   // public void shoot()
}
    

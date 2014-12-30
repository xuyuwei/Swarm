//Wall Class

import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class Wall {
	private int x,y,cur;

	private Image [] walls;
    public Wall(int x, int y) { //constructor for a wall
    	this.x=x;
    	this.y=y;
    	cur=0;
    	
    	walls=new Image[9];
    	try{
    		for (int i=0; i<9; i++){
    			walls[i] = ImageIO.read(new File("images/wall"+(i+1)+".png"));
    		}
    	}
    	catch (IOException ex){
    		
    	}
    }
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public Image getPic(){//returns the picture
    	if (cur>8){
    		return walls[0];
    	}
    	return walls[cur];
    }
    public void next(){ 
    	cur+=1;
    }
    public int getCur(){
    	return cur;
    }
    
    
}
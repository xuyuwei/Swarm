//SimpleGame
//Yuwei Xu
//This game is based off of the Neopets version of Space Invaders, Swarm. There are 5 levels with one bonus level
//and the user uses the left/right arrow keys to move and the up arrow to shoot
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import javax.imageio.*;
import java.io.*;
public class SimpleGame extends JFrame implements ActionListener{ 
	GamePanel game;	
	Timer myTimer;
	int counter=0,icounter=0, deathcounter=0;;
	GameMenu menu;
	static final int LEFT=1;
	static final int RIGHT=2;
	int direction =RIGHT;
	boolean pause = false;
    public SimpleGame() throws IOException{
    	super ("SWARM: The Bugs Strike Back");
    	game= new GamePanel(this);
    	setResizable(false);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(646,528);
		myTimer=new Timer(10,this);
		menu=new GameMenu(this);
    	
    	
    	add(game);

	//	setVisible(true);
    }
    public void actionPerformed(ActionEvent evt){
		Object source = evt.getSource();
		
		if (source==myTimer){
			game.move(); //moves the player
			game.collision(); //checks for all collisions in the game
			game.moveBullet(); //moves the bullets
			game.enemyshoot(); //enemy randomly shoots
			game.movePowerUp(); //moves the powerups
			game.moveFire(); //moves the fire
			if (game.loseLife()){ //if the player loses a life, stop timer
				myTimer.stop();
			}
			game.getPowerUp(); //if player recieves a powerup
			
			game.gameOver(); //if the game is over
			
			//finds the direction of the invaders
			if (game.leftPos()>-1 && game.leftPos()<20){
				direction=RIGHT;
			}
			if (game.rightPos()>470 && game.rightPos()<480){
				direction=LEFT;
			}
			icounter+=1;
			counter+=1;
			game.moveInvaders(direction); //moves invaders
			game.repaint();
			if (counter>=60) { //counter for the bullets so the player cannot spam bullets
				counter=game.shoot();

			}
			if (game.levelOver()){//if the level is over, stop the timer and level up
				myTimer.stop();
				game.levelUp();
				
			}
			
			
			//System.out.println("chen we need your help");
		}
		
	}
	//functions that allow the GamePanel to start and stop the timer
	public void stop(){
		myTimer.stop();
	}
	public void start(){
		
		myTimer.start();
	}
    public static void main (String [] args) throws IOException{
    	SimpleGame frame=new SimpleGame();
    }
}
class GamePanel  extends JPanel implements KeyListener {
	private boolean [] keys; //keyboard keys
	//invaders,powerups,bullets,fires all stored in arraylists
	private ArrayList<Bullet> bullets; 
	private ArrayList<Fire> fires;
	private ArrayList <Wall> walls;
	private ArrayList<Invader> invaders;
	private ArrayList<PowerUp> powerups;
	static final int LEFT=1;
	static final int RIGHT=2;
	private Canon canon;
	private Image bg,nextlvl,livesleft,canonicon,slash,gameover,redx,hiddenlvl,l6;
	//images for the numbers
	private Image [] numbers=new Image[10];
	private Image [] rednumbers=new Image[10];
	
	private int numinvaders;
	private int level, score, ppic;
	private SimpleGame mainGame;
	private boolean pause=false, pause2=false, over=false;
	private double fireamount;
	
	public GamePanel(SimpleGame sg) throws IOException{
		//initializing all the essential components
		mainGame = sg;
		numinvaders=4;
		level=1;
		score=0;
		ppic=0;
		canon=new Canon();
		fires=new ArrayList<Fire>();
		bullets=new ArrayList<Bullet>();
		powerups=new ArrayList<PowerUp>();
		invaders=new ArrayList<Invader>();
		fireamount=0.005;
		//loading images
		bg=ImageIO.read(new File("background.png"));
		nextlvl=ImageIO.read(new File ("images/nextlevel.png"));
		livesleft=ImageIO.read(new File ("images/livesleft.png"));
		canonicon=ImageIO.read(new File ("images/canon.png"));
		gameover=ImageIO.read(new File ("images/gameover.png"));
		redx=ImageIO.read(new File("numbers/x.png"));
		hiddenlvl=ImageIO.read(new File ("images/hiddenlevel.png"));
		l6=ImageIO.read(new File("numbers/l6.png"));
		for (int i=0; i<10; i++){
			numbers[i]=ImageIO.read(new File("numbers/"+i+".png"));
		}
		for (int i=0; i<10; i++){
			rednumbers[i]=ImageIO.read(new File("numbers/red"+i+".png"));
		}
		slash=ImageIO.read(new File ("numbers/slash.png"));
		//adding the invaders and the walls
		for (int i=0; i<level+3; i++){
			for (int j=0; j<level+3; j++){
				invaders.add(new Invader((level+1)/2,(i+1)*(400/(level+3))-45, (j+1)*(360/(level+3))-30));
			}
		}
		walls=new ArrayList<Wall>();
		for (int i=0; i<5; i++){
			walls.add(new Wall(i*90+44,399));
		}

		keys=new boolean [KeyEvent.KEY_LAST+1];
       	addKeyListener(this);
	}

	public void addNotify(){
		super.addNotify();
		requestFocus();
	}
	public int leftPos(){ //finds the left most invader
		if (invaders.size()>0){
			return invaders.get(0).getX();
		}
		return 0;
	}
	public int rightPos(){
		if (invaders.size()>0){ //finds the right most invader
			return invaders.get(invaders.size()-1).getX();
		}
		return 0;
	}
	public void levelUp(){ //levels up 
		level +=1;
		pause=true;
	}
	public void constructLevel(){ //constructs the new level
		score+=canon.getLives()*25;
		if (level==6){
			fireamount=0.0016;
		}
		for (int i=0; i<level+3; i++){ //adds the invaders
			for (int j=0; j<level+3; j++){
				try{
					invaders.add(new Invader((level+1)/2,(i+1)*(400/(level+3))-45, (j+1)*(360/(level+3))-30));
				}
				catch (IOException e){}
			}
		}
		walls=new ArrayList<Wall>();
		for (int i=0; i<5; i++){
			walls.add(new Wall(i*90+44,399));
		}
		fires=new ArrayList<Fire>();
		bullets=new ArrayList<Bullet>();
		powerups=new ArrayList<PowerUp>();
	}
	public void move(){ //moves the player if the arrowkeys are pressed
		if (keys[KeyEvent.VK_RIGHT]){ 
			if (canon.getX()<=460){
				canon.move(canon.getSpeed());
			}
		}
		if (keys[KeyEvent.VK_LEFT]){
			if (canon.getX()>=19){
				canon.move(-canon.getSpeed());
			}
		}	
	}
	
	public int shoot(){ //allows the user to shoot
		int newcounter =60;

		if (keys[KeyEvent.VK_UP]){
			if(canon.getBigBullets()>0){ //checks if the user has any big bullets
				bullets.add(new Bullet(canon.getX()+11,canon.getY()-5,1));
				canon.useBigBullet();
			}
			else{
				bullets.add(new Bullet(canon.getX()+15, canon.getY(),0));
			}
			newcounter=0;
		}
		
		
		return newcounter;
	}
	public void enemyshoot(){ //enemy shoots
		Random r= new Random();
		double x;
		for (int i=0; i<invaders.size(); i++){
			x=r.nextDouble(); // enemies fire randomly
			if (x<fireamount){
				fires.add(new Fire(invaders.get(i).getX()+10,invaders.get(i).getY()+27));
			}
		}
				
	}
	public void movePowerUp(){ //moves all the powerups that are still on the field
		int f=-1;
		for (int p=0; p<powerups.size(); p++){
			powerups.get(p).move();
			if (powerups.get(p).getY()>485){
				f=p;
			}	
		}
		if (f>-1){
			powerups.remove(f);
		}
	}
	public void moveFire(){ //moves the fire from the invaders
		int f=-1;
		for (int b=0; b<fires.size(); b++){
			fires.get(b).move();
			if (fires.get(b).getY()>485){
				f=b;
			}	
		}
		if (f>-1){
			fires.remove(f);
		}
	}		
	public void moveBullet(){ //moves the player's bullets
		int r=-1;
		for (int b=0; b<bullets.size(); b++){
			bullets.get(b).move();
			if (bullets.get(b).getY()<10){
				r=b;
			}	
		}
		if (r>-1){
			bullets.remove(r);
		}
	}
	public void moveInvaders(int d){ //moves the invaders 
		if (d==LEFT){
			for (Invader i : invaders){
				i.moveLeft();
			}
		}
		if (d==RIGHT){
			for (Invader i : invaders){
				i.moveRight();
			}
		}
	}
	public void collision(){ //checks for collisions
		//collision between invaders and bullets
		int b=-1, i=-1;
		for (int j=0; j<bullets.size(); j++){
			for (int k=0; k<invaders.size();k++){
				if (bullets.get(j).collide(invaders.get(k))==true){
					b=j;
					i=k;
					break;
				}
			}
		}
		if (b>-1 && i>-1){
			bullets.remove(b);
			if (invaders.get(i).getPowerUp()!=null){ //if the invader has a powerup, it is dropped once the invader dies
				powerups.add(new PowerUp(invaders.get(i).getPowerUp().getNum(),invaders.get(i).getX(),invaders.get(i).getY()));
			}
			invaders.remove(i);
			score+=3;
			
		}
		//collision between fire and walls
		int x=-1, wall2=-1;
		for(int j=0; j<fires.size();j++){
			for (int k=0; k<walls.size(); k++){
				if (fires.get(j).collide(walls.get(k))==true){
					walls.get(k).next();
					if (walls.get(k).getCur()>8){
						wall2=k;
					}
					x=j;
				}
			}
		}
		if (x>-1){
			fires.remove(x);
		}
		if (wall2>-1){
			walls.remove(wall2);
		}
		//collision between walls and bullets
		int y=-1, wall=-1;
		for(int j=0; j<bullets.size(); j++){
			for (int k=0; k<walls.size(); k++){
				if (bullets.get(j).collide(walls.get(k))==true){
					walls.get(k).next();
					if (walls.get(k).getCur()>8){
						wall=k;
					}
					y=j;
				}
			}
		}
		if (y>-1){
			bullets.remove(y);
		}
		if (wall>-1){
			walls.remove(wall);
		}

	}
	public void recievePowerUp(PowerUp p){ //uses the powerup
		if (p.getNum()==0){ //if the powerup is wallrepair 
			walls=new ArrayList<Wall>();
			for (int i=0; i<5; i++){
				walls.add(new Wall(i*90+44,399));
			}
		}
		if (p.getNum()==1){ //if the powerup is slowdown
			if (canon.getSpeed()>1){
				canon.changeSpeed(-1);
			}
		}
		if (p.getNum()==2){//if the powerup is speedup
			if (canon.getSpeed()<3){
				canon.changeSpeed(1);
			}
		}
		if (p.getNum()==3){//if the powerup is recieve big bullets
			canon.addBigBullets();
		}
	}
	public void getPowerUp(){ //if the powerup collides with the player
		int used=-1;
		for (int i=0; i<powerups.size(); i++){
			if (powerups.get(i).collide(canon)){
				score+=20;
				recievePowerUp(powerups.get(i));
				used=i;
			}
		}
		if (used>-1){
			powerups.remove(used);
		}
	}
	public boolean loseLife(){ //when the player loses a life
		for (int i=0; i<fires.size(); i++){
			if (fires.get(i).collide(canon)){
				canon.die(); //canon loses life, resets, etc
				fires.remove(i);
				fires=new ArrayList<Fire>();
				pause2=true;
				return true;
			}
		}
		return false;
		
	}
	public boolean levelOver(){ //when the level ends
		if (invaders.size()==0){
			
			return true;
		}
		return false;
	}
	public void gameOver(){ //when the game ends
		if (canon.getLives()==0){
			over=true;
		}
		if (level==5 && levelOver() && score<1200){ //game ends at level 5 if the user doesn't have 1200 points
			over=true;
		}
		if (level==6 && levelOver()){ //hidden level
			over =true;
		}
	}
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){
		keys[e.getKeyCode()]=true;
		if (keys[e.VK_ENTER]==true && pause==true){
			constructLevel();
			mainGame.start();
			pause=false;
		}
		if (keys[e.VK_ENTER]==true && pause2==true){
			mainGame.start();
			pause2=false;
		}
	}
	public void keyReleased(KeyEvent e){
		keys[e.getKeyCode()]=false;
	}
	public void paintAP(Graphics g){ //draws the number of AP slugs (big bullets)
		g.drawImage(redx,575,340,this);
		int num=canon.getBigBullets();
		if (num>10){ 
			g.drawImage(rednumbers[num/10],582,340,this);
			g.drawImage(rednumbers[num%10],589,340,this);
		}
		if (num<10){
			g.drawImage(rednumbers[num],582,340,this);
		}
	}
	public void paintPowerUp(Graphics g, int x, int y, Image pic){ //draws the powerups
		g.drawImage(pic, x,y,this);
	}
	public void paintLevel(Graphics g){//draws what level the user is on 
		if (level<6){
			g.drawImage(numbers[level],570,88,this);
		}
		else{
			g.drawImage(l6,570,88,this);
		}
		g.drawImage(slash,579,87,this);
		g.drawImage(numbers[5],586,88,this);
	}
	public void paintScore(Graphics g,int x, int y){ //draws the score
		int temp;
		//draws the score, depending on how big the score is
		
		if (score>999){
			g.drawImage(numbers[score%10],x+14,y,this);
			temp=score/10;

			g.drawImage(numbers[temp%10],x+5,y,this);
			temp=temp/10;

			g.drawImage(numbers[temp%10],x-4,y,this);
			g.drawImage(numbers[temp/10],x-13,y,this);
		}
		if (score<1000 &&score>99){
			g.drawImage(numbers[score%10],x+9,y,this);
			temp=score/10;
			g.drawImage(numbers[temp%10],x,y,this);
			g.drawImage(numbers[temp/10],x-9,y,this);
			
			
			
		}
		if (score<100 && score>9){
			g.drawImage(numbers[score/10],x-4,y,this);
			g.drawImage(numbers[score%10],x+5,y,this);
		}
		if (score>-1 && score<10){
			g.drawImage(numbers[score],x,y,this);
		}
	}
	//draws bullets, fires, invaders, walls
	public void paintBullet(Graphics g,int x, int y,Image pic){
        g.drawImage(pic,x,y,this);     
	}
	public void paintFire(Graphics g,int x, int y,Image pic){
        g.drawImage(pic,x,y,this);
	}
	public void paintInvader(Graphics g, int x, int y,Image pic){
        g.drawImage(pic,x,y,this);
	}
	public void paintWall(Graphics g, int x, int y, Image pic){
		g.drawImage(pic,x,y,this);
	}
	
	public void paintComponent(Graphics g){ //main paint
		 
         g.drawImage(bg,0,0,this);
         paintScore(g,579,33);
         paintLevel(g);
         for (int i=0; i<bullets.size(); i++){
         	paintBullet(g,bullets.get(i).getX(), bullets.get(i).getY(),bullets.get(i).getPic());
         }
        
         for (int i=0; i<invaders.size(); i++){
         	paintInvader(g,invaders.get(i).getX(),invaders.get(i).getY(),invaders.get(i).getPic());
         }
         for (int i=0; i<fires.size(); i++){
         	paintFire(g,fires.get(i).getX(), fires.get(i).getY(),fires.get(i).getPic());
         }
         for (int i=0; i<walls.size(); i++){
         	paintWall(g,walls.get(i).getX(),walls.get(i).getY(),walls.get(i).getPic());
         }
         
         for (int i=0; i<canon.getLives(); i++){
         	g.drawImage(canon.getIcon(),532+i*32,161,this);
         }
         for (int i=0; i<powerups.size(); i++){
         	paintPowerUp(g,powerups.get(i).getX(),powerups.get(i).getY(),powerups.get(i).getPic(ppic));
         }
         paintAP(g); 
         ppic+=1; //used to flash the powerups as they fall
         if (ppic>20){
         	ppic=0;
         }
         if (pause==true){ //when the level is over, display an image
         	if (level==6){
         		g.drawImage(hiddenlvl,17,76,this);
         	}
         	else{
		 		g.drawImage(nextlvl,17,76,this);
         	}
		 }
		 if (pause2==true){ //if the user dies
		 	if (canon.getLives()>0){
		 		int x=130, y=120;
		 		g.drawImage(livesleft,x,y,this); //shows a screen that asks the user to press enter to continue
		 		for (int i=0; i<canon.getLives(); i++){
		 			g.drawImage(canonicon,x+64+35*i,y+55,this);
		 		}
		 	}
		 }
         g.drawImage(canon.getPic(),canon.getX(),canon.getY(),this);
         if (over==true){ //draws the game over screen
         	g.drawImage(gameover,0,0,this);
         	paintScore(g,475,50);
         }
    }
}


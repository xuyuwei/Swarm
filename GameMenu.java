
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

//Menu for the game
class GameMenu extends JFrame implements ActionListener {
	private SimpleGame mainGame;
	JLabel back;
	JButton start;
	ImageIcon bg,playgame;
    public GameMenu(SimpleGame sg) { 
    	super("SWARM: The Bugs Strike Back");
    	setLayout(null);
    	mainGame=sg;
    	bg=new ImageIcon("images/startscreen.png");
    	playgame=new ImageIcon("images/playgame.png");
    	back=new JLabel(bg); //new Jlabel for the background image
    	back.setSize(646,528);
    	back.setLocation(-3,-14);
    	add(back);
    	start=new JButton(playgame); //Jbutton to start the game
    	start.addActionListener(this);
    	start.setSize(109,23);
    	start.setLocation(479,270);
    	add(start);
    	setSize(646,528);
    	setResizable(false);
    	setVisible(true);

    }
    public void actionPerformed (ActionEvent evt)
    {
		Object source = evt.getSource ();

		if(source == start){ //if the start button is pressed
			mainGame.setVisible(true);
			setVisible(false);
			mainGame.start();			
		}	
			
    }
    public static void main(String[] args){
    	GameMenu x=new GameMenu(null);
    }
    
}
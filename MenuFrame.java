import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuFrame extends JFrame implements ActionListener {
    private JMenu fileMenu;
    private JMenu colourMenu;
    private JMenu formatMenu;

    private JTextArea textArea;
    private JRadioButtonMenuItem colourItems[];
    private String colours[] = {"Blue","Red","Yellow"};
    private ButtonGroup coloursButtonGroup;  
    
    public MenuFrame() {
        super("JMenu Example");
	
		createFileMenu();
		createFormatMenu();
	
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		bar.add(fileMenu);
		bar.add(colourMenu);
	
		// Add text area	
		textArea = new JTextArea(40, 15);
		textArea.setEditable(false);
        add(new JScrollPane(textArea),BorderLayout.CENTER);
	}

    /* CREATE FILE MENU */
    
    /** Creates file menu */
	
    private void createFileMenu() {
        // Create file menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		
		// Create file menu items
		JMenuItem aboutItem = new JMenuItem("About ...");
		aboutItem.setMnemonic('A');
		aboutItem.setEnabled(true);
		aboutItem.addActionListener(this);
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('x');
		exitItem.setEnabled(true);
		exitItem.addActionListener(this);
		
		// Add to menu
		fileMenu.add(aboutItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
	}

    /* CREATE COLOUR MENU */    
    /** Creates colour menu comprising radio menu buttons. */
	
    private void createColourMenu() {
        // Create colour menu
		colourMenu = new JMenu("Colour");
		fileMenu.setMnemonic('C');
	        
		// Create colour menu radio button items
		colourItems = new JRadioButtonMenuItem[colours.length];
		coloursButtonGroup = new ButtonGroup();
		for (int index=0;index < colours.length;index++) {
		    colourItems[index] = new JRadioButtonMenuItem(colours[index]);
		    colourItems[index].addActionListener(this);
		    colourMenu.add(colourItems[index]);
		    coloursButtonGroup.add(colourItems[index]);
		}
	    
		// Select first colour button
		colourItems[0].setSelected(true);
	}
	

    /* CREATE FORMAT MENU */	
    private void createFormatMenu() {
        // Create file menu
		formatMenu = new JMenu("Format");
		formatMenu.setMnemonic('r');
		
		// Create file menu items
		createColourMenu();
		
		// Add items to menu
		formatMenu.add(colourMenu);
	}
    
    /* ATION PERFORMED */   
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("About ...")) 
        	about();
		else if (event.getActionCommand().equals("Exit")) 
			System.exit(0);
		else if (event.getActionCommand().equals("Blue")) 
			changeColour("Blue");
		else if (event.getActionCommand().equals("Red")) 
			changeColour("Red");
		else if (event.getActionCommand().equals("Yellow")) 
			changeColour("Yellow");
		else 
			JOptionPane.showMessageDialog(this,"Error in event handler",
			                  "Error: ",JOptionPane.ERROR_MESSAGE);
	}
	 
    /* ABOUT */   
    /** Outputs JOption pane if about menu item selected. */
    
    private void about() {   
		textArea.append("Code example illustrating use of JMenus\n");
	}
    
	
    /* CHANGE COLOUR */ 
   
    private void changeColour(String newColour) {    
		textArea.append("Change colour to " + newColour+ "\n");
	}

    public static void main(String Args[]) {
        // Creat menu frame
        MenuFrame menuFrame = new MenuFrame();
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setSize(500,200);
		menuFrame.setVisible(true);
	}
}

 

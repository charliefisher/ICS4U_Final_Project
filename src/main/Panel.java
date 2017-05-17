package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public final class Panel extends JPanel implements MouseListener, MouseMotionListener {

	// fixes an error with JPanel
	private static final long serialVersionUID = 1L;

	
	// create our states
	public static enum State {
		SetupNAME, SetupSIZE, SetupBUTTON, StartSCREEN, StartORDER, ORDER, OrderSUMMARY, EditSELECT, EditBUTTON
	}

	// instantiate our state
	private static State state;
	
	private static File productButtonSettings;


	private static Font tttFont, StonesFont;
	
	private static BufferedImage edit1, edit2, setup1, setup2, setup3, start, use2, use3, use4;
	private static Button startScreenOpenButton, startScreenEditButton;
	

	public Panel() throws IOException, FontFormatException {
		Panel.productButtonSettings = new File("src/main/ProductButtonSettings");
		
		Scanner sc = new Scanner(productButtonSettings);
	
		if(sc.hasNextLine()) {
			state = State.StartSCREEN;
			this.startScreenOpenButton = new Button("Open", 268, 401, 100, 250);
		}
		else {
			state = State.SetupNAME;
			this.startScreenEditButton = new Button("Edit", 268, 517, 100, 400);
		}
		
		// load our font
		InputStream is = getClass().getResourceAsStream("/Screens/ROBO.ttf");
		tttFont = Font.createFont(Font.TRUETYPE_FONT, is);
		
		URL fileURL;
		
		fileURL = getClass().getResource("/Screens/EDIT1.png");
		edit1 = ImageIO.read(fileURL);
		
		fileURL = getClass().getResource("/Screens/EDIT2.png");
		edit2 = ImageIO.read(fileURL);
		
		fileURL = getClass().getResource("/Screens/SETUP1.png");
		setup1 = ImageIO.read(fileURL);
		
		fileURL = getClass().getResource("/Screens/SETUP2.png");
		setup2 = ImageIO.read(fileURL);
		
		fileURL = getClass().getResource("/Screens/setup3.png");
		setup3 = ImageIO.read(fileURL);
		
		fileURL = getClass().getResource("/Screens/START.png");
		start = ImageIO.read(fileURL);
		
		fileURL = getClass().getResource("/Screens/use2.png");
		use2 = ImageIO.read(fileURL);
		
		fileURL = getClass().getResource("/Screens/USE3.png");
		use3 = ImageIO.read(fileURL);
		
		fileURL = getClass().getResource("/Screens/use4.png");
		use4 = ImageIO.read(fileURL);
		
		
		sc.close();
	}

	public void paint(Graphics g) {
		switch (state) {
		// company name
		case SetupNAME:
			g.drawImage(setup1, 0, 0, null);
			break;
		// how many buttons
		case SetupSIZE:
			g.drawImage(setup2, 0, 0, null);
			break;
		// input button info
		case SetupBUTTON:
			g.drawImage(setup3, 0, 0, null);
			break;
		// select to open cash machine or edit the cash machine
		case StartSCREEN:
			g.drawImage(start, 0, 0, null);
			break;
		// input customer name and number
		case StartORDER:
			g.drawImage(use2, 0, 0, null);
			break;
		// screen with buttons to start order
		case ORDER:
			g.drawImage(use3, 0, 0, null);
			break;
		// screen showing total of order transaction
		case OrderSUMMARY:
			g.drawImage(use4, 0, 0, null);
			break;
		// click which product button you want to edit
		case EditSELECT:
			g.drawImage(edit1, 0, 0, null);
			break;
		// change name and price of particular button selected
		case EditBUTTON:
			g.drawImage(edit2, 0, 0, null);
			break;
		}
	}


	public void run() {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (state) {
		// 
		case SetupNAME:
			
			break;
		// how many buttons
		case SetupSIZE:
			
			break;
		// input button info
		case SetupBUTTON:
			
			break;
		// select to open cash machine or edit the cash machine
		case StartSCREEN:
			if(Panel.startScreenOpenButton.clicked(e.getX(), e.getY())) {
				Panel.state = State.StartORDER;
			}
			else if (Panel.startScreenEditButton.clicked(e.getX(), e.getY())) {
				Panel.state = State.EditSELECT;
			}
			break;
		// input customer name and number
		case StartORDER:
			
			break;
		// screen with buttons to start order
		case ORDER:
			
			break;
		// screen showing total of order transaction
		case OrderSUMMARY:
			
			break;
		// click which product button you want to edit
		case EditSELECT:
			
			break;
		// change name and price of particular button selected
		case EditBUTTON:
			
			break;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}	
	
	// accessor for state
	public static State getGameState() {
		return Panel.state;
	}
	
	// mutator for state
	public static void setGameState(State newState) {
		Panel.state = newState;
	}

	// accessor for tic tac toe font
	public static Font getTTTFont() {
		return tttFont;
	}

	// accessor for stones font
	public static Font getStonesFont() {
		return StonesFont;
	}

	// UNUSED METHODS
	// JPanel makes you implement MouseListener and MouseMotionLister in order to get mouse input
	// Cannot just use specific motion events
	// in other class the do not extend JPanel, you can use listener methods on their own
	// other classes use a custom interface with only the mouse listener methods it needs
	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void mouseReleased(MouseEvent e) {

	}
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	@Override
	public void mouseExited(MouseEvent e) {

	}
}
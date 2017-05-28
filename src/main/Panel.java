package main;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public final class Panel extends JPanel implements MouseListener, KeyListener {

	// fixes an error with JPanel
	private static final long serialVersionUID = 1L;

	// declares BufferedImages for each screen
	private static BufferedImage edit1, edit2, setup1, start, use2, use3, use4;

	// declare reference type of CashMachine
	private CashMachine mc;

	// default constructor
	public Panel() throws IOException, FontFormatException {
		// instantiate the reference of CashMachine
		this.mc = new CashMachine();

		// load of all of the screens and inititalize the BufferedImages
		URL fileURL;

		fileURL = getClass().getResource("/Screens/EDIT1.png");
		edit1 = ImageIO.read(fileURL);

		fileURL = getClass().getResource("/Screens/EDIT2.png");
		edit2 = ImageIO.read(fileURL);

		fileURL = getClass().getResource("/Screens/SETUP1.png");
		setup1 = ImageIO.read(fileURL);

		fileURL = getClass().getResource("/Screens/START.png");
		start = ImageIO.read(fileURL);

		fileURL = getClass().getResource("/Screens/use2.png");
		use2 = ImageIO.read(fileURL);

		fileURL = getClass().getResource("/Screens/USE3.png");
		use3 = ImageIO.read(fileURL);

		fileURL = getClass().getResource("/Screens/use4.png");
		use4 = ImageIO.read(fileURL);
	}

	// handles the graphics
	public void paint(Graphics g) {
		// draws the appropraite screen based on the state of the CashMachine
		switch (mc.getState()) {
		// company name
		case SetupNAME:
			g.drawImage(setup1, 0, 0, null);
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

		// calls the paint(Graphics g) method of CashMachine
		// Panel handles the screen drawing and CashMachine handles the drawing
		// of strings and other GUI features
		this.mc.paint(g);
	}

	// is called 60 times a second by the main class
	public void run() throws IOException {
		// calls the run method for CashMachine
		this.mc.run();
	}

	// is called whenever a mouse is clicked
	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			// calls the mouse clicked method for CashMachine
			mc.mouseClicked(e);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// is called whenever a key is typed
	@Override
	public void keyTyped(KeyEvent e) {
		try {
			// calls the key typed method for CashMachine
			mc.keyTyped(e);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// UNUSED METHODS
	// JPanel makes you implement MouseListener and KeyListener in order to get
	// mouse and keyboard input
	// Cannot just use specific motion events
	// in other class the do not extend JPanel, you can use listener methods on
	// their own
	// other classes only use the required event handlers
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

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
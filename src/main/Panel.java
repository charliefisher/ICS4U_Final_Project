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
	
	private static BufferedImage edit1, edit2, setup1, setup2, setup3, start, use2, use3, use4;
	
	private CashMachine mc;

	public Panel() throws IOException, FontFormatException {
		this.mc = new CashMachine();
		
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
	}

	public void paint(Graphics g) {
		switch (mc.getState()) {
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
		
		this.mc.paint(g);
	}


	public void run() {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			mc.mouseClicked(e);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		try {
			mc.keyTyped(e);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	// UNUSED METHODS
	// JPanel makes you implement MouseListener in order to get mouse input
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
	@Override
	public void keyPressed(KeyEvent e) {
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
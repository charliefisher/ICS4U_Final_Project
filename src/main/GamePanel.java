package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;


public final class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

	// fixes an error with JPanel
	private static final long serialVersionUID = 1L;

	// declare our objects of each game
	TicTacToePanel ttt;
	StonesPanel stones;
	
	// create our states
	public static enum GameState {
		MAIN_MENU, STONES, TICTACTOE, RESET
	}

	// instantiate our state
	private static GameState state;


	private static Font tttFont, StonesFont;

	public GamePanel() throws IOException, FontFormatException {
		// we are in the main menu
		state = GameState.MAIN_MENU;		

		// load tictactoe font
		InputStream is = getClass().getResourceAsStream("/general_resources/tictactoe_font.ttf");
		tttFont = Font.createFont(Font.TRUETYPE_FONT, is);

		// load stones font
		is = getClass().getResourceAsStream("/general_resources/tictactoe_font.ttf");
		StonesFont = Font.createFont(Font.TRUETYPE_FONT, is);

		// instantiate our objects 
		// (better to do one of each than instantiate one every time we start a new game)
		ttt = new TicTacToePanel();
		stones = new StonesPanel();

	}

	public void paint(Graphics g) {
		switch (state) {
		case MAIN_MENU:
			
			break;
		case STONES:
			
			
			break;
		case TICTACTOE:
			
			
			break;
		case RESET:

			break;
		}
	}


	public void run() {
	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}	
	
	// accessor for state
	public static GameState getGameState() {
		return GamePanel.state;
	}
	
	// mutator for state
	public static void setGameState(GameState newState) {
		GamePanel.state = newState;
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
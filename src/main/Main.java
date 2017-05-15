package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;

public class Main {
		// declare and instantiate a JFrame
		static JFrame window = new JFrame("Name");
		private static short WINDOW_WIDTH = 600, WINDOW_HEIGHT = 600;	
		
		public static void main(String[] args) throws IOException, InterruptedException, FontFormatException {
			
			// create a GamePanel (class that extends JPanel)
			Panel panel = new Panel();

			// create a standard cursor
			Cursor jFrameCursor = new Cursor(0);
			
			// configure JFrame (close, size, position, cursor)
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
			window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
			window.setResizable(false);
			window.setLocationRelativeTo(null);
			window.setCursor(jFrameCursor);
		
			// set GamePanel as the content pane
			window.setContentPane(panel);
			
			// add listeners (so we can get input)
			panel.addMouseListener(panel);
			panel.addMouseMotionListener(panel);
			
			// get focus (for listeners)
			panel.requestFocus();
			
			// make the window visible to the user
			window.setVisible(true);
			
			// set the background color (I am going for a simple, minimalist approach)
			panel.setBackground(Color.WHITE);
			
			
			
			while (true) {					
				// get time before running our logic
				long timeBeforeRun = System.currentTimeMillis();
					
				// run our logic and paint the window
				panel.run();
				panel.repaint();
				
				// figure out how long it took to run the code
				long timeRunDifference = System.currentTimeMillis() - timeBeforeRun;

				// wait to ensure we run the application at a refresh of 60FPS
				Thread.sleep(Math.max(0, 16 - timeRunDifference)); 
			}
		}
}

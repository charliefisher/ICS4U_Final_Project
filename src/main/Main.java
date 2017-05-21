package main;

import java.awt.Cursor;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;

public class Main {
		// declare and instantiate a JFrame
		static JFrame window = new JFrame("My Checkout");
		private static short WINDOW_WIDTH = 800, WINDOW_HEIGHT = 800;	
		
		public static void main(String[] args) throws IOException, InterruptedException, FontFormatException {
			
			// create a GamePanel (class that extends JPanel)
			Panel panel = new Panel();
			
			// configure JFrame (close, size, position, cursor)
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
			window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
			window.setResizable(false);
			window.setLocationRelativeTo(null);
			window.setCursor(new Cursor(0));
		
			// set GamePanel as the content pane
			window.setContentPane(panel);
			// make the window visible to the user
			window.setVisible(true);

			// add listeners (so we can get input)
			panel.addMouseListener(panel);
			panel.addKeyListener(panel);	
			panel.requestFocus();
						
			
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

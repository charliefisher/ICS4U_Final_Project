package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ReferenceBox extends JPanel {

	private static final long serialVersionUID = 1L;

	private static BufferedImage refScreen; // background img for window

	private static Font RefFont; // identifier for font

	// arraylist of product buttons to draw price and name
	private ArrayList<ProductButton> tempProductInfo;

	private static String UNDEFINED_BUTTON_NAME;

	public ReferenceBox(String undefinedButtonName, ArrayList<ProductButton> newProductButtons)
			throws IOException, FontFormatException {
		// store the undefined button name from cashmachine
		ReferenceBox.UNDEFINED_BUTTON_NAME = undefinedButtonName;

		URL fileUrl;// look up image

		// import background screen for reference box
		fileUrl = getClass().getResource("/Screens/reference.png");
		refScreen = ImageIO.read(fileUrl);

		// input our desired font
		InputStream is = getClass().getResourceAsStream("/Screens/ROBO.ttf");
		RefFont = Font.createFont(Font.TRUETYPE_FONT, is);
		is.close();

		// make tempProductInfo reference the ArrayList of product buttons in
		// CashMachine
		this.tempProductInfo = newProductButtons;
	}

	// all drawing of data
	public void paint(Graphics g) {
		g.drawImage(refScreen, 0, 0, null); // draw our background image for
											// screen

		// setup font colour and size
		g.setColor(new Color(217, 234, 223));
		g.setFont(ReferenceBox.RefFont.deriveFont(12f));

		// check to make sure the buttons have been initialized
		if (this.tempProductInfo != null) {
			// cycle through buttons, draw at an increase y of 36 each time
			for (int i = 0, y = 56; i < 20; i++, y += 39) {
				//// has a name
				if (!tempProductInfo.get(i).getName().equals(ReferenceBox.UNDEFINED_BUTTON_NAME)) {
					// draw name
					g.drawString(tempProductInfo.get(i).getName(), 40, y);
				} else {
					g.drawString("", 40, y);// undefined name, draw blank
				}

				// store double price value
				Double drawDouble = new Double(tempProductInfo.get(i).getPrice());

				if (!drawDouble.toString().equals("0.0")) {// not 0, draw price
					g.drawString("    $ ", 200, y);
					// format draws 2 decimal places
					g.drawString(String.format("%.2f", drawDouble), 250, y);
				}
				// draw name and 0 price
				else if (!tempProductInfo.get(i).getName().equals(ReferenceBox.UNDEFINED_BUTTON_NAME)) {
					g.drawString("    $ ", 200, y);
					g.drawString(String.format("%.2f", drawDouble), 250, y);
				} else {
					g.drawString("", 250, y); // 0 and undefinded, draw nothing
				}
			}
		}
	}
}

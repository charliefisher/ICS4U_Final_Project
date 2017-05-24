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
	private static BufferedImage refScreen;
	
	private static Font RefFont;
	
	private ArrayList<ProductButton> tempProductInfo;
	
	private static String UNDEFINED_BUTTON_NAME;
	
	public ReferenceBox(String undefinedButtonName) throws IOException, FontFormatException{
		ReferenceBox.UNDEFINED_BUTTON_NAME = undefinedButtonName;
		
		URL fileUrl;
		
		fileUrl = getClass().getResource("/Screens/reference.png");
		refScreen = ImageIO.read(fileUrl);
		
		InputStream is = getClass().getResourceAsStream("/Screens/ROBO.ttf");
		RefFont = Font.createFont(Font.TRUETYPE_FONT, is);
		is.close();
		
	}

	public void paint(Graphics g){
		g.drawImage(refScreen, 0, 0, null);
		
		g.setColor(new Color(217,234,223));
		g.setFont(ReferenceBox.RefFont.deriveFont(12f));
		
		try {
			for(int i= 0, y = 56 ; i < 20; i++, y+= 40){
				if (!tempProductInfo.get(i).getName().equals(ReferenceBox.UNDEFINED_BUTTON_NAME)) {
					g.drawString(tempProductInfo.get(i).getName(), 37, y);
				}
				else {
					g.drawString("", 37, y);
				}	
			}
		}
		catch(NullPointerException e) {
			
		}
	}
	
	public void setProductButtons(ArrayList<ProductButton> newProductButtons) {
		this.tempProductInfo = newProductButtons;
	}	
}

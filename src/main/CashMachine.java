package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CashMachine {
	
	public static enum State {
		SetupNAME, SetupSIZE, SetupBUTTON, StartSCREEN, StartORDER, ORDER, OrderSUMMARY, EditSELECT, EditBUTTON
	}

	private State state;
	private static Font MCFont;
	private File productButtonSettings;
	private ArrayList<ProductButton> productButtons = new ArrayList<ProductButton>();
	
	
	private static Button editConfirmButton, startScreenOpenButton, startScreenEditButton, startReturnToStartButton, startFinishButton, setupNextButton, setupNextButtonLow,
						  startExitButton;
	
	private String typed = "";
	
	
	
	public CashMachine() throws FontFormatException, IOException{
		this.productButtonSettings = new File("src/main/ProductButtonSettings");
		
		Scanner sc = new Scanner(productButtonSettings);
	
		if(sc.hasNextLine()) {
			state = State.StartSCREEN;
			configure();
			
			this.editConfirmButton = new Button("Confirm Changes", 268, 401, 250, 100); // need to update cordinates
			
			this.startScreenOpenButton = new Button("Open", 268, 401, 250, 100);
			this.startScreenEditButton = new Button("Edit", 268, 517, 250, 100);
			this.startReturnToStartButton = new Button("Start", 29, 695, 116, 77); // need to update cordinates
			this.startFinishButton = new Button("Finish", 656, 695, 67, 77); // need to update cordinates
			this.startExitButton = new Button("Exit", 750, 12, 35, 32);
			
			this.setupNextButton = new Button("Next", 268, 401, 250, 100); // need to update cordinates
			this.setupNextButtonLow = new Button("Next (Low)", 268, 401, 250, 100); // need to update cordinates
		}
		else {
			state = State.SetupNAME;
			
		}
		
		InputStream is = getClass().getResourceAsStream("/Screens/ROBO.ttf");
		MCFont = Font.createFont(Font.TRUETYPE_FONT, is);
		
		sc.close();

	}
	
	public void configure() throws FileNotFoundException{
		Scanner sc = new Scanner(this.productButtonSettings);
		
		for(int i = 0, xCord = 150, yCord = 150; sc.hasNextLine(); i++, xCord += 175, yCord += 175) {
			if(i % 4 == 0) {
				xCord = 150;
			}
			else if(i % 3 == 0) {
				yCord = 150;
			}
			
			productButtons.add(new ProductButton(sc.next(), sc.nextDouble(), xCord, yCord));
			
			System.out.println(productButtons.get(i).toString());
		}
	}
	
	public void paint(Graphics g) {
		switch (this.state) {
		// company name
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
		
			break;
		// input customer name and number
		case StartORDER:
			g.setFont(CashMachine.MCFont.deriveFont(36f));
			g.setColor(Color.WHITE);
			g.drawString(this.typed, 215, 330);
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
	
	public void edit(){
		
	}
	
	public void run(){
		
	}
	
	public String getReceipt(){ // idk what type we should make this method to return receipt
		return" ";
	}
	
	public State getState(){
		return this.state;
	}
	
	public void setState(State newState){
		this.state = newState;
	}
	
	public static Font getMCFont() {
		return CashMachine.MCFont;
	}

	
	public void mouseClicked(MouseEvent e) {
		switch (this.state) {
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
			if(CashMachine.startScreenOpenButton.clicked(e.getX(), e.getY())) {
				this.state = State.StartORDER;
			}
			else if (CashMachine.startScreenEditButton.clicked(e.getX(), e.getY())) {
				this.state = State.EditSELECT;
			}
			break;
		// input customer name and number
		case StartORDER:
			if(CashMachine.startFinishButton.clicked(e.getX(), e.getY())) {
				this.state = State.ORDER;
			}
			else if (CashMachine.startReturnToStartButton.clicked(e.getX(), e.getY())) {
				this.state = State.StartSCREEN;
			}
			break;
		// screen with buttons to start order
		case ORDER:
			if(CashMachine.startFinishButton.clicked(e.getX(), e.getY())) {
				this.state = State.OrderSUMMARY;
			}
			else if (CashMachine.startReturnToStartButton.clicked(e.getX(), e.getY())) {
				this.state = State.StartORDER;
			}
			break;
		// screen showing total of order transaction
		case OrderSUMMARY:
			if (CashMachine.startReturnToStartButton.clicked(e.getX(), e.getY())) {
				this.state = State.StartSCREEN;
			}
			else if (CashMachine.startExitButton.clicked(e.getX(), e.getY())){
				this.state = State.StartSCREEN;
			}
			break;
		// click which product button you want to edit
		case EditSELECT:
			if (CashMachine.startExitButton.clicked(e.getX(), e.getY())){
				this.state = State.StartSCREEN;
			}
			break;
		// change name and price of particular button selected
		case EditBUTTON:
			
			break;
		}
	}
	

	public void keyTyped(KeyEvent e) {			
		switch (this.state) {
		// 
		case SetupNAME:
			
			break;
		// how many buttons
		case SetupSIZE:
			
			break;
		// input button info
		case SetupBUTTON:
			
			break;
		case StartORDER:
			char temp = e.getKeyChar();
			
			if (temp == KeyEvent.VK_BACK_SPACE && typed.length() > 0) {
				typed = typed.substring(0, typed.length()-1);
			}
			else if (temp != KeyEvent.VK_BACK_SPACE && typed.length() < 17) {
				typed += temp;
			}
			
			typed.toUpperCase();
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
}

package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import database.Customer;
import database.Transaction;

public class CashMachine {
	
	public static enum State {
		SetupNAME, SetupSIZE, SetupBUTTON, StartSCREEN, StartORDER, ORDER, OrderSUMMARY, EditSELECT, EditBUTTON
	}

	private State state;
	private static Font MCFont;
	private File productButtonSettings;
	private ArrayList<ProductButton> productButtons = new ArrayList<ProductButton>();
	
	
	private static Button editConfirmButton, startScreenOpenButton, startScreenEditButton, startReturnToStartButton, startFinishButton, setupNextButton, setupNextButtonLow,
						  startExitButton, startCustomerNameBounds, startCustomerNumberBounds;
	
	private String customerName = "", customerNumber = "", productButtonPrice;
	private boolean customerNameComplete = false, customerNumberComplete = false, 
					productNameComplete = false, productPriceComplete = false;
	private int productButtonIndex = 0;
	
	private Customer customer;
	private Transaction transaction;
	
	private BufferedImage highlightName, highlightNumber; // for two highlight fields
	
	public CashMachine() throws FontFormatException, IOException{
		this.productButtonSettings = new File("src/main/ProductButtonSettings");
		
		Scanner sc = new Scanner(productButtonSettings);
	
		if(sc.hasNextLine()) {
			this.customer = new Customer();
			
			state = State.StartSCREEN;
			configure();
			
			CashMachine.editConfirmButton = new Button("Confirm Changes", 268, 401, 250, 100); // need to update cordinates
			
			CashMachine.startScreenOpenButton = new Button("Open", 268, 401, 250, 100);
			CashMachine.startScreenEditButton = new Button("Edit", 268, 517, 250, 100);
			CashMachine.startReturnToStartButton = new Button("Start", 29, 695, 116, 77); // need to update cordinates
			CashMachine.startFinishButton = new Button("Finish", 656, 695, 116, 77); // need to update cordinates
			CashMachine.startExitButton = new Button("Exit", 750, 12, 35, 32);
			
			CashMachine.startCustomerNameBounds = new Button("Customer Name Bounds", 146, 280, 524, 120); // need to update cordinates
			CashMachine.startCustomerNumberBounds = new Button("Customer Number Bounds", 146, 450, 524, 120); // need to update cordinates
			
			CashMachine.setupNextButton = new Button("Next", 268, 401, 250, 100); // need to update cordinates
			CashMachine.setupNextButtonLow = new Button("Next (Low)", 268, 401, 250, 100); // need to update cordinates
		}
		else {
			state = State.SetupNAME;
			
		}
		
		URL fileURL; // import two click overlays
		fileURL = getClass().getResource("/Screens/CUSTOMER NAME.png");
		highlightName = ImageIO.read(fileURL);
		fileURL = getClass().getResource("/Screens/CUSTOMER NUMBER.png");
		highlightNumber = ImageIO.read(fileURL);
		
		InputStream is = getClass().getResourceAsStream("/Screens/ROBO.ttf");
		MCFont = Font.createFont(Font.TRUETYPE_FONT, is);
		
		sc.close();

	}
	
	public void configure() throws FileNotFoundException{
		Scanner sc = new Scanner(this.productButtonSettings);
		
		for(int i = 0, xCord = 82, yCord = 134; sc.hasNextLine(); i++, xCord += 127) {
			if(i % 4 == 0) {
				xCord = 82;
			}
			else if(i % 3 == 0) {
				yCord += 128;
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
			
			if(!customerNameComplete)
				g.drawImage(this.highlightName, 250, 355, null);
			else
				g.drawImage(this.highlightNumber, 235, 524, null);
			
			g.drawString(this.customerName.toUpperCase(), 215, 330);
			g.drawString(this.customerNumber, 215, 500);
			break;
		// screen with buttons to start order
		case ORDER:
			g.setFont(CashMachine.MCFont.deriveFont(36f));
			g.setColor(new Color(64,227,126));
			
			FontMetrics fm = g.getFontMetrics();
			int widthOfValue = fm.stringWidth(transaction.getSubTotal()); // find length of sub total
			
			//draw subtotal right justified
			g.drawString(transaction.getSubTotal(), 780-widthOfValue, 46);
			break;
		// screen showing total of order transaction
		case OrderSUMMARY:
			g.setFont(CashMachine.MCFont.deriveFont(30f));
			g.setColor(new Color(21,134,65));
			String temp = transaction.getOrderSummary();
			for(int i = 0, y = 400; i < 5;i++, y+=40){
				
				FontMetrics fm2 = g.getFontMetrics();
				int lengthOfString = fm2.stringWidth(temp.substring(0,temp.indexOf("?")));
				
				int centerJustify = 400 -lengthOfString/2; //determine where to start x co-ordinate
				int lengthOfHeader = fm2.stringWidth(temp.substring(0, temp.indexOf(":")+1)); //eg. Total:
																							  // used as ref where to add next x value
				g.setColor(new Color(32,106,61)); //darker colour
				g.drawString(temp.substring(0, temp.indexOf(":")+1),centerJustify, y); //draw sub head
				g.setColor(new Color(21,134,65)); // lighter colour
				g.drawString(temp.substring(temp.indexOf(":") + 1, temp.indexOf("?")), centerJustify+lengthOfHeader, y); //draw data
				temp = temp.substring(temp.indexOf("?")+1); //move on through string to next point of interest
			}
			break;
		// click which product button you want to edit
		case EditSELECT:
			
			break;
		// change name and price of particular button selected
		case EditBUTTON:
			g.setFont(CashMachine.MCFont.deriveFont(36f));
			g.setColor(Color.WHITE);
			
			if(!customerNameComplete)
				g.drawImage(this.highlightName, 250, 355, null);
			else
				g.drawImage(this.highlightNumber, 235, 524, null);
			
			g.drawString(this.productButtons.get(this.productButtonIndex).getName().toUpperCase(), 215, 330);
			g.drawString(this.productButtonPrice, 215, 500);
			break;
		}
	}
	
	public void edit(ProductButton editButton, int buttonNumber){
		
	}
	
	public void run() throws IOException{
		switch(this.state) {
		case SetupNAME:
			
			break;
		case SetupSIZE:
			
			break;
		case SetupBUTTON:
			
			break;
		case StartORDER:
			break;
		case StartSCREEN:
			break;
		case ORDER:
			
			break;
		case OrderSUMMARY:
			this.customer.write();
			this.transaction.write();
			System.out.println("WritingFromRun");
			break;
		case EditSELECT:
			break;
		case EditBUTTON:
			
			break;
		}
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

	
	public void mouseClicked(MouseEvent e) throws IOException {
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
				customerNameComplete = true;
				customerNumberComplete = true;
				this.loadCustomer();
			}
			else if (CashMachine.startReturnToStartButton.clicked(e.getX(), e.getY())) {
				this.state = State.StartSCREEN;
			}
			else if (CashMachine.startCustomerNameBounds.clicked(e.getX(), e.getY())) {
				this.customerNameComplete = false;
				this.customerNumberComplete = true;
			}
			else if (CashMachine.startCustomerNumberBounds.clicked(e.getX(), e.getY())) {
				this.customerNameComplete = true;
				this.customerNumberComplete = false;
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
			else{
				for(int i = 0; i < this.productButtons.size(); i++){
					if (this.productButtons.get(i).clicked(e.getX(), e.getY())) {
						this.transaction.addToSubtotal(this.productButtons.get(i));
					}
				}
			}
			break;
		// screen showing total of order transaction
		case OrderSUMMARY:
			if (CashMachine.startReturnToStartButton.clicked(e.getX(), e.getY())) {
				this.state = State.StartORDER;
				// RESET CUSTOMER DATA HERE
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
			else{
				for(int i = 0; i < this.productButtons.size(); i++){
					if (this.productButtons.get(i).clicked(e.getX(), e.getY())) {
						this.productButtonIndex = i;
						this.productButtonPrice = new Double(this.productButtons.get(this.productButtonIndex).getPrice()).toString();
						this.state = State.EditBUTTON;
						edit(this.productButtons.get(i), i);
					}
				}
			}
			break;
		// change name and price of particular button selected
		case EditBUTTON:
			if (CashMachine.startExitButton.clicked(e.getX(), e.getY())){
				this.state = State.EditSELECT;
			}
			break;
		}
	}
	

	public void keyTyped(KeyEvent e) throws IOException {			
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
			
			if (temp == KeyEvent.VK_ENTER) {
				if (!customerNameComplete) {
					customerNameComplete = true;
				}
				else {
					this.loadCustomer();
					break;
				}
			}
			
			if (!customerNameComplete) {
				if (temp == KeyEvent.VK_BACK_SPACE && customerName.length() > 0) {
					customerName = customerName.substring(0, customerName.length()-1);
				}
				else if (temp != KeyEvent.VK_BACK_SPACE && customerName.length() < 17) {
					customerName += temp;
				}
			}
			else if (!customerNumberComplete && temp != KeyEvent.VK_ENTER) {
				if (temp == KeyEvent.VK_BACK_SPACE && customerNumber.length() > 0) {
					customerNumber = customerNumber.substring(0, customerNumber.length()-1);
				}
				else if (temp != KeyEvent.VK_BACK_SPACE && customerNumber.length() < 16) {
					customerNumber += temp;
				}
			}
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
			temp = e.getKeyChar();
			
			if (temp == KeyEvent.VK_ENTER) {
				if (!productNameComplete) {
					productNameComplete = true;
				}
				else {
					this.loadCustomer();
					// Move to next state
					// write the changes to the product button file
				}
			}
			
			if (!productNameComplete) {
				if (temp == KeyEvent.VK_BACK_SPACE && this.productButtons.get(productButtonIndex).getName().length() > 0) {
					this.productButtons.get(this.productButtonIndex).setName(this.productButtons.get(this.productButtonIndex).getName().substring(0, this.productButtons.get(this.productButtonIndex).getName().length()-1));
				}
				else if (temp != KeyEvent.VK_BACK_SPACE && this.productButtons.get(productButtonIndex).getName().length() < 17) {
					this.productButtons.get(this.productButtonIndex).setName(this.productButtons.get(this.productButtonIndex).getName()+ temp);
				}
			}
			else if (!productPriceComplete && temp != KeyEvent.VK_ENTER) {
				if (temp == KeyEvent.VK_BACK_SPACE && this.productButtons.get(productButtonIndex).getPrice() > 0) {
					productButtonPrice = productButtonPrice.substring(0, productButtonPrice.length()-1);					
				}
				else if (temp != KeyEvent.VK_BACK_SPACE && new Double(this.productButtons.get(productButtonIndex).getPrice()).toString().length() < 16) {
					productButtonPrice += temp;
				}
			}
			break;
		}		
	}
	
	private void loadCustomer() throws IOException {	
		System.out.println(customerName);
		System.out.println(customerNumber);
		
		if(this.customerNumber.length() == 10) {	
			if(this.customer.load(this.customerName)) {
				this.transaction = new Transaction(this.customer);
				this.state = State.ORDER;
			}
			else {
				this.customer.create(this.customerName, this.customerNumber);
				this.transaction = new Transaction(this.customer);
				this.state = State.ORDER;
			}
		}	
		
		//&& this.customerName.contains(" ")
	}
}

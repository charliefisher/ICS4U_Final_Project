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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import database.Customer;
import database.Transaction;

public class CashMachine {

	public static enum State {
		SetupNAME, StartSCREEN, StartORDER, ORDER, OrderSUMMARY, EditSELECT, EditBUTTON
	}

	private static Button editConfirmButton, startScreenOpenButton, startScreenEditButton, startReturnToStartButton,
			startFinishButton, setupNextButton, startExitButton, startCustomerNameBounds, startCustomerNumberBounds,
			editProductNameBounds, editProductPriceBounds, openReferenceScreen;

	// string stored for undefined buttons
	public static final String UNDEFINED_BUTTON_NAME = "*****UNDEFINED*****";
	private State state;
	private static Font MCFont;
	private File productButtonSettings, settings;
	private ArrayList<ProductButton> productButtons = new ArrayList<ProductButton>();

	private String customerName = "", customerNumber = "", productButtonName, productButtonPrice, companyName = "";
	private boolean customerNameComplete = false, customerNumberComplete = false, productNameComplete = false,
			productPriceComplete = false, writeToGlobalReciept = true;
	private int productButtonIndex = 0;

	private Customer customer;
	private Transaction transaction;

	// for two highlighted fields in menus
	private BufferedImage highlightName, highlightNumber, highlightProductName, highlightProductPrice;

	private ReferenceBox refPanel; // reference to the reference window code

	private JFrame ref = new JFrame("Reference Data");

	public CashMachine() throws FontFormatException, IOException {
		this.productButtonSettings = new File("src/main/product_button_settings");
		this.settings = new File("src/main/settings");

		this.customer = new Customer();
		configure();

		// initialize all buttons with correct coordinates
		CashMachine.editConfirmButton = new Button("Confirm Changes", 236, 610, 344, 77);

		CashMachine.startScreenOpenButton = new Button("Open", 268, 401, 250, 100);
		CashMachine.startScreenEditButton = new Button("Edit", 268, 517, 250, 100);
		CashMachine.startReturnToStartButton = new Button("Start", 29, 695, 116, 77);
		CashMachine.startFinishButton = new Button("Finish", 656, 695, 116, 77);
		CashMachine.startExitButton = new Button("Exit", 750, 12, 35, 32);

		CashMachine.startCustomerNameBounds = new Button("Customer Name Bounds", 146, 280, 524, 120);
		CashMachine.startCustomerNumberBounds = new Button("Customer Number Bounds", 146, 450, 524, 120);

		CashMachine.editProductNameBounds = new Button("Product Name Bounds", 180, 270, 524, 117);
		CashMachine.editProductPriceBounds = new Button("Product Number Bounds", 180, 433, 524, 117);

		CashMachine.setupNextButton = new Button("Next", 355, 482, 115, 75);

		CashMachine.openReferenceScreen = new Button("Reference Screen", 315, 64, 174, 58);

		Scanner sc = new Scanner(this.settings);
		Scanner sc2 = new Scanner(this.productButtonSettings);

		if (sc.hasNextLine() && sc2.hasNextLine()) {
			this.companyName = sc.nextLine();
			state = State.StartSCREEN;
		} else if (sc.hasNextLine()) {
			this.companyName = sc.nextLine();

			// company name but buttons empty, set up buttons
			state = State.EditSELECT;
		} else {
			state = State.SetupNAME; // no previous data, setup screen
		}

		sc.close();
		sc2.close();
		// import overlays for editing customer data and product data
		URL fileURL;

		fileURL = getClass().getResource("/Screens/CUSTOMER NAME.png");
		highlightName = ImageIO.read(fileURL);
		fileURL = getClass().getResource("/Screens/CUSTOMER NUMBER.png");
		highlightNumber = ImageIO.read(fileURL);
		fileURL = getClass().getResource("/Screens/product name.png");
		highlightProductName = ImageIO.read(fileURL);
		fileURL = getClass().getResource("/Screens/product price.png");
		highlightProductPrice = ImageIO.read(fileURL);

		// setup our input font we will draw
		InputStream is = getClass().getResourceAsStream("/Screens/ROBO.ttf");
		MCFont = Font.createFont(Font.TRUETYPE_FONT, is);

		is.close();

		// initialize/setup the reference box
		this.refPanel = new ReferenceBox(CashMachine.UNDEFINED_BUTTON_NAME, this.productButtons);
		this.ref.setSize(400, 822);
		this.ref.setResizable(false);
		this.ref.setLocationRelativeTo(null);
		this.ref.setContentPane(refPanel);

	}

	public void configure() throws IOException {
		// will read the settings file
		Scanner sc = new Scanner(this.productButtonSettings);

		// sets up all product buttons based on coordinates (that are cycled
		// through)
		for (int i = 0, xCord = 82, yCord = 134; i < 20; i++, xCord += 127) {
			if (i % 5 == 0 && i > 0) {
				xCord = 82;
				yCord += 128;
			}
			// setup product button that already has name
			if (sc.hasNextLine()) {
				productButtons.add(new ProductButton(sc.next(), sc.nextDouble(), xCord, yCord));
			} else { // setup blank product button
				productButtons.add(new ProductButton(UNDEFINED_BUTTON_NAME, 0, xCord, yCord));
			}

		}

		sc.close();
	}

	// write name and price of 20 buttons to product_button_settings
	private void writeProductButtons() throws IOException {
		FileWriter wr = new FileWriter(this.productButtonSettings);

		for (int i = 0; i < 20; i++) {
			wr.write(this.productButtons.get(i).getName() + "\n");
			wr.write(this.productButtons.get(i).getPrice() + "\n");
		}

		wr.close();
	}

	public void paint(Graphics g) {
		switch (this.state) {
		// company name
		case SetupNAME:
			g.setFont(CashMachine.MCFont.deriveFont(36f));// setup font and size
			g.setColor(Color.WHITE);
			// draw company name
			g.drawString(this.companyName.toUpperCase(), 220, 365);
			break;
		// input customer name and number
		case StartORDER:
			g.setFont(CashMachine.MCFont.deriveFont(36f));
			g.setColor(Color.WHITE);
			// if we are still editing customer name
			if (!customerNameComplete) {
				// draw the white highlight overtop to indcate edit
				g.drawImage(this.highlightName, 250, 355, null);
			} else {
				// highlight over numebr
				g.drawImage(this.highlightNumber, 235, 524, null);
			}

			// draw customer name and number
			g.drawString(this.customerName.toUpperCase(), 215, 330);
			g.drawString(this.customerNumber, 215, 500);
			break;
		// screen with buttons to start order
		case ORDER:
			g.setFont(CashMachine.MCFont.deriveFont(36f));
			g.setColor(new Color(64, 227, 126));

			FontMetrics fm = g.getFontMetrics();

			// find pixel length of subtotal value
			int widthOfValue = fm.stringWidth("$" + transaction.getSubtotal());

			// draw subtotal right justified
			g.drawString("$" + transaction.getSubtotal(), 780 - widthOfValue, 46);
			break;
		// screen showing total of order transaction
		case OrderSUMMARY:
			g.setFont(CashMachine.MCFont.deriveFont(30f));
			g.setColor(new Color(21, 134, 65));
			// temp string contain all order summary data
			String temp = transaction.getOrderSummary();

			for (int i = 0, y = 400; i < 5; i++, y += 40) { // draw 5 lines of
															// data
				FontMetrics fm2 = g.getFontMetrics();
				int lengthOfString = fm2.stringWidth(temp.substring(0, temp.indexOf("?")));

				// determine where to start x co-ordinate
				int centerJustify = 400 - lengthOfString / 2;

				// eg. Total: used as ref where to add next x value
				int lengthOfHeader = fm2.stringWidth(temp.substring(0, temp.indexOf(":") + 1));

				g.setColor(new Color(32, 106, 61)); // darker colour
				// draw sub head
				g.drawString(temp.substring(0, temp.indexOf(":") + 1), centerJustify, y);

				g.setColor(new Color(21, 134, 65)); // lighter colour
				g.drawString(temp.substring(temp.indexOf(":") + 1, temp.indexOf("?")), centerJustify + lengthOfHeader,
						y); // draw data
				// move on through string to next point of interest
				temp = temp.substring(temp.indexOf("?") + 1);
			}
			break;
		// change name and price of particular button selected
		case EditBUTTON:
			g.setFont(CashMachine.MCFont.deriveFont(36f));
			g.setColor(Color.WHITE);

			if (!productNameComplete) { // if still editing product name
				// draw highlight over product name
				g.drawImage(this.highlightProductName, 256, 346, null);
			} else {
				// draw highlight over product price
				g.drawImage(this.highlightProductPrice, 262, 515, null);
			}
			// draw product button name
			g.drawString(this.productButtonName, 215, 321);
			g.drawString("$", 215, 489);
			g.drawString(this.productButtonPrice, 240, 489); // draw the price

			// draw which button user is editing
			g.setColor(new Color(217, 234, 223));
			g.setFont(CashMachine.MCFont.deriveFont(58f));
			g.drawString("" + (this.productButtonIndex + 1), 525, 205);
			break;
		default:
			break;
		}
	}

	public void edit(ProductButton editButton, int buttonNumber) {

	}

	public void run() throws IOException {
		refPanel.repaint();// repaint updated reference panel
		if (this.state == State.OrderSUMMARY) {
			// close the register (update the transaction number)
			this.transaction.registerClose();
			this.customer.write(this.transaction.getTransactionNum());
			this.transaction.write();
			if (this.writeToGlobalReciept) {
				this.writeReceipt();
				this.writeToGlobalReciept = false;
			}
		}
	}

	// writes the company reciept (a file with all of the transcations listed)
	private void writeReceipt() throws IOException {
		// locates the correct directory for the universal receipt file
		File dir = new File("src/main/");
		// make the directory for the universal receipt file
		dir.mkdirs();

		// creates the file for the universal receipt
		File totalTransactions = new File(dir, this.companyName.replace(" ", "").toLowerCase() + "_universal_recipt");
		// creates a permanent file for the unviersal reciept
		totalTransactions.createNewFile();

		// declares and instantiates a scanner that reads from the universal
		// receipt
		Scanner sc = new Scanner(totalTransactions);
		// declares a filewriter
		FileWriter wr;

		// if the file already has info (has already been created), then append
		// to the bottom of the file
		// else, the file has no info (was just created), then write the company
		// name
		if (sc.hasNextLine()) {
			wr = new FileWriter(totalTransactions, true);
		} else {
			wr = new FileWriter(totalTransactions);
			wr.write(this.companyName);
		}

		// write neccessary data, customer, prices, etc to receipt
		wr.write("\n\n" + this.transaction.getTransactionNum() + "\n");
		wr.write(this.transaction.getDate() + "\n");
		wr.write(this.transaction.getCustomer() + "\n");
		wr.write(this.transaction.getSubtotal() + "\n");
		wr.write(this.transaction.getTax() + "\n");
		wr.write(this.transaction.getTotal());

		// close the filewriter (finished writing)
		wr.close();
		// close the scanner (finished reading)
		sc.close();
	}

	public State getState() { // return current state
		return this.state;
	}

	public void setState(State newState) { // set the state
		this.state = newState;
	}

	public static Font getMCFont() {
		return CashMachine.MCFont; // return our font
	}

	public void mouseClicked(MouseEvent e) throws IOException {
		switch (this.state) {
		// input company name
		case SetupNAME:
			if (CashMachine.setupNextButton.clicked(e.getX(), e.getY())) {
				this.confirmCompanyName(); // clicked next, confirm company name
			}
			break;
		// select to open cash machine or edit the cash machine
		case StartSCREEN:
			if (CashMachine.startScreenOpenButton.clicked(e.getX(), e.getY())) {
				this.state = State.StartORDER; // clicked open go to customer
												// name enter
			} else if (CashMachine.startScreenEditButton.clicked(e.getX(), e.getY())) {
				// go to select button to edit screen
				this.state = State.EditSELECT;
			}
			break;
		// input customer name and number
		case StartORDER:
			// clicked next
			if (CashMachine.startFinishButton.clicked(e.getX(), e.getY())) {
				customerNameComplete = true;
				customerNumberComplete = true;
				// confirms the data above and loads customer
				this.loadCustomer();
			} else if (CashMachine.startReturnToStartButton.clicked(e.getX(), e.getY())) {
				// clicked start goes back to main menu
				this.state = State.StartSCREEN;
			}
			// clicked into enter customer name, trip boolean to draw highlight
			else if (CashMachine.startCustomerNameBounds.clicked(e.getX(), e.getY())) {
				this.customerNameComplete = false;
				this.customerNumberComplete = true;
			}
			// clicked into edit customer name box, trip boolean to highlight it
			else if (CashMachine.startCustomerNumberBounds.clicked(e.getX(), e.getY())) {
				this.customerNameComplete = true;
				this.customerNumberComplete = false;
			}
			break;
		// screen with buttons to start order
		case ORDER:
			if (CashMachine.startFinishButton.clicked(e.getX(), e.getY())) {
				// clicked finish, go to order summary
				this.state = State.OrderSUMMARY;
			} else if (CashMachine.startReturnToStartButton.clicked(e.getX(), e.getY())) {
				// clicked start, go back to enter customer name
				this.state = State.StartORDER;
			} else if (CashMachine.openReferenceScreen.clicked(e.getX(), e.getY())) {
				// clicked open ref screen, make it visible
				this.ref.setVisible(true);
			} else {
				// clicked any one of the product buttons on screen, add their
				// price to subtotal
				for (int i = 0; i < this.productButtons.size(); i++) {
					if (this.productButtons.get(i).clicked(e.getX(), e.getY())
							&& (!this.productButtons.get(i).getName().equals(UNDEFINED_BUTTON_NAME)
									|| this.productButtons.get(i).getPrice() != 0.0)) {
						this.transaction.addToSubtotal(this.productButtons.get(i), CashMachine.UNDEFINED_BUTTON_NAME);
					}
				}
			}
			break;
		// screen showing total of order transaction
		case OrderSUMMARY:
			if (CashMachine.startReturnToStartButton.clicked(e.getX(), e.getY())) {
				// reset input fields and the customer
				this.customerName = "";
				this.customerNumber = "";
				this.customerNameComplete = false;
				this.customerNumberComplete = false;
				this.customer = new Customer();

				this.writeToGlobalReciept = true;
				// clicked start,go back to type in new customer order
				this.state = State.StartORDER;
			} else if (CashMachine.startExitButton.clicked(e.getX(), e.getY())) {
				// clicked x, go back to start screen
				this.state = State.StartSCREEN;
				this.writeToGlobalReciept = true;
			}
			break;
		// click which product button you want to edit
		case EditSELECT:
			if (CashMachine.startExitButton.clicked(e.getX(), e.getY())) {
				// clicked x to go back to main menu
				this.state = State.StartSCREEN;
			} else if (CashMachine.openReferenceScreen.clicked(e.getX(), e.getY())) {
				this.ref.setVisible(true); // open up reference box
			} else { // clicked a product button
				for (int i = 0; i < this.productButtons.size(); i++) {
					if (this.productButtons.get(i).clicked(e.getX(), e.getY())) {
						// loads info of button selected before switching screen
						this.productButtonIndex = i;
						this.productButtonPrice = String.format("%.2f",
								this.productButtons.get(this.productButtonIndex).getPrice());

						if (!this.productButtons.get(this.productButtonIndex).getName().equals(UNDEFINED_BUTTON_NAME)) {
							this.productButtonName = this.productButtons.get(this.productButtonIndex).getName();
						} else {
							this.productButtonName = "";
						}
						// switch to button edit screen
						this.state = State.EditBUTTON;
						edit(this.productButtons.get(i), i);
					}
				}
			}
			break;
		// change name and price of particular button selected
		case EditBUTTON:
			if (CashMachine.startExitButton.clicked(e.getX(), e.getY())) {
				this.state = State.EditSELECT; // click x, go back to selection
												// menu
			} else if (CashMachine.editProductNameBounds.clicked(e.getX(), e.getY())) {
				// clicked on name selection ^
				this.productNameComplete = false; // flip boolean for
													// highlighting
				this.productPriceComplete = true;
			} else if (CashMachine.editProductPriceBounds.clicked(e.getX(), e.getY())) {
				// click on price selection ^

				// flip boolean for highlighting
				this.productNameComplete = true;
				this.productPriceComplete = false;
			} else if (CashMachine.editConfirmButton.clicked(e.getX(), e.getY())) {
				this.confirmButtonChanges(); // confirm product button changes
			}
			break;
		}
	}

	public void keyTyped(KeyEvent e) throws IOException {
		switch (this.state) {
		case SetupNAME:
			char temp = e.getKeyChar();

			if (temp == KeyEvent.VK_ENTER) {
				this.confirmCompanyName(); // confirm the company name entered
			}
			// for deleting character
			if (temp == KeyEvent.VK_BACK_SPACE && companyName.length() > 0) {
				companyName = companyName.substring(0, companyName.length() - 1);
			}
			// adds key customer presses to company name
			else if (temp != KeyEvent.VK_BACK_SPACE && companyName.length() < 16) {
				companyName += temp;
			}
			break;
		case StartORDER:
			temp = e.getKeyChar();

			if (temp == KeyEvent.VK_ENTER) {
				if (!customerNameComplete) {
					// customer name is now set to complete
					customerNameComplete = true;
				} else {
					this.loadCustomer(); // if customer name complete
					break;
				}
			}

			if (!customerNameComplete) { // input for name
				if (temp == KeyEvent.VK_BACK_SPACE && customerName.length() > 0) {
					customerName = customerName.substring(0, customerName.length() - 1);
				} else if (temp != KeyEvent.VK_BACK_SPACE && customerName.length() < 16) {
					customerName += temp;
				}
			} else if (!customerNumberComplete && temp != KeyEvent.VK_ENTER) {
				// look for input of number ^

				if (temp == KeyEvent.VK_BACK_SPACE && customerNumber.length() > 0) {
					customerNumber = customerNumber.substring(0, customerNumber.length() - 1);
				} else if (temp != KeyEvent.VK_BACK_SPACE && customerNumber.length() < 16) {
					customerNumber += temp;
				}
			}
			break;
		// click which product button you want to edit
		case EditSELECT:
			// hit esc to go back to start screen
			if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
				this.state = State.StartSCREEN;
			}
			break;
		// change name and price of particular button selected
		case EditBUTTON:
			temp = e.getKeyChar();

			if (temp == KeyEvent.VK_ESCAPE) { // esc to go back to select menu
				this.state = State.EditSELECT;
			} else {
				if (temp == KeyEvent.VK_ENTER) {
					if (!productNameComplete) {
						// product name now set to complete
						productNameComplete = true;
					} else {
						this.confirmButtonChanges(); // confirm product
					}
				}

				if (!productNameComplete) { // look for product name input
					if (temp == KeyEvent.VK_BACK_SPACE && productButtonName.length() > 0) {
						productButtonName = productButtonName.substring(0, productButtonName.length() - 1);
					} else if (temp != KeyEvent.VK_BACK_SPACE && productButtonName.length() < 16) {
						productButtonName += temp;
					}
				} else if (!productPriceComplete && temp != KeyEvent.VK_ENTER) {
					// look for product number input ^

					if (temp == KeyEvent.VK_BACK_SPACE && productButtonPrice.length() > 0) {
						productButtonPrice = productButtonPrice.substring(0, productButtonPrice.length() - 1);
					} else if (temp != KeyEvent.VK_BACK_SPACE && productButtonPrice.length() < 16) {
						productButtonPrice += temp;
					}
				}
			}
			break;
		default:
			break;
		}
	}

	// called when the user confirms the customer
	// called either my clicking on confirm or pressing enter
	// switches state from StartORDER to ORDER
	private void loadCustomer() throws IOException {
		// check to make sure that the customer number is of length 10
		if (this.customerNumber.length() == 10) {
			// if the customer loads successfully, instantiate transaction and
			// change the state to ORDER
			// else, create a new customer, instantiate transaction and change
			// the state to ORDER
			if (this.customer.load(this.customerName)) {
				this.transaction = new Transaction(this.customer);
				this.state = State.ORDER;
			} else {
				this.customer.create(this.customerName, this.customerNumber);
				this.transaction = new Transaction(this.customer);
				this.state = State.ORDER;
			}
		}
	}

	// called when the user confirms changes to the button
	// called either my clicking on confirm or pressing enter
	// switches state from EditBUTTON to EditSELECT
	private void confirmButtonChanges() throws IOException {
		// if the button name is not blank (""), set the button name to the
		// instance variable storing it
		// else, set the button name to the UNDEFINED_BUTTON_NAME
		if (!this.productButtonName.equals("")) {
			this.productButtons.get(this.productButtonIndex).setName(this.productButtonName);
		} else {
			this.productButtons.get(this.productButtonIndex).setName(UNDEFINED_BUTTON_NAME);
		}

		// if the button price is not blank (""), set the button price to the
		// instance variable storing it
		// else, set product button price to 0
		if (!this.productButtonPrice.equals("")) {
			this.productButtons.get(this.productButtonIndex).setPrice(Double.parseDouble(this.productButtonPrice));
		} else {
			this.productButtons.get(this.productButtonIndex).setPrice(0.0);
		}

		// reset instance variables so that it can receive input again
		this.productNameComplete = false;
		this.productPriceComplete = false;
		// write the changes to the product button file
		this.writeProductButtons();
		// change the state to EditSELECT
		this.state = State.EditSELECT;
	}

	// called when the user confirms the name of the company
	// called either my clicking on confirm or pressing enter
	// switches state from SetupNAME to EditSELECT
	private void confirmCompanyName() throws IOException {
		// declare and instantiate a filewriter for the settings file (where the
		// company name is stored)
		FileWriter wr = new FileWriter(this.settings);
		// write the company name to the file
		wr.write(this.companyName);
		// close the filewriter (finished writing)
		wr.close();
		// switch state to EditSELECT
		this.state = State.EditSELECT;
	}
}
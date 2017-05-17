package main;

import java.awt.event.MouseEvent;

public class CashMachine {
	
	private int state = 1; // 1-9 menus states
	
	
	
	public CashMachine(){
		configure();
	}
	
	public void configure(){
		
	}
	
	public void edit(){
		
	}
	
	public void run(){
		
	}
	
	public String getReceipt(){ // idk what type we should make this method to return receipt
		return" ";
	}
	
	public int getState(){
		return 0;
	}
	
	public int setState(int state){
		return this.state = state;
	}
	
	public void mouseClicked(MouseEvent e){ // idk if this is setup right
		
	}
}

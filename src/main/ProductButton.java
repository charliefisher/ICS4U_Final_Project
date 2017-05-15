package main;

public class ProductButton extends Button {

	private double price;

	public ProductButton(String name){
		super(name);
		
		price = 0.0; // do a text input here
	}
	
	@Override
	public boolean clicked() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public double getPrice(){
		return price;
	}
	
	public double setPrice(double price){
		return this.price = price;
	}

}

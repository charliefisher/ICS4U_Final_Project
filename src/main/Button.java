package main;

public abstract class Button {
	
	private String name = " ";

	public Button(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public String setName(String name){
		return this.name = name;
	}
	
	public abstract boolean clicked();
}

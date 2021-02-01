package classes;

import interfaces.savingData;
import javafx.scene.Scene;

public abstract class Person implements savingData {
	//private data fields.
	private String name;
	private String password;
	
	//constructor.
	Person(String name,String password){
		this.name=name;
		this.password=password;
	}
	
	//setters and getters.
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//abstract methods each class that inheritance from this class will have to implement.
	abstract double getTotalProfit();
	abstract Scene basicWiew(Store myStore);
	abstract void refresh(Store myStore);
	
	//basic string manipulation to save data.
	public String toString() {
		return this.name+"\n"+
				this.password;
	}
	
	
}

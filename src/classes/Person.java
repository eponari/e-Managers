package classes;

import interfaces.savingData;
import javafx.stage.Stage;

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

	public String getPassword() {
		return password;
	}

	//abstract methods each class that inheritance from this class will have to implement.
	abstract double getTotalProfit();
	abstract void showBasicWiew(Stage stg,Store myStore);
	abstract void refresh(Stage stg,Store myStore);
	
	//basic string manipulation to save data.
	public String toString() {
		return this.name+"\n"+
				this.password;
	}
	
	
}

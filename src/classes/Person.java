package classes;

import interfaces.savingData;
import javafx.scene.Scene;

public abstract class Person implements savingData {
	private String name;
	private String password;
	
	Person(String name,String password){
		this.name=name;
		this.password=password;
	}
	
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

	
	

	abstract double getTotalProfit();
	
	
	
	
	
	public String toString() {
		return this.name+"\n"+
				this.password;
	}
	
	abstract Scene basicWiew(Store myStore);
}

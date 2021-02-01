package classes;

import interfaces.savingData;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class SoldProduct implements savingData {
	//private data fields.
	private String name;
	private double sellingPrice;
	private int quantity;
	
	//constructor.
	public SoldProduct(String name,double sellingPrice, int quantity){
		this.name=name;
		this.sellingPrice=sellingPrice;
		this.quantity=quantity;
	}
	
	//setters and getters.
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getSellingPrice() {
		return sellingPrice;
	}
	
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	//methods.
	double getTotalPrice() {
		//return the total price of the bill.
		return this.sellingPrice*this.quantity;
	}
	
	//basic string manipulation to save data.
	public String toString() {
		return this.name+"\n"+
				this.sellingPrice+" "+this.quantity+"\n";
	}
	
	//HBox element in JavaFX to show data in Application.
	public HBox pane(){
		//create a HBox.
		HBox wiew= new HBox();
		
		//set spacing to 10px.
		wiew.setSpacing(10);
		
		//add the data fields to HBox.
		wiew.getChildren().addAll(
				new Label(this.name),
				new Label(Integer.toString(this.quantity)),
				new Label(Double.toString(this.sellingPrice)),
				new Label(Double.toString(this.getTotalPrice()))
		);
		
		//return the HBox.
		return wiew;
	}
}

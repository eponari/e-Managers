package classes;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class SoldProduct {
	String name;
	double sellingPrice;
	int quantity;
	
	public SoldProduct(String name,double sellingPrice, int quantity){
		this.name=name;
		this.sellingPrice=sellingPrice;
		this.quantity=quantity;
	}
	
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
	
	double getTotalPrice() {
		return this.sellingPrice*this.quantity;
	}
	
	public String toString() {
		return this.name+"\n"+
				this.sellingPrice+" "+this.quantity+"\n";
	}
	
	public HBox pane(){
		HBox wiew= new HBox();
		
		wiew.setSpacing(10);
				
		wiew.getChildren().addAll(
				new Label(this.name),
				new Label(Integer.toString(this.quantity)),
				new Label(Double.toString(this.sellingPrice)),
				new Label(Double.toString(this.getTotalPrice()))
		);
		
		return wiew;
	}
}

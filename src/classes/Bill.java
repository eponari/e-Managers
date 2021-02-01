package classes;

import java.util.ArrayList;

import interfaces.savingData;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Bill implements savingData {
	//private data fields.
	private ArrayList <SoldProduct> boughtProducts;
	private double totalPrice;
	
	//constructor.
	public Bill(){
		this.boughtProducts=new ArrayList<SoldProduct>();
		this.totalPrice=0;
	}
	
	//setter and getters.
	public ArrayList<SoldProduct> getBoughtProducts() {
		return boughtProducts;
	}

	public void setBoughtProducts(ArrayList<SoldProduct> boughtProducts) {
		this.boughtProducts = boughtProducts;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	//method to add and drop sold products.
	public void add(SoldProduct soldProduct) {
		boughtProducts.add(soldProduct);
		totalPrice+=soldProduct.getSellingPrice()*soldProduct.getQuantity();
	}
	
	public void drop(int index) {
		totalPrice-=(boughtProducts.get(index).getSellingPrice()*boughtProducts.get(index).getQuantity());
		boughtProducts.remove(index);
	}
	
	//borderPane to show the products bought in JavaFX.
	public BorderPane pane() {
		//create BorderPane.
		BorderPane fullScreen=new BorderPane();
		
		//create mainPane.
		GridPane mainPane=new GridPane();
		
		//set vertical and horizontal gap.
		mainPane.setVgap(20);
		mainPane.setHgap(20);
		
		//create a table head with labels.
		mainPane.add(new Label("Name"), 0, 0);
		mainPane.add(new Label("Quantity"), 1, 0);
		mainPane.add(new Label("Price"), 2, 0);
		mainPane.add(new Label("Total Price"), 3, 0);
		
		mainPane.setAlignment(Pos.CENTER);
		
		for(int i=0;i<this.getBoughtProducts().size();i++) {
			//add each element and its properties.
			mainPane.add(new Label(this.getBoughtProducts().get(i).getName()), 0, i+1);
			mainPane.add(new Label(Integer.toString(this.getBoughtProducts().get(i).getQuantity())), 1, i+1);
			mainPane.add(new Label(Double.toString(this.getBoughtProducts().get(i).getSellingPrice())), 2, i+1);
			mainPane.add(new Label(Double.toString(this.getBoughtProducts().get(i).getTotalPrice())), 3, i+1);
		}
		
		//add the total of the bill as a label in the end of the GridPane.
		mainPane.add(new Label("Total:"), 2, 1+this.getBoughtProducts().size());
		mainPane.add(new Label(Double.toString(this.totalPrice)), 3, 1+this.getBoughtProducts().size());
		
		//create a label and set a font for it.
		Label title=new Label("BILL");
		title.setFont(new Font("Arial", 24));
		HBox titleBox=new HBox();
		titleBox.getChildren().add(title);
		
		//add the title in the top.
		fullScreen.setTop(titleBox);
		
		titleBox.setAlignment(Pos.CENTER);
		
		//add the mainPane in the center of the BorderPane.
		fullScreen.setCenter(mainPane);
		
		//return the BorderPane.
		return fullScreen;
	}
	
	public String toString() {
		//create a StringBuffer and add each soldProduct.
		StringBuffer description= new StringBuffer(this.boughtProducts.size()+"\n");
		for(int i=0;i<this.boughtProducts.size();i++) {
			description.append(this.boughtProducts.get(i).toString());
		}
		//return the StringBuffer to String.
		return description.toString();
	}
	
}

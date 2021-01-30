package classes;

import java.util.ArrayList;

import interfaces.savingData;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Bill implements savingData {
	ArrayList <SoldProduct> boughtProducts;
	double totalPrice;
	
	public Bill(){
		this.boughtProducts=new ArrayList<SoldProduct>();
		this.totalPrice=0;
	}

	public ArrayList<SoldProduct> getBoughtProducts() {
		return boughtProducts;
	}

	public void setBoughtProducts(ArrayList<SoldProduct> boughtProducts) {
		this.boughtProducts = boughtProducts;
	}
	
	public void add(SoldProduct soldProduct) {
		boughtProducts.add(soldProduct);
		totalPrice+=soldProduct.getSellingPrice()*soldProduct.getQuantity();
	}
	public void drop(int index) {
		totalPrice-=(boughtProducts.get(index).getSellingPrice()*boughtProducts.get(index).getQuantity());
		boughtProducts.remove(index);
	}
	
	public BorderPane pane() {
		BorderPane fullScreen=new BorderPane();
		
		GridPane mainPane=new GridPane();
		
		mainPane.setVgap(20);
		mainPane.setHgap(20);
		
		mainPane.add(new Label("Name"), 0, 0);
		mainPane.add(new Label("Quantity"), 1, 0);
		mainPane.add(new Label("Price"), 2, 0);
		mainPane.add(new Label("Total Price"), 3, 0);
		
		mainPane.setAlignment(Pos.CENTER);
		
		for(int i=0;i<this.getBoughtProducts().size();i++) {
			mainPane.add(new Label(this.getBoughtProducts().get(i).getName()), 0, i+1);
			mainPane.add(new Label(Integer.toString(this.getBoughtProducts().get(i).getQuantity())), 1, i+1);
			mainPane.add(new Label(Double.toString(this.getBoughtProducts().get(i).getSellingPrice())), 2, i+1);
			mainPane.add(new Label(Double.toString(this.getBoughtProducts().get(i).getTotalPrice())), 3, i+1);
		}
		
		mainPane.add(new Label("Total:"), 2, 1+this.getBoughtProducts().size());
		mainPane.add(new Label(Double.toString(this.totalPrice)), 3, 1+this.getBoughtProducts().size());
		
		Label title=new Label("BILL");
		title.setFont(new Font("Arial", 24));
		HBox titleBox=new HBox();
		titleBox.getChildren().add(title);
		
		fullScreen.setTop(titleBox);
		
		
		titleBox.setAlignment(Pos.CENTER);
		
		fullScreen.setCenter(mainPane);
		
		return fullScreen;
	}
	
	public String toString() {
		StringBuffer description= new StringBuffer(this.boughtProducts.size()+"\n");
		for(int i=0;i<this.boughtProducts.size();i++) {
			description.append(this.boughtProducts.get(i).toString());
		}
		return description.toString();
	}
	
}

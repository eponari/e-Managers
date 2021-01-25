package classes;

import java.util.ArrayList;

import interfaces.savingData;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
	
	public VBox pane() {
		VBox mainPane=new VBox();
		mainPane.setSpacing(10);
		
		mainPane.setAlignment(Pos.CENTER);
		
		for(int i=0;i<this.getBoughtProducts().size();i++) {
			HBox currentPane=this.getBoughtProducts().get(i).pane();
			mainPane.getChildren().add(currentPane);
		}
		return mainPane;
	}
	
	public String toString() {
		StringBuffer description= new StringBuffer(this.boughtProducts.size()+"\n");
		for(int i=0;i<this.boughtProducts.size();i++) {
			description.append(this.boughtProducts.get(i).toString());
		}
		return description.toString();
	}
	
}

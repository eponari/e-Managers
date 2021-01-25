package classes;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Employee extends Person {
	private double baseWage;
	private int percentageTip;
	private ArrayList<Bill> bills;
	private HBox showTitle=new HBox();
	private HBox showWage=new HBox();
	private GridPane productTable=new GridPane();
	private VBox allBills=new VBox();
	private Scene scene;
	
	public Employee(String name,String password,double baseWage,int percentageTip){
		super(name,password);
		
		this.baseWage=baseWage;
		this.percentageTip=percentageTip;
		this.bills=new ArrayList<Bill>();
	}
	
	public double getBaseWage() {
		return baseWage;
	}



	public void setBaseWage(double baseWage) {
		this.baseWage = baseWage;
	}



	public int getPercentageTip() {
		return percentageTip;
	}

	

	public void setPercentageTip(int percentageTip) {
		this.percentageTip = percentageTip;
	}
	
	public ArrayList<Bill> getBills() {
		return bills;
	}

	public void setBills(ArrayList<Bill> bills) {
		this.bills = bills;
	}
	
	public void addBill(Bill newBill) {
		bills.add(newBill);
	}
	public void deleteBill(int index) {
		bills.remove(index);
	}
	
	double getTotalProfit() {
		double total=0;
		ArrayList<Bill> currentBills=this.getBills();
		for(int i=0;i<currentBills.size();i++) {
			total+=(currentBills.get(i).totalPrice);
		}
		return total;
	}

	double getTip() {
		return this.getTotalProfit()*(percentageTip/100.0);
	}
	
	public String toString() {
		StringBuffer description=new StringBuffer(super.toString()+"\n"+this.baseWage+" "+this.percentageTip+" "+this.bills.size()+"\n");
		
		for(int i=0;i<this.bills.size();i++) {
			description.append(this.bills.get(i).toString());
		}
		return description.toString();
	}
	
	VBox getBoxOfBills() {
		allBills.setSpacing(10);
		
		Label billsTitle=new Label("Bills");
		
		billsTitle.setStyle("-fx-font-weight: bold;");
		allBills.getChildren().add(billsTitle);
	    
	    
	    
		for(int i=0;i<this.getBills().size();i++) {
			Label nameOfBill= new Label("Bill "+(i+1));
			allBills.getChildren().add(nameOfBill);
			
			double currentTotalPrice=this.getBills().get(i).totalPrice;
			
			Bill currentBill=this.getBills().get(i);
			
			nameOfBill.setOnMouseClicked(e->{
				
				Scene scene= new Scene(currentBill.pane(),500,500);
				
				Stage stage=new Stage();
				stage.setScene(scene);
				stage.show();
			});
		}
		return allBills;
	}
	
	GridPane getBoxOfProducts(Store myStore) {

		productTable.setHgap(15);
		productTable.setVgap(15);
		
		Label name=new Label("Name");
		Label quantity=new Label("Quantity");
		Label price=new Label("Price");
		
		name.setStyle("-fx-font-weight: bold;");
		quantity.setStyle("-fx-font-weight: bold;");
		price.setStyle("-fx-font-weight: bold;");
		
		productTable.add(name,0, 0);
		productTable.add(quantity,1, 0);
		productTable.add(price,2, 0);
		
		
		
		for(int i=0;i<myStore.getProducts().size();i++) {
			productTable.add(new Label(myStore.getProducts().get(i).getName()),0,i+1);
			productTable.add(myStore.getProducts().get(i).getCountQuantity(),1,i+1);
			productTable.add(new Label(Double.toString(myStore.getProducts().get(i).getSellingPrice())),2,i+1);
		}
		
		Button buy=new Button("Buy");
		
		buy.setOnMouseClicked(e->{
			Bill newBill= new Bill();
			for(int i=0;i<myStore.getProducts().size();i++) {
				if(myStore.getProducts().get(i).getCountQuantity().getValue()!=0) {
					newBill.add(myStore.getProducts().get(i).getProductForBill());
				}
			}
			this.addBill(newBill);
			
			
		});
		
		productTable.add(buy,2,this.getBills().size()+1);
		productTable.setAlignment(Pos.BASELINE_RIGHT);
		return productTable;
	}
	
	HBox getBoxOfWage() {
		
		showWage.getChildren().addAll(new Text("Base Wage: "+this.getBaseWage()),new Text("Tips: "+this.getTip()));
		
		showWage.setSpacing(50);
		showWage.setAlignment(Pos.BASELINE_RIGHT);
		return showWage;
	}
	
	HBox getTitleBox() {
		
		Label message=new Label("Welcome "+this.getName()+"!");
		message.setFont(new Font("Arial", 24));
		showTitle.getChildren().add(message);
		showTitle.setAlignment(Pos.CENTER);
		return showTitle;
	}
	
	@Override
	public Scene basicWiew(Store myStore) {
		
		BorderPane mainPane=new BorderPane();
		
		GridPane centerPane=new GridPane();
		
		centerPane.setHgap(50);
		centerPane.setVgap(50);
		
		centerPane.setAlignment(Pos.CENTER);
		
		centerPane.add(this.getBoxOfBills(),0,0);
		centerPane.add(this.getBoxOfProducts(myStore),1,0);
		
		mainPane.setTop(this.getTitleBox());
		mainPane.setCenter(centerPane);
		mainPane.setBottom(this.getBoxOfWage());
		
		return new Scene(mainPane,500,500);
		
	}

}

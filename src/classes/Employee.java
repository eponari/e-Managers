package classes;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
	private HBox titleBox;
	private HBox wageBox;
	private GridPane tableOfProducts;
	private VBox boxOfBills;
	ScrollPane sp;
	Label message;
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
	
	double getProfitOfEmployee() {
		double total=0;
		for(int i=0;i<this.bills.size();i++) {
			total+=this.bills.get(i).totalPrice;
		}
		return total;
	}
	
	public String toString() {
		StringBuffer description=new StringBuffer(super.toString()+"\n"+this.baseWage+" "+this.percentageTip+" "+this.bills.size()+"\n");
		
		for(int i=0;i<this.bills.size();i++) {
			description.append(this.bills.get(i).toString());
		}
		return description.toString();
	}
	
	void refreshBoxOfBills() {
		this.boxOfBills=new VBox();
		this.boxOfBills.setSpacing(15);
		VBox currentBills=new VBox();
		currentBills.setSpacing(10);
		
		Label billsTitle=new Label("Bills");
		
		billsTitle.setStyle("-fx-font-weight: bold;");

		for(int i=0;i<this.getBills().size();i++) {
			Label nameOfBill= new Label("Bill "+(i+1));
			currentBills.getChildren().add(nameOfBill);
						
			Bill currentBill=this.getBills().get(i);
			
			nameOfBill.setOnMouseClicked(e->{
				
				Scene scene= new Scene(currentBill.pane(),300,(currentBill.getBoughtProducts().size()*70));
				
				Stage stage=new Stage();
				stage.setResizable(false);
				stage.getIcons().add(new Image("file:src/photos/logo.png"));
				stage.setTitle("Bill");
				stage.setScene(scene);
				stage.show();
			});
		}
		
		this.sp=new ScrollPane(currentBills);
		
		sp.setStyle("-fx-background-color:transparent;");
		sp.setPrefSize(100, 200);
		
		this.boxOfBills.getChildren().addAll(billsTitle,sp);
		
	}
	
	void refreshTableOfProducts(Store myStore) {
		this.tableOfProducts= new GridPane();
		this.tableOfProducts.setHgap(15);
		this.tableOfProducts.setVgap(15);
		
		Label name=new Label("Name");
		Label quantity=new Label("Quantity");
		Label price=new Label("Price");
		
		name.setStyle("-fx-font-weight: bold;");
		quantity.setStyle("-fx-font-weight: bold;");
		price.setStyle("-fx-font-weight: bold;");
		
		this.tableOfProducts.add(name,0, 0);
		this.tableOfProducts.add(quantity,1, 0);
		this.tableOfProducts.add(price,2, 0);
		
		
		
		for(int i=0;i<myStore.getProducts().size();i++) {
			this.tableOfProducts.add(new Label(myStore.getProducts().get(i).getName()),0,i+1);
			myStore.getProducts().get(i).refreshCountQuantity();
			this.tableOfProducts.add(myStore.getProducts().get(i).getCountQuantity(),1,i+1);
			this.tableOfProducts.add(new Label(Double.toString(myStore.getProducts().get(i).getSellingPrice())),2,i+1);
		}
		
		Button buy=new Button("Buy");
		
		buy.setOnMouseClicked(e->{
			Bill newBill= new Bill();
			for(int i=0;i<myStore.getProducts().size();i++) {
				if(myStore.getProducts().get(i).getCountQuantity().getValue()!=0) {
					newBill.add(myStore.getProducts().get(i).getProductForBill());
				}
			}
			if(newBill.getBoughtProducts().size()!=0) {
				this.addBill(newBill);
			}
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Bill information");
			alert.setHeaderText(null);
			alert.initOwner(scene.getWindow());
			alert.setContentText("The bill was recorded successfully! The products have been bought! Raport of bills is going to be updated soon!");

			alert.showAndWait();
		});
		
		this.tableOfProducts.add(buy,2,myStore.getProducts().size()+1);
		this.tableOfProducts.setAlignment(Pos.BASELINE_RIGHT);
	}
	
	void refreshBoxWage() {
		this.wageBox=new HBox();
		this.wageBox.getChildren().addAll(new Text("Base Wage: "+this.getBaseWage()),new Text("Tips: "+this.getTip()));
		
		this.wageBox.setSpacing(50);
		this.wageBox.setAlignment(Pos.BASELINE_RIGHT);
	}
	
	void refreshTitleBox() {
		this.message=new Label("Welcome "+this.getName()+"!");
		message.setFont(new Font("Arial", 24));
		titleBox=new HBox();
		titleBox.getChildren().add(message);
		titleBox.setAlignment(Pos.CENTER);
	}
	
	void refresh(Store myStore) {
		this.refreshBoxOfBills();
		this.refreshBoxWage();
		this.refreshTableOfProducts(myStore);
		this.refreshTitleBox();
	}
	
	void refreshScene(Store myStore) {
		BorderPane mainPane=new BorderPane();
		
		GridPane centerPane=new GridPane();
		
		this.refresh(myStore);
		
		centerPane.setHgap(50);
		centerPane.setVgap(50);
		
		centerPane.setAlignment(Pos.CENTER);
		
		centerPane.add(this.boxOfBills,0,0);
		centerPane.add(this.tableOfProducts,1,0);
		
		mainPane.setTop(this.titleBox);
		mainPane.setCenter(centerPane);
		mainPane.setBottom(this.wageBox);
		
		this.scene = new Scene(mainPane,500,500);
	}
	
	@Override
	public Scene basicWiew(Store myStore) {
		this.refreshScene(myStore);
		return this.scene;
	}

}

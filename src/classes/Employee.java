package classes;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Employee extends Person {
	//private data fields.
	private double baseWage;
	private int percentageTip;
	private ArrayList<Bill> bills;
	private HBox titleBox;
	private HBox wageBox;
	private GridPane tableOfProducts;
	private VBox boxOfBills;
	private ScrollPane sp;
	private Label message;
	private Scene scene;
	
	//constructor.
	public Employee(String name,String password,double baseWage,int percentageTip){
		super(name,password);
		this.baseWage=baseWage;
		this.percentageTip=percentageTip;
		this.bills=new ArrayList<Bill>();
	}
	
	//setters and getters.
	public double getBaseWage() {
		return baseWage;
	}

	public int getPercentageTip() {
		return percentageTip;
	}

	public ArrayList<Bill> getBills() {
		return bills;
	}

	//methods to add and delete bills from employee.
	public void addBill(Bill newBill) {
		bills.add(newBill);
	}
	public void deleteBill(int index) {
		bills.remove(index);
	}
	
	//methods for financial analysis.
	@Override
	double getTotalProfit() {
		double total=0;
		ArrayList<Bill> currentBills=this.getBills();
		for(int i=0;i<currentBills.size();i++) {
			total+=(currentBills.get(i).getTotalPrice());
		}
		return total;
	}

	double getTip() {
		return this.getTotalProfit()*(percentageTip/100.0);
	}
	
	double getProfitOfEmployee() {
		double total=0;
		for(int i=0;i<this.bills.size();i++) {
			total+=this.bills.get(i).getTotalPrice();
		}
		return total;
	}
	
	//using StringBuffer to get all the information of a employee in a string.
	public String toString() {
		StringBuffer description=new StringBuffer(super.toString()+"\n"+this.baseWage+" "+this.percentageTip+" "+this.bills.size()+"\n");
		
		for(int i=0;i<this.bills.size();i++) {
			description.append(this.bills.get(i).toString());
		}
		return description.toString();
	}
	
	//methods to refresh the JavaFx elements.
	void refreshBoxOfBills() {
		//create a VBox to contain the title of bills and a scrollPane with bills and set spacing as 15px.
		this.boxOfBills=new VBox();
		this.boxOfBills.setSpacing(15);
		
		//create a VBox for the current bills you are working with and set spacing as 10px.
		VBox currentBills=new VBox();
		currentBills.setSpacing(10);
		
		//create a label and give it a style.
		Label billsTitle=new Label("Bills");
		
		billsTitle.setStyle("-fx-font-weight: bold;");

		for(int i=0;i<this.getBills().size();i++) {
			//create a label and add it to the currentBills.
			Label nameOfBill= new Label("Bill "+(i+1));
			currentBills.getChildren().add(nameOfBill);
			
			//take the bill you are currently working with.
			Bill currentBill=this.getBills().get(i);
			
			//when the label is mouse clicked.
			nameOfBill.setOnMouseClicked(e->{
				//create a scene with the pane of the current bill.
				Scene scene= new Scene(currentBill.pane(),300,(currentBill.getBoughtProducts().size()*70));
				
				//create a stage.
				Stage stage=new Stage();
				//make it not resizable and set title and logo of the stage.
				stage.setResizable(false);
				stage.getIcons().add(new Image("file:src/photos/logo.png"));
				stage.setTitle("Bill");
				//set scene of stage and show the stage.
				stage.setScene(scene);
				stage.show();
			});
		}
		//create a scrollPane with the currentBills.
		this.sp=new ScrollPane(currentBills);
		//set style by making it transparent and setting a preferable size.
		sp.setStyle("-fx-background-color:transparent;");
		sp.setPrefSize(100, 200);
		
		//add the billsTitle and scrollPane to the box of bills.
		this.boxOfBills.getChildren().addAll(billsTitle,sp);
	}
	
	void refreshTableOfProducts(Store myStore) {
		//create a gridPane and set the vertical and horizontal gap.
		this.tableOfProducts= new GridPane();
		this.tableOfProducts.setHgap(15);
		this.tableOfProducts.setVgap(15);
		
		//create labels for the table and set them as bold.
		Label name=new Label("Name");
		Label quantity=new Label("Quantity");
		Label price=new Label("Price");
		
		name.setStyle("-fx-font-weight: bold;");
		quantity.setStyle("-fx-font-weight: bold;");
		price.setStyle("-fx-font-weight: bold;");
		
		//add table head to the table of products.
		this.tableOfProducts.add(name,0, 0);
		this.tableOfProducts.add(quantity,1, 0);
		this.tableOfProducts.add(price,2, 0);
		
		//for each product.
		for(int i=0;i<myStore.getProducts().size();i++) {
			//add its data fields to make them visible in the application.
			this.tableOfProducts.add(new Label(myStore.getProducts().get(i).getName()),0,i+1);
			myStore.getProducts().get(i).refreshCountQuantity();
			this.tableOfProducts.add(myStore.getProducts().get(i).getCountQuantity(),1,i+1);
			this.tableOfProducts.add(new Label(Double.toString(myStore.getProducts().get(i).getSellingPrice())),2,i+1);
		}
		
		//create a button.
		Button buy=new Button("Buy");
		
		//when the button is mouse clicked.
		buy.setOnMouseClicked(e->{
			//create a new bill.
			Bill newBill= new Bill();
			for(int i=0;i<myStore.getProducts().size();i++) {
				//if a spinner's value isn't 0.
				if(myStore.getProducts().get(i).getCountQuantity().getValue()!=0) {
					//create a new soldProduct and add it to the bill.
					newBill.add(myStore.getProducts().get(i).getProductForBill());
				}
				//set all spinners as 0.
				myStore.getProducts().get(i).getCountQuantity().getValueFactory().setValue(0);
			}
			
			//if there is a product in the bill.
			if(newBill.getBoughtProducts().size()!=0) {
				
				//add it to the employee's bills.
				this.addBill(newBill);
				
				//create a alert and show that the bill was created successfully.
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Bill information");
				alert.setHeaderText(null);
				alert.initOwner(scene.getWindow());
				alert.setContentText("The bill was recorded successfully! The products have been bought! Raport of bills is going to be updated soon!");

				alert.showAndWait();
			}
		});
		
		//add the buy button and get it to the right of baseline.
		this.tableOfProducts.add(buy,2,myStore.getProducts().size()+1);
		this.tableOfProducts.setAlignment(Pos.BASELINE_RIGHT);
	}
	
	void refreshBoxWage() {
		//create a HBox and add the base wage and tip the employee is gaining.
		this.wageBox=new HBox();
		this.wageBox.getChildren().addAll(new Text("Base Wage: "+this.getBaseWage()),new Text("Tips: "+this.getTip()));
		
		//set position and spacing.
		this.wageBox.setSpacing(50);
		this.wageBox.setAlignment(Pos.BASELINE_RIGHT);
	}
	
	void refreshTitleBox() {
		//create a label to welcome the employee.
		this.message=new Label("Welcome "+this.getName()+"!");
		
		//set some style.
		message.setFont(new Font("Arial", 24));
		
		//create a HBox to display the label and set position as center.
		titleBox=new HBox();
		titleBox.getChildren().add(message);
		titleBox.setAlignment(Pos.CENTER);
	}
	
	void refreshScene(Store myStore) {
		//create a borderPane and as mainPane.
		BorderPane mainPane=new BorderPane();
		
		//create a gridPane as centerPane.
		GridPane centerPane=new GridPane();
		
		//refresh the JavaFX elements.
		this.refresh(myStore);
		
		//set spacing and positioning.
		centerPane.setHgap(50);
		centerPane.setVgap(50);
		
		centerPane.setAlignment(Pos.CENTER);
		
		//add the box of bills and table of products side by side.
		centerPane.add(this.boxOfBills,0,0);
		centerPane.add(this.tableOfProducts,1,0);
		
		//add the top bottom and center of the border pane.
		mainPane.setTop(this.titleBox);
		mainPane.setCenter(centerPane);
		mainPane.setBottom(this.wageBox);
		
		//create a new scene using this border pane.
		this.scene = new Scene(mainPane,500,500);
	}
	
	//abstract methods we need to implement.
	
	@Override
	void refresh(Store myStore) {
		//refresh all JavaFX elements.
		this.refreshBoxOfBills();
		this.refreshBoxWage();
		this.refreshTableOfProducts(myStore);
		this.refreshTitleBox();
	}
	
	@Override
	public Scene basicWiew(Store myStore) {
		//return the refreshed scene.
		this.refreshScene(myStore);
		return this.scene;
	}

}

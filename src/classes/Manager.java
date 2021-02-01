package classes;

import java.util.ArrayList;

import com.sun.prism.paint.Color;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class Manager extends Person {
	private ArrayList<Employee> myEmployees;
	private GridPane productTable;
	private GridPane employeeTable;
	private HBox welcomeMessage;
	private HBox profitBox;
	private BorderPane mainPane;
	private Scene mainScreen;
	
	
	Manager(String name,String password){
		super(name,password);
		this.myEmployees=new ArrayList<Employee>();
	}
	
	public ArrayList<Employee> getMyEmployees() {
		return myEmployees;
	}

	public void setMyEmployees(ArrayList<Employee> myEmployees) {
		this.myEmployees = myEmployees;
	}
	
	public void addEmployee(Employee newEmployee) {
		this.myEmployees.add(newEmployee);
	}
	public void dropEmployee(int index) {
		this.myEmployees.remove(index);
	}
	
	public double getTotalProfit() {
		double total=0;
		ArrayList<Employee> currentEmployees=this.getMyEmployees();
		for(int i=0;i<currentEmployees.size();i++) {
			total+=(currentEmployees.get(i).getTotalProfit()-currentEmployees.get(i).getBaseWage()-currentEmployees.get(i).getTip());
		}
		return total;
	}
	
	public String toString() {
		StringBuffer description=new StringBuffer(super.toString()+"\n"+this.myEmployees.size()+"\n");
		for(int i=0;i<this.myEmployees.size();i++) {
			description.append(this.myEmployees.get(i).toString());
		}
		return description.toString();
	}
	
	void refreshProductTable(Store myStore) {
		this.productTable=new GridPane();
		
		Label name=new Label("Name");
		Label quantity=new Label("Quantity");
		Label refill=new Label("Refill amount");
		Label request=new Label("Request");
		
		name.setStyle("-fx-font-weight: bold;");
		quantity.setStyle("-fx-font-weight: bold;");
		refill.setStyle("-fx-font-weight: bold;");
		request.setStyle("-fx-font-weight: bold;");
		
		this.productTable.add(name, 0, 0);
		this.productTable.add(quantity, 1, 0);
		this.productTable.add(refill, 2, 0);
		this.productTable.add(request, 3, 0);
		
		this.productTable.setVgap(20);
		this.productTable.setHgap(20);
		
		
		for(int i=0;i<myStore.getProducts().size();i++) {
			Button addQuantity = new Button("+");
			
			Product currentProduct=myStore.getProducts().get(i);
			
			addQuantity.setOnMouseClicked(e->{
				currentProduct.setQuantity(currentProduct.getQuantity()+currentProduct.getRefill());
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Refill information");
				alert.setHeaderText(null);
				alert.initOwner(mainScreen.getWindow());
				alert.setContentText("The refill is successful! The products will come soon!");

				alert.showAndWait();
				
			});
			
			this.productTable.add(new Label(myStore.getProducts().get(i).getName()), 0, i+1);
			this.productTable.add(new Label(Integer.toString(myStore.getProducts().get(i).getQuantity())), 1, i+1);
			this.productTable.add(new Label(Integer.toString(myStore.getProducts().get(i).getRefill())), 2, i+1);
			this.productTable.add(addQuantity, 3, i+1);
		}
	}
	
	void refreshEmployeeTable() {
		this.employeeTable=new GridPane();
		
		Label name=new Label("Name");
		Label profit=new Label("Profit for the store");
		
		name.setStyle("-fx-font-weight: bold;");
		profit.setStyle("-fx-font-weight: bold;");
		
		this.employeeTable.add(name, 0, 0);
		this.employeeTable.add(profit, 1, 0);
		
		this.employeeTable.setVgap(30);
		this.employeeTable.setHgap(30);
		
		for(int i=0;i<this.getMyEmployees().size();i++) {
			this.employeeTable.add(new Label(this.getMyEmployees().get(i).getName()), 0, i+1);
			this.employeeTable.add(new Label(Double.toString(this.getMyEmployees().get(i).getTotalProfit())), 1, i+1);
		}
	}
	
	void refreshWelcomeMessage(Store myStore) {
		this.welcomeMessage=new HBox();
				
		Label message= new Label("Welcome "+myStore.getMainManager().getName()+" !");
		
		message.setFont(new Font("Arial", 24));
		
		welcomeMessage.getChildren().add(message);
		
		welcomeMessage.setAlignment(Pos.CENTER);
		
	}
	
	void refreshProfitBox() {
		this.profitBox= new HBox();
		
		this.profitBox.setSpacing(20);
		
		this.profitBox.getChildren().add(new Label("Total revenue: "+Double.toString(this.getTotalProfit())));
		this.profitBox.setAlignment(Pos.BOTTOM_RIGHT);
	}
		
	void refresh(Store myStore) {
		this.refreshEmployeeTable();
		this.refreshProductTable(myStore);
		this.refreshProfitBox();
		this.refreshWelcomeMessage(myStore);
	}
	
	@Override
	public Scene basicWiew(Store myStore) {
		this.refresh(myStore);

		this.mainPane=new BorderPane();
		this.mainPane.setTop(this.welcomeMessage);
		this.mainPane.setBottom(this.profitBox);
		
		
		
		GridPane centerPane= new GridPane();
		
		Label employeesLabel=new Label("Employees");
		Label productsLabel=new Label("Products");
		
		employeesLabel.setStyle("-fx-font-weight: bold;");
		productsLabel.setStyle("-fx-font-weight: bold;");
		
		centerPane.add(employeesLabel, 0, 0);
		centerPane.add(productsLabel, 1, 0);
		centerPane.add(this.employeeTable, 0, 1);
		centerPane.add(this.productTable, 1, 1);
		
		centerPane.setVgap(20);
		centerPane.setHgap(20);
		
		centerPane.setAlignment(Pos.CENTER);
		
		this.mainPane.setCenter(centerPane);
		this.mainScreen=new Scene(this.mainPane,500,500);
		return this.mainScreen;
	}
}

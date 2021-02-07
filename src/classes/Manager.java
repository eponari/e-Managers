package classes;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Manager extends Person {
	//private data fields.
	private ArrayList<Employee> myEmployees;
	private GridPane productTable;
	private GridPane employeeTable;
	private ScrollPane spEmployee;
	private HBox welcomeMessage;
	private HBox profitBox;
	private BorderPane mainPane;
	private Button addEmployee=new Button("Add new employee");
	private Scene mainScreen;
	
	//constructor.
	Manager(String name,String password){
		super(name,password);
		this.myEmployees=new ArrayList<Employee>();
	}
	
	//setters and getters.
	public ArrayList<Employee> getMyEmployees() {
		return myEmployees;
	}
	
	//methods to add and drop employees.
	public void addEmployee(Employee newEmployee) {
		this.myEmployees.add(newEmployee);
	}
	
	public void dropEmployee(int index) {
		this.myEmployees.remove(index);
	}
	
	//calculate total profit from the employees.
	@Override
	public double getTotalProfit() {
		double total=0;
		ArrayList<Employee> currentEmployees=this.getMyEmployees();
		for(int i=0;i<currentEmployees.size();i++) {
			total+=(currentEmployees.get(i).getTotalProfit()-currentEmployees.get(i).getBaseWage()-currentEmployees.get(i).getTip());
		}
		return total;
	}
	
	//save data to string.
	public String toString() {
		StringBuffer description=new StringBuffer(super.toString()+"\n"+this.myEmployees.size()+"\n");
		for(int i=0;i<this.myEmployees.size();i++) {
			description.append(this.myEmployees.get(i).toString());
		}
		return description.toString();
	}
	
	//methods to refresh JavaFX elements.
	
	void refreshProductTable(Stage stg,Store myStore) {
		//create a gridPane.
		this.productTable=new GridPane();
		
		//create bold labels for the gridPane.
		Label name=new Label("Name");
		Label quantity=new Label("Quantity");
		Label refill=new Label("Refill amount");
		Label request=new Label("Request");
		
		name.setStyle("-fx-font-weight: bold;");
		quantity.setStyle("-fx-font-weight: bold;");
		refill.setStyle("-fx-font-weight: bold;");
		request.setStyle("-fx-font-weight: bold;");
		
		//add labels to the gridPane.
		this.productTable.add(name, 0, 0);
		this.productTable.add(quantity, 1, 0);
		this.productTable.add(refill, 2, 0);
		this.productTable.add(request, 3, 0);
		
		//set the spacing.
		this.productTable.setVgap(20);
		this.productTable.setHgap(20);
		
		//for each product.
		for(int i=0;i<myStore.getProducts().size();i++) {
			//create a button with "+" character inside.
			Button addQuantity = new Button("+");
			
			//get the currentProduct we have.
			Product currentProduct=myStore.getProducts().get(i);
			
			//when the button is mouse clicked.
			addQuantity.setOnMouseClicked(e->{
				//set the quantity of the product as its quantity+refill size.
				currentProduct.setQuantity(currentProduct.getQuantity()+currentProduct.getRefill());
				
				//create and show an alert that tells the user the refill was successful and the products are coming.
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Refill information");
				alert.setHeaderText(null);
				alert.initOwner(mainScreen.getWindow());
				alert.setContentText("The refill is successful! The products will come soon!");

				alert.showAndWait();
				
				this.showBasicWiew(stg, myStore);
				
			});
			
			//add the information of the current product we are dealing with.
			this.productTable.add(new Label(myStore.getProducts().get(i).getName()), 0, i+1);
			this.productTable.add(new Label(Integer.toString(myStore.getProducts().get(i).getQuantity())), 1, i+1);
			this.productTable.add(new Label(Integer.toString(myStore.getProducts().get(i).getRefill())), 2, i+1);
			this.productTable.add(addQuantity, 3, i+1);
		}
	}
	
	void refreshEmployeeTable() {
		//create gridPane.
		this.employeeTable=new GridPane();
		
		GridPane title=new GridPane();
		//create Labels with bold style.
		Label name=new Label("Name");
		Label profit=new Label("Profit for the store");
		
		name.setStyle("-fx-font-weight: bold;");
		profit.setStyle("-fx-font-weight: bold;");
		
		//add them to the gridPane.
		title.add(name, 0, 0);
		title.add(profit, 1, 0);
		
		GridPane currentEmployees=new GridPane();
		
		//set spacing.
		title.setVgap(30);
		title.setHgap(30);
		currentEmployees.setVgap(30);
		currentEmployees.setHgap(30);
		
		//get the information for each employee.
		for(int i=0;i<this.getMyEmployees().size();i++) {
			currentEmployees.add(new Label(this.getMyEmployees().get(i).getName()), 0, i);
			currentEmployees.add(new Label(Double.toString(this.getMyEmployees().get(i).getTotalProfit())), 1, i);
		}
		
		spEmployee= new ScrollPane(currentEmployees);
		
		spEmployee.setPrefSize(100, 230);
		
		spEmployee.setStyle("-fx-background-color:transparent;");
		
		this.employeeTable.add(title, 0, 0);
		this.employeeTable.add(spEmployee, 0, 1);
		this.employeeTable.setVgap(20);
	}
	
	void refreshWelcomeMessage() {
		//create a HBox and display a welcoming message.
		this.welcomeMessage=new HBox();
				
		Label message= new Label("Welcome "+this.getName()+" !");
		
		message.setFont(new Font("Arial", 24));
		
		welcomeMessage.getChildren().add(message);
		
		welcomeMessage.setAlignment(Pos.CENTER);
	}
	
	void refreshProfitBox() {
		//create a HBox and display the total revenue.
		this.profitBox= new HBox();
		
		this.profitBox.setSpacing(20);
		
		this.profitBox.getChildren().add(new Label("Total revenue: "+Double.toString(Math.round(this.getTotalProfit()))));
		this.profitBox.setAlignment(Pos.BOTTOM_RIGHT);
	}
	
	void refreshButton(Stage stg,Store myStore) {
		this.addEmployee.setOnMouseClicked(e->{
			//stage to register the new employee.
			Stage registerStage=new Stage();
			
			//title we give the registration form.
			Label title= new Label("New employee");
			title.setAlignment(Pos.CENTER);
			title.setFont(new Font("Arial", 24));
			
			//gridpane to hold the data from the user.
			GridPane registerForm=new GridPane();
			
			registerForm.setHgap(10);
			registerForm.setVgap(15);
			
			registerForm.add(new Label("Name: "), 0, 0);
			registerForm.add(new Label("Password: "), 0, 1);
			registerForm.add(new Label("Base wage: "), 0, 2);
			registerForm.add(new Label("Percentage tip: "), 0, 3);
			
			TextField getName=new TextField();
			PasswordField getPassword=new PasswordField();
			TextField getBaseWage=new TextField();
			
			
			Spinner<Integer> getPercentageTip=new Spinner<Integer>(0,20,0);
			Button add=new Button("Add");
			
			registerForm.add(getName, 1, 0);
			registerForm.add(getPassword, 1, 1);
			registerForm.add(getBaseWage, 1, 2);
			registerForm.add(getPercentageTip, 1, 3);
			registerForm.add(add, 1, 4);
			
			//when add button is clicked.
			add.setOnMouseClicked(f->{
				try {
					//try to create a new employee and add it to the store.
					Employee newEmployee=new Employee(getName.getText(),getPassword.getText(),Double.parseDouble(getBaseWage.getText()),getPercentageTip.getValue());
					
					this.addEmployee(newEmployee);
					
					//tell the user the addition was done.
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Employee added");
					alert.setHeaderText(null);
					alert.initOwner(mainScreen.getWindow());
					alert.setContentText("The new employee is added successfully!");

					alert.showAndWait();
					
					//close registration form.
					registerStage.close();
					
					//refresh stage.
					this.showBasicWiew(stg, myStore);					
				}
				catch(Exception p) {
					//if we input isn't right(wage has characters in it.) tell the user something is wrong.
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Input mismatch!");
					alert.setHeaderText(null);
					alert.initOwner(mainScreen.getWindow());
					alert.setContentText("This data can't be used to create an employee!");
					alert.showAndWait();
				}
				
			});
			
			//set aligment.
			registerForm.setAlignment(Pos.CENTER);
			
			//organize everything in a borderpane.
			BorderPane fullScreen= new BorderPane();
			
			fullScreen.setTop(title);
			BorderPane.setAlignment(title, Pos.CENTER);
			
			fullScreen.setCenter(registerForm);
			
			//show the new stage.
			registerStage.setScene(new Scene(fullScreen,500,500));
			registerStage.show();
			
		});
				
	}
	
	@Override
	void refresh(Stage stg,Store myStore) {
		//use all the refresh methods.
		this.refreshEmployeeTable();
		this.refreshProductTable(stg,myStore);
		this.refreshProfitBox();
		this.refreshWelcomeMessage();
		this.refreshButton(stg,myStore);
	}
	
	@Override
	public void showBasicWiew(Stage stg,Store myStore) {
		//refresh the JavaFX elements.
		this.refresh(stg,myStore);
		
		//create a borderPane and set its Top and Bottom.
		this.mainPane=new BorderPane();
		this.mainPane.setTop(this.welcomeMessage);
		this.mainPane.setBottom(this.profitBox);
		
		//create a gridPane for the center of BorderPane.
		GridPane centerPane= new GridPane();
		
		//create bold labels.
		Label employeesLabel=new Label("Employees");
		Label productsLabel=new Label("Products");
		
		employeesLabel.setStyle("-fx-font-weight: bold;");
		productsLabel.setStyle("-fx-font-weight: bold;");
				
		//add the labels and the panes to the centerPane.
		centerPane.add(employeesLabel, 0, 0);
		centerPane.add(productsLabel, 1, 0);
		centerPane.add(this.employeeTable, 0, 1);
		centerPane.add(this.productTable, 1, 1);
		centerPane.add(addEmployee,0,2);
		
		//set spacing and positioning.
		centerPane.setVgap(20);
		centerPane.setHgap(20);
		
		centerPane.setAlignment(Pos.CENTER);
		
		//set the center of the mainPane.
		this.mainPane.setCenter(centerPane);
		
		//create a scene and return it.
		this.mainScreen=new Scene(this.mainPane,500,500);
		
		stg.setScene(mainScreen);
		stg.show();
	}
}

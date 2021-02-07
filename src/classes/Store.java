package classes;

import java.util.ArrayList;

import interfaces.savingData;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Store implements savingData {
	//private data fields.
	private Manager mainManager;
	private ArrayList<Product> products;
	
	//constructor.
	Store(){
		this.products=new ArrayList<Product>(); 
	}
	
	//getters and setters.
	public Manager getMainManager() {
		return mainManager;
	}
	
	public void setMainManager(Manager mainManager) {
		this.mainManager = mainManager;
	}
	
	public ArrayList<Product> getProducts() {
		return products;
	}
	
	//methods to add and delete products from store.
	void addProduct(Product newProduct) {
		this.products.add(newProduct);
	}
	
	void removeProduct(int index) {
		this.products.remove(index);
	}
	
	//method to create a login into the store application.
	public void storeScene(Stage stg) {
		//create a gridPane.
		GridPane login=new GridPane();
		
		//specify positioning and spacing.
		login.setAlignment(Pos.CENTER);
		
		login.setHgap(10);
		login.setVgap(10);
		
		//create textFields to get the information from the user and a button.
		TextField username=new TextField();
		TextField password=new PasswordField();
		Button loginBTN=new Button("Login");
		
		//add them to the gridPane.
		login.add(new Label("Username:"), 0, 0);
		login.add(username, 1,0);
		login.add(new Label("Password:"), 0, 1);
		login.add(password, 1, 1);
		login.add(loginBTN,1,2);
		
		//create a scene with the gridPane.
		Scene scene= new Scene(login,500,300);
		
		//when the button is mouse clicked.
		loginBTN.setOnMouseClicked(e->{
			//we assume you are not logged in.
			Boolean loginCompleted=false;
			
			//create 2 strings to keep track of the data we got with the textFields.
			String usernameUsed=username.getText();
			String passwordUsed=password.getText();
			
			//create a stage called message which we will show the user.
			Stage message=new Stage();
			
			//set title and logo.
			message.setTitle("e Managers");
			message.getIcons().add(new Image("file:src/photos/logo.png"));
			
			//we check is this is the manager since he is unique.
			if(usernameUsed.equals(this.getMainManager().getName())&&passwordUsed.equals(this.getMainManager().getPassword())) {

				//set the scene as the scene of our manager and show it.
				this.getMainManager().showBasicWiew(message,this);
				//end the function of the button.
				return;
			}
			
			else {
				
				//look at each employee we have.
				for(int i=0;i<this.getMainManager().getMyEmployees().size();i++) {
					
					//take care of the current employee we are in.
					Employee currentEmp=this.getMainManager().getMyEmployees().get(i);
					if(usernameUsed.equals(currentEmp.getName())&&passwordUsed.equals(currentEmp.getPassword())) {
												
						//set the scene as the scene of this employee and show it.
						currentEmp.showBasicWiew(message, this);
						
						//end the function of the button.
						return;
					}
				}
			}
			
			//if we still aren't logged in.
			if(!loginCompleted) {
				//create a alert to tell the user theirs userName or password is wrong.
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Refill information");
				alert.setHeaderText(null);
				alert.setContentText("The username or password you tried is incorrect! Please try again!");
				alert.initOwner(scene.getWindow());
				alert.showAndWait();
			}		
		});
		
		//setScene of this stage as our scene.
		stg.setScene(scene);
	}
	
	//using a stringBuffer, turn the data of this object into a string.
	public String toString() {
		StringBuffer description=new StringBuffer(this.mainManager.toString()+"\n"+this.products.size()+"\n");
		
		for(int i=0;i<this.products.size();i++) {
			description.append(this.products.get(i).toString());
		}
		
		return description.toString();
	}
	
}

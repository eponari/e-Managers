package classes;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Store {
	private Manager mainManager;
	private ArrayList<Product> products;
	DataParser read;
	
	Store(){
		this.products=new ArrayList<Product>(); 
		
	}
	
	public Manager getMainManager() {
		return mainManager;
	}
	public void setMainManager(Manager mainManager) {
		this.mainManager = mainManager;
	}
	
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
	void addProduct(Product newProduct) {
		this.products.add(newProduct);
	}
	void removeProduct(int index) {
		this.products.remove(index);
	}
	
	public Scene storeScene() {
		GridPane login=new GridPane();
		
		login.setAlignment(Pos.CENTER);
		
		login.setHgap(10);
		login.setVgap(10);
		
		TextField username=new TextField();
		TextField password=new PasswordField();
		Button loginBTN=new Button("Login");
		
		login.add(new Label("Username:"), 0, 0);
		login.add(username, 1,0);
		login.add(new Label("Password:"), 0, 1);
		login.add(password, 1, 1);
		login.add(loginBTN,1,2);
		login.add(new Spinner(0,5,0),2,2);
		
		Scene scene= new Scene(login,500,300);
		
		loginBTN.setOnMouseClicked(e->{
			Boolean loginCompleted=false;
			
			String usernameUsed=username.getText();
			String passwordUsed=password.getText();
			
			Stage message=new Stage();
			Label showLabel=new Label();
			
			if(usernameUsed.equals(this.getMainManager().getName())&&passwordUsed.equals(this.getMainManager().getPassword())) {
				showLabel.setText("A manager is logged on!");
				loginCompleted=true;
			}
			
			else {
				for(int i=0;i<this.getMainManager().getMyEmployees().size();i++) {
					Employee currentEmp=this.getMainManager().getMyEmployees().get(i);
					if(usernameUsed.equals(currentEmp.getName())&&passwordUsed.equals(currentEmp.getPassword())) {
						message.setScene(currentEmp.basicWiew(this));
						message.show();
						
						return;
					}
				}
			}
			
			if(!loginCompleted) {
				showLabel.setText("This user does not exist! Try again!");
			}
			
			Scene succesfulLogin=new Scene(showLabel,150,150);
			message.setScene(succesfulLogin);
			message.show();
			
		});
		return scene;
	}
	
	
		
	public String toString() {
		StringBuffer description=new StringBuffer(this.mainManager.toString()+"\n"+this.products.size()+"\n");
		
		for(int i=0;i<this.products.size();i++) {
			description.append(this.products.get(i).toString());
		}
		
		return description.toString();
	}
	
}

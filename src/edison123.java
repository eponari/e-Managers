import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import classes.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafxClasses.Login;

public class edison123 extends Application{
	
	String dataFile="src/data/data.txt";
	Store myStore;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stg) throws Exception {
		try {
			// TODO 
			DataParser read=new DataParser();
			Scanner input=new Scanner(new File(this.dataFile)).useLocale(Locale.US);;
			
			this.myStore= read.readStore(input);
			Manager mymanager=myStore.getMainManager();
			ArrayList<Product> myproducts=myStore.getProducts();
			ArrayList<Employee> myemployees=mymanager.getMyEmployees();
				
			input.close();
			
			stg.setTitle("e Managers");
			stg.getIcons().add(new Image("file:src/photos/logo.png"));
			stg.setScene(myStore.storeScene());
			stg.show();
		}
		
		catch (FileNotFoundException e) {
			System.out.println("Something is wrong with the file!");
		}
	}
	
	@Override
	public void stop() {
		PrintWriter output;
		try {
			output = new PrintWriter(new File(this.dataFile));
			output.write(this.myStore.toString());
			output.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Something is wrong!");
		}
	}
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;
import classes.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{
	
	//the file where the data is read and stored.
	private File dataFile=new File("src/data/data.txt");
	
	//the store the application is supervising.
	private Store myStore;
	
	//main method to launch the application.
	public static void main(String[] args) {
		launch(args);
	}

	//when the application starts.
	@Override
	public void start(Stage stg) throws Exception {
		try {
			// Create a dataParser that I have created to read the info in files. 
			DataParser read=new DataParser();
			//create a scanner using the file.
			Scanner input=new Scanner(this.dataFile).useLocale(Locale.US);;
			
			//read the store using the dataParser.
			this.myStore= read.readStore(input);
		
			//close the input.
			input.close();
			
			//set the title and the logo of the application.
			stg.setTitle("e Managers");
			stg.getIcons().add(new Image("file:src/photos/logo.png"));
			
			//set Scene as the scene from the store.
			stg.setScene(myStore.storeScene());
			stg.show();
		}
		//catch the error in case something is wrong in the file of data.
		catch (FileNotFoundException e) {
			System.out.println("The input from the file isn't valid!");
		}
	}
	
	//when the application stops.
	@Override
	public void stop() {
		//create a printWriter to write the new changed data in the file we read the data from.
		PrintWriter output;
		try {
			output = new PrintWriter(this.dataFile);
			output.write(this.myStore.toString());
			output.close();
		}
		//catch the error if the file isn't found.
		catch (FileNotFoundException e1) {
			System.out.println("This file can't be found!");
		}
	}
}

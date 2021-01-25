package javafxClasses;

import classes.*;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Login extends GridPane {
	private Manager mainManager;
	private ArrayList<Employee> myEmployee;
	private TextField username;
	private TextField password;
	
	
	public Login(Manager mainManager){
		this.mainManager=mainManager;
		this.myEmployee=this.mainManager.getMyEmployees();
		
		add(username,0,0);
		add(password,0,1);
	}
	
	
}

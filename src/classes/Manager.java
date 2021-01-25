package classes;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

public class Manager extends Person {
	private ArrayList<Employee> myEmployees;
	
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
			total+=(currentEmployees.get(i).getTotalProfit()-currentEmployees.get(i).getTip());
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
	
	
	

	@Override
	Scene basicWiew(Store myStore) {
		ScrollPane mainPane=new ScrollPane();
		
		StackPane products=new StackPane();
		
		Scene scene=new Scene(mainPane);
		
		return scene;
		
	}
	
}

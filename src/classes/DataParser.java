package classes;

import java.util.Scanner;

public class DataParser {
	
	public SoldProduct readSoldProduct(Scanner input) {
		//get info of soldProduct and return a new object with the data.
		String name=input.nextLine();
		double sellingPrice=input.nextDouble();
		int quantity=input.nextInt();
		
		return new SoldProduct(name,sellingPrice,quantity);
	}
	
	public Product readProduct(Scanner input) {
		//get info of Product and return a new object with the data.
		String name=input.nextLine();
		double sellingPrice=input.nextDouble();
		int quantity=input.nextInt();
		double buyingPrice=input.nextDouble();
		int refill=input.nextInt();
		
		return new Product(name,sellingPrice,quantity,buyingPrice,refill);
	}
	
	public Bill readBill(Scanner input) {
		//create a new Bill.
		Bill newBill=new Bill();
		
		//get the size of the products in the bill and skip to the next line.
		int size=input.nextInt();
		input.nextLine();

		//get soldProduct and add it to the bill.
		for(int i=0;i<size;i++) {
			newBill.add(this.readSoldProduct(input));
			input.nextLine();
		}
		
		//return bill.
		return newBill;
	}
	
	public Employee readEmployee(Scanner input) {
		//get a employee info.
		String name=input.nextLine();
		String password=input.nextLine();
		double baseWage=input.nextDouble();
		int percentageTip=input.nextInt();
		
		//create a employee with the data taken.
		Employee newEmployee=new Employee(name,password,baseWage,percentageTip);
		
		//get the size of bills the employee has.
		int size=input.nextInt();
		input.nextLine();

		//get info of a bill and add it to the employee.
		for(int i=0;i<size;i++) {
			newEmployee.addBill(this.readBill(input));
		}
		
		//return the employee.
		return newEmployee;
	}
	
	public Manager readManager(Scanner input) {
		//get info of a manager.
		String name=input.nextLine();
		String password=input.nextLine();
		
		//create a manager.
		Manager newManager=new Manager(name,password);
		
		//get the size of employees of these manager.
		int size=input.nextInt();
		input.nextLine();

		//get a employee and add it to the manager's employees.
		for(int i=0;i<size;i++) {
			newManager.addEmployee(this.readEmployee(input));
		}
		
		//return the manager.
		return newManager;
	}
	
	public Store readStore(Scanner input) {
		//create a store.
		Store newStore=new Store();
		
		//get its manager.
		newStore.setMainManager(this.readManager(input));
		
		//get the size of the products they have.
		int size=input.nextInt();
		if(size!=0) {
			input.nextLine();
		}

		//get the product and add it to the store.
		for(int i=0;i<size;i++) {
			newStore.addProduct(this.readProduct(input));
			if(i!=size-1) {
				input.nextLine();
			}
		}
		
		//return the store.
		return newStore;
	}
	
}

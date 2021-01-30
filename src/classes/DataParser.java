package classes;

import java.util.Scanner;

public class DataParser {
	
	public SoldProduct readSoldProduct(Scanner input) {
		String name=input.nextLine();
		System.out.println(name);
		double sellingPrice=input.nextDouble();
		int quantity=input.nextInt();
		return new SoldProduct(name,sellingPrice,quantity);
	}
	
	public Product readProduct(Scanner input) {
		String name=input.nextLine();
		double sellingPrice=input.nextDouble();
		int quantity=input.nextInt();
		double buyingPrice=input.nextDouble();
		int refill=input.nextInt();
		
		return new Product(name,sellingPrice,quantity,buyingPrice,refill);
	}
	
	public Bill readBill(Scanner input) {
		Bill newBill=new Bill();
		
		int size=input.nextInt();
		input.nextLine();

		
		for(int i=0;i<size;i++) {
			newBill.add(this.readSoldProduct(input));
			input.nextLine();
		}
		
		return newBill;
	}
	
	public Employee readEmployee(Scanner input) {
		String name=input.nextLine();
		String password=input.nextLine();
		double baseWage=input.nextDouble();
		int percentageTip=input.nextInt();
		
		Employee newEmployee=new Employee(name,password,baseWage,percentageTip);
		
		int size=input.nextInt();
		input.nextLine();

		for(int i=0;i<size;i++) {
			newEmployee.addBill(this.readBill(input));
		}
		
		return newEmployee;
	}
	
	public Manager readManager(Scanner input) {
		String name=input.nextLine();
		String password=input.nextLine();
		
		Manager newManager=new Manager(name,password);
		
		int size=input.nextInt();
		input.nextLine();

		
		for(int i=0;i<size;i++) {
			newManager.addEmployee(this.readEmployee(input));
		}
		return newManager;
	}
	
	public Store readStore(Scanner input) {
		Store newStore=new Store();
		newStore.setMainManager(this.readManager(input));
		
		int size=input.nextInt();
		if(size!=0) {
			input.nextLine();
		}

		
		for(int i=0;i<size;i++) {
			newStore.addProduct(this.readProduct(input));
			if(i!=size-1) {
				input.nextLine();
			}
		}
		
		return newStore;
	}
	
}

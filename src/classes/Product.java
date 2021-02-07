package classes;

import javafx.scene.control.Spinner;

public class Product extends SoldProduct{
	//private data fields.
	private double buyingPrice;
	private int refill;
	private Spinner<Integer> countQuantity;
	
	//constructor.
	public Product(String name,double sellingPrice,int quantity,double buyingPrice,int refill){
		super(name,sellingPrice,quantity);
		this.buyingPrice=buyingPrice;
		this.refill=refill;
	}
	
	//setters and getters.
	public Spinner<Integer> getCountQuantity() {
		return countQuantity;
	}

	public double getBuyingPrice() {
		return buyingPrice;
	}

	public int getRefill() {
		return refill;
	}
	
	//methods for financial information like marginal and total profit.
	double marginalProfit() {
		return this.buyingPrice-this.getSellingPrice();
	}
	
	double totalProfit() {
		return marginalProfit()*this.getQuantity();
	}

	//basic string manipulation to save data.
	public String toString() {
		return super.toString()+" "+this.buyingPrice+" "+this.refill+"\n";
	}
	
	//sets the maximum and minimum for our spinner objects as the quantity of the products we have and 0 for minimum.
	void refreshCountQuantity() {
		this.countQuantity=new Spinner<Integer>(0,this.getQuantity(),0);
	}
	
	//creates a soldProduct from this product with the same name and sellingPrice but a different quantity.
	public SoldProduct getProductForBill() {
		int boughtProducts=this.countQuantity.getValue();
		this.setQuantity(this.getQuantity()-boughtProducts);
		return new SoldProduct(this.getName(),this.getSellingPrice(),boughtProducts);
	}
}

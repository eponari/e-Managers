package classes;

import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Product extends SoldProduct{
	double buyingPrice;
	int refill;
	Spinner<Integer> countQuantity;
	
	public Product(String name,double sellingPrice,int quantity,double buyingPrice,int refill){
		super(name,sellingPrice,quantity);
		this.buyingPrice=buyingPrice;
		this.refill=refill;
	}
	
	public Spinner<Integer> getCountQuantity() {
		return countQuantity;
	}

	public void setCountQuantity(Spinner<Integer> countQuantity) {
		this.countQuantity = countQuantity;
	}

	public double getBuyingPrice() {
		return buyingPrice;
	}
	public void setBuyingPrice(double buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

	public int getRefill() {
		return refill;
	}
	public void setRefill(int refill) {
		this.refill = refill;
	}

	double marginalProfit() {
		return this.buyingPrice-this.sellingPrice;
	}
	
	double totalProfit() {
		return marginalProfit()*quantity;
	}

	public String toString() {
		return super.toString()+" "+this.buyingPrice+" "+this.refill+"\n";
	}
	
	void refreshCountQuantity() {
		System.out.println("Refreshed");
		this.countQuantity=new Spinner<Integer>(0,this.quantity,0);
	}
	
	public SoldProduct getProductForBill() {
		int boughtProducts=this.countQuantity.getValue();
		this.quantity-=boughtProducts;
		return new SoldProduct(this.name,this.sellingPrice,boughtProducts);
	}
}

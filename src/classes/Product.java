package classes;

import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Product extends SoldProduct{
	double buyingPrice;
	int pallets;
	int refill;
	Spinner<Integer> countQuantity= new Spinner<Integer>(0,this.quantity,0);
	
	public Product(String name,double sellingPrice,int quantity,double buyingPrice,int pallets,int refill){
		super(name,sellingPrice,quantity);
		
		this.buyingPrice=buyingPrice;
		this.pallets=pallets;
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

	public int getPallets() {
		return pallets;
	}
	public void setPallets(int pallets) {
		this.pallets = pallets;
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
		return super.toString()+" "+this.buyingPrice+" "+this.pallets+" "+this.refill+"\n";
	}
	
	public HBox pane() {
		HBox wiew= new HBox();
		
		wiew.setSpacing(10);
				
		wiew.getChildren().addAll(
				new Text(this.name),
				countQuantity,
				new Text(Double.toString(this.sellingPrice))
		);
		
		return wiew;
	}
	
	public SoldProduct getProductForBill() {
		int boughtProducts=this.countQuantity.getValue();
		this.quantity-=boughtProducts;
		this.countQuantity.getValueFactory().setValue(0);
		return new SoldProduct(this.name,this.sellingPrice,boughtProducts);
	}
}

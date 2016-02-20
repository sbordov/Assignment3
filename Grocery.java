package assignment3;

public class Grocery extends Item {
	//variables, constructor here
	public boolean isPerishable;
	
	public Grocery(String iName, float iCost, int iQuantity, int iWeight, String iPerish){
		super(iName, iCost, iQuantity, iWeight);
		if(iPerish.equals("P")){
			isPerishable = true;
		} else{
			isPerishable = false;
		}
	}
	
	// Shipping prices for groceries are 20% higher for perishable items.
	float shipping (){
		float shippingPrice;
		if(isPerishable){
			shippingPrice = (this.weight * 20) * ((float) 1.2); // Premium shipping if perishable.
		} else{
			shippingPrice = this.weight * 20; // Regular shipping if non-perishable.
		}
		return shippingPrice;
	}
	
	// Groceries aren't taxed.
	float tax (){
		float tax = 0; // No tax on groceries.
		return tax;
	}
	
	// Print out item attributes, which includes perishability status for groceries.
	void printItemAttributes () 
	{
		//Print all applicable attributes of this class
		String perishable = "";
		if(this.isPerishable){
			perishable = "P";
		} else{
			perishable = "NP";
		}
		System.out.println("Name: " + name + "\tPrice: " + price + "\tQuantity: " + quantity +
				"\tWeight: " + weight + "\tPerishability: " + perishable);
	}
	
	
}

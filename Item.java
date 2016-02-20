package assignment3;

public class Item 
{
//Declare variables for this class. Think about its type: public, protected or private?
	public String name;
	
	public float price;
	
	public int quantity;
	
	public int weight;
	

// Item constructor.
	public Item(String itemName, float itemCost, int itemQuantity, int itemWeight){
		name = itemName;
		price = itemCost;
		quantity = itemQuantity;
		weight = itemWeight;
	}
	
	
	// Should not need to overwrite calculatePrice. Instead, overwrite shipping() and tax()
	// for different item types.
	float calculatePrice () 
	{
		float final_price = 0;
		// Insert price calculation here
		float shippingPrice = this.shipping();
		float tax = this.tax();
		final_price = (price + tax + shippingPrice) * quantity;
		return final_price;
	}
	
	// Calculates shipping cost for a generic item.
	float shipping (){
		float shippingPrice = this.weight * 20;
		return shippingPrice;
	}
	
	// Calculates sales tax at a 10% rate. 
	float tax (){
		float tax = this.price / 10; // 10% tax rate.
		return tax;
	}
	
	// Print all attributes of a generic item.
	void printItemAttributes () 
	{
		//Print all applicable attributes of this class
		System.out.println("Name: " + name + "\tPrice: " + price + "\tQuantity: " + quantity +
				"\tWeight: " + weight);
	}

}

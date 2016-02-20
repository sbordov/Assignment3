package assignment3;

public class Electronics extends Item 
{
	// Variables, constructors etc. here.
	// Extra note
	public boolean isFragile;
	public String stateID;
	
	public Electronics(String iName, float iCost, int iQuantity, int iWeight, String iFragility,
			String iState){
		super(iName, iCost, iQuantity, iWeight);
		if(iFragility.equals("F")){ // Fragility status depends on a string value "F or NF"
			isFragile = true; // "F => fragile"
		} else{
			isFragile = false; // "NF => not fragile"
		}
		stateID = iState;
		
	}
	
	// Electronics shipping incurs premium costs if the item is fragile.
	float shipping (){
		float shippingPrice;
		if(this.isFragile){
			shippingPrice = (this.weight * 20) * ((float) 1.2); // Premium shipping if fragile.
		} else{
			shippingPrice = this.weight * 20; // Regular shipping if non-perishable.
		}
		return shippingPrice;
	}
	
	// Electronics aren't taxed if from Texas, New Mexico, Virginia, Arizona, or Arkansas.
	float tax (){
		float tax;
		if(this.salesTaxApplied()){ // Sales tax should not be applied to items from TX, NM, VA, AZ, and AK.
			tax = this.price / 10;
		} else{
			tax = 0;
		}
		return tax;
	}
	
	// Determine if sales tax should be applied to a given item depending on its state of origin.
	boolean salesTaxApplied(){
		if("TX NM VA AZ AK".indexOf(this.stateID) >= 0){ // These states don't have sales tax.
			return false;
		} else{
			return true;
		}
	}
	
	
	// Print out electronics attributes, which include regular item attr., fragility, and state of origin.
	void printItemAttributes () 
	{
		//Print all applicable attributes of this class
		String fragile = "";
		if(this.isFragile){
			fragile = "F";
		} else{
			fragile = "NF";
		}
		System.out.println("Name: " + name + "\tPrice: " + price + "\tQuantity: " + quantity +
				"\tWeight: " + weight + "\tFragility: " + fragile + "\tState of Origin: " + stateID);
	}
}

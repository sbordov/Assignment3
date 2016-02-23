package assignment3;

public class ValidInput{
	//Dan Added this because
	public String[] inputArgs;
	
	//Below is currently Stefan's Additions
	public boolean checkInsert (String[] input){
		String catCheck = categoryCheck(input[1]); 
		boolean result = false;
		switch (catCheck){
			case "clothing":
				result = checkClothingInput(input);
				break;
			case "groceries":
				result = checkGroceriesInput(input);
				break;
			case "electronics":
				result = checkElectronicsInput(input);
				break;
			default:
				result = false;
		}
		return result;
	}
	
	public boolean checkDelete (String[] input){
		if(input.length == 2){
			return true;
		} else{
			System.out.println("Inappropriate arguments for delete transaction.");
			return false;
		}
	}
	
	public boolean checkSearch (String[] input){
		if(input.length == 2){
			return true;
		} else{
			System.out.println("Inappropriate arguments for search transaction.");
			return false;
		}
	}
	
	public boolean checkUpdate (String[] input){
		if((input.length == 3) && isValidInt(input[2])){
			return true;
		} else{
			System.out.println("Innapropriate arguments for update transaction.");
			return false;
		}
			
	}
	
	public boolean checkPrint (String[] input){
		if(input.length == 1){
			return true;
		} else{
			System.out.println("Inappropriate number of arguments for print command.");
			return false;
		}
	}
	
	public boolean checkClothingInput (String[] input){
		String price = input[3]; String quantity = input[4]; String weight = input[5];
		
		if(input.length == 6){
			if(isValidPrice(price) && isValidInt(quantity) && isValidInt(weight)){
				return true;
			} else{
				System.out.println("Inappropriate arguments for insert of category type Clothing.");
				return false;
			}
		} else{
			System.out.println("Inappropriate number of arguments for category type Clothing.");
			return false;
		}
	}
	
	public boolean checkGroceriesInput (String[] input){
		String category = input[1]; String price = input[3]; String quantity = input[4]; 
		String weight = input[5]; String perishability = input[6];
		
		if(input.length == 7){
			if(isValidPrice(price) && isValidInt(quantity) && isValidInt(weight) &&
					isValidOp1(perishability, category)){
				return true;
			} else{
				System.out.println("Inappropriate arguments for insert of category type Groceries.");
				return false;
			}
		} else{
			System.out.println("Inappropriate number of arguments for category type Groceries.");
			return false;
		}
	}
	
	public boolean checkElectronicsInput (String[] input){
		String category = input[1]; String price = input[3]; String quantity = input[4]; 
		String weight = input[5]; String fragility = input[6]; String state = input[7];
		
		if(input.length == 8){
			if(isValidPrice(price) && isValidInt(quantity) && isValidInt(weight) &&
					isValidOp1(fragility, category) && isValidOp2(state, category)){
				return true;
			} else{
				System.out.println("Inappropriate arguments for insert of category type Electronics.");
				return false;
			}
		} else{
			System.out.println("Inappropriate number of arguments for category type Electronics.");
			return false;
		}
	}
	
	public  boolean isValidOp (String input){
		if(input.equalsIgnoreCase("insert") || input.equalsIgnoreCase("delete") ||
				input.equalsIgnoreCase("search") || input.equalsIgnoreCase("update") ||
				input.equalsIgnoreCase("print")){
			return true;
		} else{ 
			return false;
		}
	}
	
	public String operationCheck (String input){
		if(input.equalsIgnoreCase("insert")){ 
			return "insert";
		} else if(input.equalsIgnoreCase("delete")){
			return "delete";
		} else if(input.equalsIgnoreCase("search")){
			return "search";
		} else if(input.equalsIgnoreCase("update")){
			return "update";
		} else if(input.equalsIgnoreCase("print")){
			return "print";
		} else{
			System.out.println("Error in checking operation.");
			return "";
		}
	}
	
	public String categoryCheck (String input){
		if(input.equalsIgnoreCase("clothing")){
			return "clothing";
		} else if(input.equalsIgnoreCase("groceries")){
			return "groceries";
		} else if(input.equalsIgnoreCase("electronics")){
			return "electronics";
		} else{
			System.out.println("Erroneous category input.");
			return "";
		}
	}
	
	public  boolean isValidCat (String input){
		if(input.equalsIgnoreCase("clothing") || input.equalsIgnoreCase("electronics") ||
				input.equalsIgnoreCase("groceries")){
			return true;
		} else{
			return false;
		}
	}
	
	public  boolean isValidPrice (String input){
		int i = 0;
		String character = input.substring(i, i + 1);
		int punct = 0; // Count for number of occurrences of periods.
		int digsAfterDecimal = 0; // Count for number of digits occurring after decimal.
		while(("0123456789.".indexOf(character) >= 0) && (i < input.length())){
			character = input.substring(i, i + 1);
			if(character.equals(".")){
				if(punct == 0){
					punct = 1; // First occurrence of punctuation is fine.
				} else { return false; } // Multiple occurrences of punctuation is not okay.
			} else{
				if(punct == 1){
					digsAfterDecimal += 1; // Counts number of digits occurring after a decimal.
				}
			}
			i++;
		}
		if("0123456789.".indexOf(character) < 0){ // Signifies invalid input character.
			return false;
		}
		if(digsAfterDecimal <= 2){
			return true;	// If all characters are numerals, punct <= 1, and the number of
							// digits following a decimal is <= 2, the price is valid.
		} else{
			return false;
		}
	}
	
	public  boolean isValidWholeNum (String input){
		int i = 0;
		String character = input.substring(i, i + 1);
		int punct = 0; // Count for number of occurrences of periods.
		while(("0123456789.".indexOf(character) >= 0) && (i < input.length())){
			character = input.substring(i, i + 1);
			if(character.equals(".")){
				if(punct == 0){
					punct = 1; // First occurrence of punctuation is fine.
				} else { return false; } // Multiple occurrences of punctuation is not okay.
			}
			i++;
		}
		if("0123456789.".indexOf(character) < 0){ // Signifies invalid input character.
			return false;
		}
		if(i == input.length()){ // If i = input.length(), input is a proper whole number.
			return true;
		}
		return false; // If i != input.length(), there is at least one invalid character.
	}
	
	public  boolean isValidInt (String input){
		int i = 0;
		String character = input.substring(i, i + 1);
		// Check each character in a string to see if they are integers.
		while(("0123456789".indexOf(character) >= 0) && (i < input.length())){
			character = input.substring(i, i + 1);
			i++;
		}
		if(i == input.length()){ // If all characters are valid ints, return true.
			return true;
		} else{
			return false;
		}
	}
	
	public  boolean isValidOp1 (String input, String category){
		if(input.equalsIgnoreCase("groceries")){
			if(input.equalsIgnoreCase("P") || input.equalsIgnoreCase("NP")){
				return true;
			} else{
				return false;
			}
		} else if(input.equalsIgnoreCase("electronics")){
			if(input.equalsIgnoreCase("F") || input.equalsIgnoreCase("NF")){
				return true;
			} else{
				return false;
			}
		} else{
			return false;
		}
	}
	
	public  boolean isValidOp2 (String input, String category){
		if(input.equalsIgnoreCase("electronics")){
			if(isState(input)){
				return true;
			} else{
				return false;
			}
		} else{
			return false;
		}
	}
	
	public  String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL",
			"GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", 
			"MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR",
			"PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
	};
	
	public  boolean isState (String input){
		boolean matchFound = false;
		int i = 0;
		// Check if input is one of 51 possible US state abbreviations (includes Washington DC).
		while((matchFound = false) && (i < states.length)){
			if(states[i].equalsIgnoreCase(input)){
				matchFound = true;
			}
			i++;
		}
		return false;
	}
}

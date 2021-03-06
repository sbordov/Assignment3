package assignment3;

public class ValidInput{
	public String[] inputArgs;
	
	// Check to see if input matches the given category.
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
	
	// Check if a delete transaction has the proper number of input arguments.
	public boolean checkDelete (String[] input){
		if(input.length == 2){
			return true;
		} else{
			System.out.println("Inappropriate arguments for delete transaction.");
			return false;
		}
	}
	
	// Check if a search transaction has the proper number of input args.
	public boolean checkSearch (String[] input){
		if(input.length == 2){
			return true;
		} else{
			System.out.println("Inappropriate arguments for search transaction.");
			return false;
		}
	}
	
	// Check if an update transaction has proper number of input args.
	public boolean checkUpdate (String[] input){
		if((input.length == 3) && isValidInt(input[2])){
			return true;
		} else{
			System.out.println("Innapropriate arguments for update transaction.");
			return false;
		}
			
	}
	
	// Check if print transaction has proper number of input args.
	public boolean checkPrint (String[] input){
		if(input.length == 1){
			return true;
		} else{
			System.out.println("Inappropriate number of arguments for print command.");
			return false;
		}
	}
	
	// Ensure inputs from a clothing insert transaction will not create errors.
	public boolean checkClothingInput (String[] input){
		if(input.length != 6){
			System.out.println("Inappropriate number of arguments for category type Clothing.");
			return false;
		}
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
	
	// Ensure inputs from a groceries insert transaction will not create errors.
	public boolean checkGroceriesInput (String[] input){
		if(input.length != 7 ){
			System.out.println("Inappropriate number of arguments for category type Groceries.");
			return false;
		}
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
	
	// Ensure inputs from an electronics insert transaction will not create errors.
	public boolean checkElectronicsInput (String[] input){
		if(input.length != 8){
			System.out.println("Inappropriate number of arguments for category type Electronics.");
			return false;
		}
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
	
	// Check to see if input is a valid shoppingCart operation.
	public  boolean isValidOp (String input){
		if(input.equalsIgnoreCase("insert") || input.equalsIgnoreCase("delete") ||
				input.equalsIgnoreCase("search") || input.equalsIgnoreCase("update") ||
				input.equalsIgnoreCase("print")){
			return true;
		} else{ 
			return false;
		}
	}
	
	// Ensure case insensitivity for operations by returning a standardized operation string if
	// the transaction input is a case-insensitive match.
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
	
	// Ensure case insensitivity for categories by returning a standardized category String if
	// the transaction input is a case-insensitive match.
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
	
	// Validate category identification string.
	public  boolean isValidCat (String input){
		if(input.equalsIgnoreCase("clothing") || input.equalsIgnoreCase("electronics") ||
				input.equalsIgnoreCase("groceries")){
			return true;
		} else{
			return false;
		}
	}
	
	// Validate input price.
	public  boolean isValidPrice (String input){
		int i = 0;
		String character = input.substring(i, i + 1);
		int punct = 0; // Count for number of occurrences of periods.
		int digsAfterDecimal = 0; // Count for number of digits occurring after decimal.
		int numTrailingZeroes = 0;
		while(("0123456789.".indexOf(character) >= 0) && (i < input.length())){
			character = input.substring(i, i + 1);
			if(character.equals(".")){
				if(punct == 0){
					punct = 1; // First occurrence of punctuation is fine.
				} else { return false; } // Multiple occurrences of punctuation is not okay.
			} else{
				if(punct == 1){
					if(digsAfterDecimal <= 2 && character.equals("0")){
						while(character.equals("0") && (i < input.length() - 1)){
							numTrailingZeroes += 1;
							i++;
							character = input.substring(i, i + 1);
						}
						if(!character.equals("0")){
							digsAfterDecimal += numTrailingZeroes + 1;
						}

					} else{
						digsAfterDecimal += 1; // Counts number of digits occurring after a decimal.
					}
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
	
	/* Didn't use.
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
	*/
	
	// Check a String to see if it is an integer (integers with trailing decimal zeros are still ints).
	public  boolean isValidInt (String input){
		int i = 0;
		String character = input.substring(i, i + 1);
		// Check each character in a string to see if they are integers.
		while(("0123456789".indexOf(character) >= 0) && (i < input.length())){
			character = input.substring(i, i + 1);
			i++;
		}
		if(character.equals(".") && i >= input.length()){
			return false;
		} else if(character.equals(".") && (i < input.length())){
			character = input.substring(i, i + 1);
			while(character.equals("0") && (i < input.length() - 1)){
				i++;
				character = input.substring(i, i + 1);
			}
			if(!character.equals("0")){
				return false; // There exists an integer beyond a punctuation mark that doesn't = 0.
			} else{
				return true;
			}
		}
		if(i == input.length()){ // If all characters are valid ints, return true.
			return true;
		} else{
			return false;
		}
	}
	
	// Check optional input for groceries and electronics to see if it properly specifies fragility
	// or perishability.
	public  boolean isValidOp1 (String input, String category){
		if(category.equalsIgnoreCase("groceries")){
			if(input.equalsIgnoreCase("P") || input.equalsIgnoreCase("NP")){
				return true;
			} else{
				return false;
			}
		} else if(category.equalsIgnoreCase("electronics")){
			if(input.equalsIgnoreCase("F") || input.equalsIgnoreCase("NF")){
				return true;
			} else{
				return false;
			}
		} else{
			return false;
		}
	}
	
	// Check to see if additional input for electronics insert transaction is a valid state ID.
	public  boolean isValidOp2 (String input, String category){
		if(category.equalsIgnoreCase("electronics")){
			if(isState(input)){
				return true;
			} else{
				return false;
			}
		} else{
			return false;
		}
	}
	
	// List of all valid state abbreviations in the US.
	private  String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL",
			"GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", 
			"MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR",
			"PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
	};
	
	// Returns true if the input string matches a U.S. state abbreviation.
	public  boolean isState (String input){
		boolean matchFound = false;
		int i = 0;
		// Check if input is one of 51 possible US state abbreviations (includes Washington DC).
		while((matchFound == false) && (i < states.length)){
			if(states[i].equalsIgnoreCase(input)){
				matchFound = true;
			}
			i++;
		}
		return matchFound;
	}
}

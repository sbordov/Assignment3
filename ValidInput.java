package assignment3;

public class ValidInput{
	//Dan Added this because
	public String[] inputArgs;
	
	//Below is currently Stefan's Additions
	public  boolean isValidOp (String input){
		if(input.equalsIgnoreCase("insert") || input.equalsIgnoreCase("delete") ||
				input.equalsIgnoreCase("search") || input.equalsIgnoreCase("search") ||
				input.equalsIgnoreCase("update") || input.equalsIgnoreCase("print")){
			return true;
		} else{ 
			return false;
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

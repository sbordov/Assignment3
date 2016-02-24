package assignment3;
/*************************************************************************************************
IMPORTANT!^
IMPORTANT!^
IMPORTANT!^
IMPORTANT!^
IMPORTANT!^
IMPORTANT!^
IMPORTANT!^
IMPORTANT!^
IMPORTANT!^
Don't forget to change package to Assignment3 or points will be deducted, would change it now but it can wait 
*/










import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class A3Driver
	{

	  public static void main(String[] args) 
	  {
 
		//Open file; file name specified in args (command line)
		if (args.length != 1) 
		{
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}
		String[] transactions = processLinesInFile (args[0]);
		ArrayList<Item> shoppingCart = new ArrayList<Item>(); 
		processTransactions(transactions, shoppingCart);
		
		return;
	  }
	  
	  // I (Stefan) added everything below this line.((Edit)-Dan Coded the meat of the function reviewTransaction)
		/******************************************************************************
		* Method Name: processLinesInFile                                             *
		* Purpose: Opens the file specified in String filename, reads each line in it *
		*          and processes transactions according to input.					  *
		*         								                                      *
		* Returns: None                                                               *
		******************************************************************************/
		public static String[] processLinesInFile (String filename) 
		{ 	
			ArrayList<String> transactions = new ArrayList<String>();
			String[] goodTransactions = new String[]{};
		
			try 
			{
				FileReader freader = new FileReader(filename);
				BufferedReader reader = new BufferedReader(freader);
				
				
				int i = 0; // Transaction string index.
				for (String s = reader.readLine(); s != null; s = reader.readLine()) 
				{
					boolean isValid = reviewTransaction(s); // Checks line of transaction for validity.
					if(isValid){ // Adds a valid transaction to the transaction list to be performed.
						transactions.add(s); // Adds valid transaction line to return string.
						i++; // Increment transactions index.
					}
				}
				goodTransactions = new String[i];
				Iterator<String> it = transactions.iterator();
				int index = 0;
				while(it.hasNext()){
					String temp = it.next();
					goodTransactions[index] = temp;
					index += 1;
				}
				return goodTransactions;
			} 
			catch (FileNotFoundException e) 
			{
				System.err.println ("Error: File not found. Exiting...");
				e.printStackTrace();
				System.exit(-1);
			} catch (IOException e) 
			{
				System.err.println ("Error: IO exception. Exiting...");
				e.printStackTrace();
				System.exit(-1);
			}
			return goodTransactions;//Added this
		}
		
		/******************************************************************************
		* Method Name: reviewTransactions	                                          *
		* Purpose: Given a line of input, check if all text makes up a valid		  *
		*          transaction.														  *
		*         								                                      *
		* Returns: boolean indicating validity of input input transaction.            *
		******************************************************************************/
		public static boolean reviewTransaction (String transaction){
			 ValidInput parsedArgs = new ValidInput();
			 parsedArgs.inputArgs = transaction.split("\\s+"); // Parses transaction.
			 
			 //Check for valid operation
			 String operation = parsedArgs.inputArgs[0];
			 if(!parsedArgs.isValidOp(operation)){
				 System.out.println("Invalid operation.");
				 return false;
			 } else{ // Figure out which operation we're dealing with.
				 String opCheck = parsedArgs.operationCheck(operation);
				 switch (opCheck){
				 	case "insert": // insert
				 		return parsedArgs.checkInsert(parsedArgs.inputArgs);
				 	case "delete": // delete
				 		return parsedArgs.checkDelete(parsedArgs.inputArgs);
				 	case "search": // search
				 		return parsedArgs.checkSearch(parsedArgs.inputArgs);
				 	case "update": // update
				 		return parsedArgs.checkUpdate(parsedArgs.inputArgs);
				 	case "print": // print
				 		return parsedArgs.checkPrint(parsedArgs.inputArgs);
				 	default: System.out.println("Invalid operation");
				 		return false;
				 }
			 }
		}
		
		/******************************************************************************
		* Method Name: processTransactions	                                          *
		* Purpose: Given a String array of valid transactions, process transactions	  *
		*          and fill shoppingCart with items as necessary.					  *
		*         								                                      *
		* Returns: None                                                               *
		******************************************************************************/
		//Dan's Code is here 
		public static void processTransactions (String[] transaction, ArrayList<Item> shoppingCart){
			if(transaction.length == 0){
				System.out.println("No transactions to perform.");
				return;
			}
			ValidInput parsedArgs = new ValidInput();
			for(int i = 0; i < transaction.length; i++){
				 parsedArgs.inputArgs = transaction[i].split("\\s+"); // Parses transaction.
				 String operation = parsedArgs.inputArgs[0];
				 String opCheck = parsedArgs.operationCheck(operation);
				 switch (opCheck){
				 	case "insert": // insert
				 		insert(parsedArgs.inputArgs, shoppingCart);
				 		break;
				 	case "delete": // delete
				 		delete(parsedArgs.inputArgs, shoppingCart);
				 		break;
				 	case "search": // search
				 		search(parsedArgs.inputArgs, shoppingCart);
				 		break;
				 	case "update": // update
				 		update(parsedArgs.inputArgs, shoppingCart);
				 		break;
				 	case "print": // print
				 		print(parsedArgs.inputArgs, shoppingCart);
				 		break;
				 	default: System.out.println("Invalid operation");
				 }
			}
			return;
		}
		
		// Inserts items in alpha-numeric order.
		public static void insert (String[] transaction, ArrayList<Item> shoppingCart){
			String name = transaction[2];
			float price = Float.parseFloat(transaction[3]);
			int quantity = Integer.parseInt(transaction[4]);
			int weight = Integer.parseInt(transaction[5]);
			String op1; String op2;
			Item item = new Item(name, price, quantity, weight);
			ValidInput parsedArgs = new ValidInput();
			parsedArgs.inputArgs = transaction;
			String category = parsedArgs.categoryCheck(transaction[1]);
			switch (category){
				case "clothing": 
					item = new Clothing(name, price, quantity, weight);
					break;
				case "groceries":
					op1 = transaction[6];
					item = new Grocery(name, price, quantity, weight, op1);
					break;
				case "electronics":
					op1 = transaction[6];
					op2 = transaction[7];
					item = new Electronics(name, price, quantity, weight, op1, op2);
					break;
				default: System.out.println("Error in insert.");
			}
			// Find index of location to insert new item that maintains alphabetical order.
			int index = binarySearch(shoppingCart, item);
			shoppingCart.add(index, item); // Add item to ArrayList.
		}
		
		// Delete all entries of given name.
		public static void delete (String[] transaction, ArrayList<Item> shoppingCart){
			String name = transaction[1];
			int deletions = 0;
			Iterator<Item> i = shoppingCart.iterator();
			while(i.hasNext()){ // Use iterator to go through shoppingCart.
				Item temp = i.next(); // Check next item.
				 // If the temp item name matches name, remove fr/ shoppingCart
				if(temp.getName().equals(name)){
					i.remove();
					deletions += 1;
				}
			}
			if(deletions == 0){
				System.out.println("No items named " + name + " were found in the shopping cart.");
			} else{
				System.out.println("Deleted " + deletions + " occurence(s) of items named " + name
						+ " from the shopping cart.");
			}
				
		}
		
		// Find number of entries of given name. Outputs number of items of name to screen.
		public static void search (String[] transaction, ArrayList<Item> shoppingCart){
			String name = transaction[1];
			int matches = 0;
			Iterator<Item> i = shoppingCart.iterator();
			while(i.hasNext()){ // Use iterator to go through shoppingCart.
				Item temp = i.next(); // Check next item.
				 // If the temp item name matches name, remove fr/ shoppingCart
				if(temp.getName().equals(name)){
					matches += 1;
				}
			}
			if(matches == 0){
				System.out.println("No items named " + name + " were found in the shopping cart.");
			} else{
				System.out.println("Found " + matches + " occurences of items named " + name
						+ " in the shopping cart.");
			}
		}
		
		// Update the quantity of the first item of a given name appearing in shoppingCart.
		// Prints the name and new quantity value to screen.
		public static void update (String[] transaction, ArrayList<Item> shoppingCart){
			String name = transaction[1];
			int quantity = Integer.parseInt(transaction[2]);
			Iterator<Item> i = shoppingCart.iterator();
			boolean match = false;
			while(i.hasNext() && !match){
				Item temp = i.next();
				if(temp.getName().equals(name)){
					temp.quantity = quantity;
					match = true;
				}
			}
			if(match){
				System.out.println("Item " + name + "'s quantity updated to " + quantity);
			} else{
				System.out.println("No items named " + name + " were found in the shopping cart.");
			}
		}
		
		// Print all contents of shoppingCart in alpha-numeric order.
		public static void print (String[] transaction, ArrayList<Item> shoppingCart){
			Iterator<Item> i = shoppingCart.iterator();
			float price;
			float total = 0; // Total price of all items.
			while(i.hasNext()){
				Item temp = i.next();
				price = temp.calculatePrice(); 
				total += price;
				System.out.println("Name: " + temp.getName() + "\tQuantity: " + temp.getQuantity()
					+ "\tPrice (plus tax, S&H): $" + price);
			}
			System.out.println("Total charge for shopping cart: $" + total);
		}
		
		public static int binarySearch(ArrayList<Item> shoppingCart, Item item){
			int low = 0;
		    int high = shoppingCart.size() - 1;

		    while (low <= high) {
		        int mid = (low + high) / 2;
		        Item midItem = shoppingCart.get(mid);
		        int cmp = midItem.compareTo(item);

		        if (cmp < 0)
		            low = mid + 1;
		        else if (cmp > 0)
		            high = mid - 1;
		        else
		            return mid;
		    }
		    return low;
		}
	}
		


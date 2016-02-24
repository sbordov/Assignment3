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
import java.util.Comparator;



public class A3Driver
	{

	  public static void main(String[] args) 
	  {
 
		/*
		 * I (Stefan) added this stretch of code. The rest came in the default package.
		 * The current design plan is to use processLinesInFile to comb through the input
		 * data and remove lines with improper input arguments. The String array named
		 * transactions should then be run through a separate method that actually performs
		 * necessary commands. String[] transaction will be a single transaction line.
		 * Before executing each transaction line, a method should check to see
		 * if it can actually perform that operation (e.g. a transaction line with the
		 * operand "update" should only go through transaction if there is an object of
		 * the listed name already in the ArrayList). 
		 */
		//Open file; file name specified in args (command line)
		if (args.length != 1) 
		{
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}
		String[] transactions = processLinesInFile (args[0]);
		  
		//Parse input, take appropriate actions.
			
		//Stub for arraylist.
		ArrayList<Item> shoppingCart = new ArrayList<Item>(); 
		processTransactions(transactions, shoppingCart);
		
		// General code example for how to iterate an array list. You will have to modify this heavily, to suit your needs.
		Iterator<Item> i = shoppingCart.iterator();
		while (i.hasNext()) 
		{
			Item temp = i.next();
			temp.calculatePrice(); 
			temp.printItemAttributes();
			//This (above) works because of polymorphism: a determination is made at runtime, 
			//based on the inherited class type, as to which method is to be invoked. Eg: If it is an instance
			// of Grocery, it will invoke the calculatePrice () method defined in Grocery.
		}		
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
			String[] transactions={};
		
			try 
			{
				FileReader freader = new FileReader(filename);
				BufferedReader reader = new BufferedReader(freader);
				
				
				int i = 0; // Transaction string index.
				for (String s = reader.readLine(); s != null; s = reader.readLine()) 
				{
					boolean isValid = reviewTransaction(s); // Checks line of transaction for validity.
					if(isValid){ // Adds a valid transaction to the transaction list to be performed.
						transactions[i] = s; // Adds valid transaction line to return string.
						i++; // Increment transactions index.
					}
				}
				return transactions;
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
			return transactions;//Added this
		}
		
		/******************************************************************************
		* Method Name: reviewTransactions	                                          *
		* Purpose: Given a line of input, check if all text makes up a valid		  *
		*          transaction.														  *
		*         								                                      *
		* Returns: boolean indicating validity of input input transaction.            *
		******************************************************************************/
		//Dan's Code is here 
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
					item = new Grocery(name, price, quantity, weight, op2);
					break;
				default: System.out.println("Error in insert.");
			}
			// Find index of location to insert new item that maintains alphabetical order.
			int index = Collections.binarySearch(shoppingCart, item);
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
				if(temp.getName() == name){
					i.remove();
					deletions += 1;
				}
			}
			if(deletions == 0){
				System.out.println("No items named " + name + " were found in the shopping cart.");
			} else{
				System.out.println("Deleted " + deletions + " occurences of items named " + name
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
				if(temp.getName() == name){
					matches += 1;
				}
			}
			if(matches == 0){
				System.out.println("No items named " + name + " were found in the shopping cart.");
			} else{
				System.out.println("Found " + matches + " occurences of items named " + name
						+ " from the shopping cart.");
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
				if(temp.getName() == name){
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
			System.out.println("Total charge for shopping cart: $");
		}
	}
			  
			 /*
			 //firstly, our inputs should have a minimum of 6 inputs, and could have up to 8 
			 if(parsedArgs.inputArgs.length < 6||parsedArgs.inputArgs.length > 8)	
			 {
				 System.out.println("Invalid Input Length");
				 return false;
			 }		
			 //Added a try/catch just to ensure there are no reads that should not be implemented
			 try
			 {
				 
				 if(!parsedArgs.isValidOp(parsedArgs.inputArgs[0]))
				 {
					 System.out.println("Sorry, that is not a valid operation. Try again");
					 return false;
				 
				 }
				 
			 // Check for valid category.
				 else if(!parsedArgs.isValidCat(parsedArgs.inputArgs[1]))
				 { 
					 System.out.println("Sorry, that is not a valid category. Try again");
					 return false;
				 }
				// Assume all names are valid.
				// Check for valid price. Can price = 0?----------------------Assumption is yes for now. 
				 else if(!parsedArgs.isValidPrice(parsedArgs.inputArgs[3])){
					 System.out.println("Sorry, that is not a valid price input");
					 return false;
				 }
				 // Check for valid Quantity. Can quantity = 0? =========Again assumption will be yes for now.
				 else if(!parsedArgs.isValidInt(parsedArgs.inputArgs[4]))
				 {
					 System.out.println("Sorry, that is not a valid quantity, isValidInt Error. Try again");
					 return false;
				 } 
			
				 //Checking for valid weight
				 else if(!parsedArgs.isValidInt(parsedArgs.inputArgs[5])){
					 System.out.println("Sorry, that is not a valid weight. Try again");
					 return false;
				 }
				 //Checking for Op1 Validity, inputs to the checker function in ValidInput are
				  * (input,category), both of type String where input refers to the op1 field (inputArgs[6]) 
				  * and category refers to inputArgs[1]
				  //
				 else if(!parsedArgs.isValidOp1(parsedArgs.inputArgs[6], parsedArgs.inputArgs[1])){
					 

				 }else if(!parsedArgs.isValidOp1(parsedArgs.inputArgs[7], parsedArgs.inputArgs[1]))
			 }
			 catch (Exception e)
			 {
				 System.out.println("You have an invalidity in input somewhere causing an index to be read that is out of bounds");
				 return false;
				 
			 }
			 */
		


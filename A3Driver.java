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



public class ShoppingCartDriver 
	{

	  public static void main(String[] args) 
	  {
		// TODO Auto-generated method stub
		
		  
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
		/**
		 * Everything after this in the main method was given to us. You might need to change it.
		 */
		  
		  
		//Parse input, take appropriate actions.
			
		//Stub for arraylist.
		ArrayList<Item> shoppingCart = new ArrayList<Item>(); 
		
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
		* Returns: None                                                               *
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
				 		break;
				 	case "delete": // delete
				 		return parsedArgs.checkDelete(parsedArgs.inputArgs);
				 		break;
				 	case "search": // search
				 		return parsedArgs.checkSearch(parsedArgs.inputArgs);
				 		break;
				 	case "update": // update
				 		return parsedArgs.checkUpdate(parsedArgs.inputArgs);
				 		break;
				 	case "print": // print
				 		return parsedArgs.checkPrint(parsedArgs.inputArgs);
				 		break;
				 	default: System.out.println("Invalid operation");
				 }
			 }
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
		


package assignment3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import assignment3.ValidInput;

public class ShoppingCartDriver 
	{

	  public static void main(String[] args) 
	  {
		// TODO Auto-generated method stub
		
		  
		/**
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
	  
	  // I (Stefan) added everything below this line.
		/******************************************************************************
		* Method Name: processLinesInFile                                             *
		* Purpose: Opens the file specified in String filename, reads each line in it *
		*          and processes transactions according to input.					  *
		*         								                                      *
		* Returns: None                                                               *
		******************************************************************************/
		public static String[] processLinesInFile (String filename) 
		{ 
			try 
			{
				FileReader freader = new FileReader(filename);
				BufferedReader reader = new BufferedReader(freader);
				
				String[] transactions;
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
		}
		
		/******************************************************************************
		* Method Name: reviewTransactions	                                          *
		* Purpose: Given a line of input, check if all text makes up a valid		  *
		*          transaction.														  *
		*         								                                      *
		* Returns: None                                                               *
		******************************************************************************/
		public static boolean reviewTransaction (String transaction){
			 String inputArgs[] = transaction.split("\\s+"); // Parses transaction.
			 
			 // Check for valid operation.

			 // Check for valid category.
			 
			 // Assume all names are valid.
			 
			 // Check for valid price. Can price = 0?
			 
			 // Check for valid Quantity. Can quantity = 0?
			 
			 // Check for valid weight. Can weight = 0?
			 
			 // Check for valid optional input 1.
			 
			 // Check for valid optional input 2.
		}
		
}

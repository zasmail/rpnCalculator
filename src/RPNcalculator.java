import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class RPNcalculator {
	private static Queue <Double> arith = new LinkedList<Double>();	//queue of the numbers to be operated on. As soon as a calculation is done add answer to queue
	public static void main(String[] args) {
		executeRPN();
	}
	/* 
	 * This is the main method in the class. This will method asks the user for input until the user inputs a q.
	 * This method will handle I/O exceptions and catch IllegalArgumentExceptions. The Illegal argument exception will
	 * be handled whenever it is originally thrown in subsequent methods.
	 */
	public static void executeRPN(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	//read one line at a time from the console
		String current = new String ();	//holds the user input
		System.out.println("Welcome to RPNcalculator. To quit enter q.");
		while (!current.equals("q")){	//while user input is not "q" continue to run the calculator
			System.out.print(">");
			try {
				current=br.readLine();	//get user input
			} catch (Exception e) {
				e.printStackTrace();	//this will be used to find I/O exceptions
			}
			try{
				getInput(current);
			} catch (IllegalArgumentException e){
				//An error message will have already been printed, continue to find valid input
			}	
		}
	}
	/*
	 * This method takes a string from the user and adds it to the Queue if it is a double.
	 * If it is not a double it will call the isOperator method which will determine how to handle operators.
	 * If it not an operator or double, the method will throw an IllegalArgumentException. 
	 * This method also ensures that the user enters the right input at the proper time so that
	 * an operation is not called prematurely and that more than two numbers never end up in the queue.
	 */
	static void getInput(String current) throws IllegalArgumentException{        
        try{
            double d = Double.parseDouble(current);	//assume that the user entered a number, we'll verify this later
            if (arith.size()>1){	//if the queue has two numbers already, operator input is needed 
            	System.err.println("Operator Needed!");
            	throw (new IllegalArgumentException());	
            }
            arith.add(d);	//add number to the queue
            System.out.println(d);
        }catch(NumberFormatException e){
        	if (!isOperator(current)){	//check to see if the input is an operator or "q"
        		System.err.println("Invalid Format!");
            	throw (new IllegalArgumentException());
        	}
        }
	}
	/*
	 * This method checks to see if the input is an operator and that if it is an operator, it is entered
	 * at the appropriate time.
	 * For the sake of ease, q is also considered an operator, but will not perform any operations on the queue.
	 * "q" is an operator because it is considered a valid input, but not a number.
	 */
	static boolean isOperator(String operator){
		switch (operator){
			case "+":
			case "-":
			case "*":
			case "/":
				if (arith.size()>1){	//if there are 2 numbers in the queue, do the operation
					doOperation(operator); 	
					return true;
				}
				else {	//otherwise throw exception 
					System.err.println("Number expected!");
	            	throw (new IllegalArgumentException());
				}
			case "q": //the case that the user would like to quit
				return true;
		}
		return false;
	}
	
	/*
	 * If all the necessary conditions are met, run the operations and print the answer.
	 * The answer will be added back to the queue after it is calculated.
	 * THIS WILL NOT CHECK FOR DIVIDING BY ZERO AND WILL RETURN "INFINITY"
	 */
	private static void doOperation (String operator){
		Double answer=arith.poll();	//grabs the first number in the queue
		switch (operator){
		case "+": 	//addition
			answer+=arith.poll();
			break;
		case "-":	//subtraction
			answer-=arith.poll();
			break;
		case "*":	//multiplication	
			answer*=arith.poll();
			break;
		case "/":	//division
			answer/=arith.poll();
			break;
		}
//		This would check to see if the user tried to divide by zero and would clear the queue
//		if (Double.isInfinite(answer)){
//			System.out.println("Dividing by zero, clearing the queue."); 
//			arith.clear();
//			return;
//		}
		System.out.println(answer); //print the answer
		arith.add(answer);	//add it back into the queue
	}
}

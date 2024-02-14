package ExpressionEvaluator;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
/**
 * EvaluateExpression - A Class that takes an expression given by the user and with 
 * accordance to order of precedence, prints out the operation step-by-step. The program will also print 
 * out the correct solution of the expression. This program is for simple operations only, such as 
 * multiplication, division, addition, and subtraction, and expressions provided by the user must be in Infix 
 * form ((e.g. 5 + 2 / 10 + 3).
 * @author Dante Anzalone
 * @version 2023-09 (4.29.0)
 * @references 
 * PostFix Conversion : https://www.geeksforgeeks.org/convert-infix-expression-to-postfix-expression/#
 * Arithmetic expression evaluation using stack : https://www.youtube.com/watch?v=UKuIw8cKKsc&ab_channel=EZCSE
 */
public class EvaluateExpression 
{
	private String equation, unseperated;
	private Stack <Double> values;
	private ArrayList <String> postfix;
	private ArrayList <String> equationsWithAnswer;
	
	/**
	 * EvaluateExpression Constructor
	 */
	public EvaluateExpression ()
	{
		values = new Stack <Double> ();
		postfix = new ArrayList <String> ();
		equationsWithAnswer = new ArrayList <String> ();
	}
	
	/**
	 * run Method - Runs the Evaluate Expression Program
	 * @param None
	 * @return None
	 */
	public void run ()
	{
		Double result; 
		
		equation = userPrompt(); 		// Obtains the expression via prompting user for input
		postFixConversion(equation);	// Converts Infix Expression to PostFix --> Computer's way of reading expressions
		result = evaluation();			// Evaluates the expression
		
		System.out.printf("%n=============================================================================%n"
				+ "%nPostFix ArrayList: %s%n", postfix);
		System.out.printf("%nAnswer: %s%n"
				+ "%n=============================================================================%n", result);
		
		print();
	}
	
	/**
	 * userPrompt Method - Asks the user for an equation to solve
	 * @param None
	 * @return equation - A string given via user and formatted to include no white
	 * spaces
	 */
	public String userPrompt ()
	{
		String equation;
		boolean run;
		
		equation = "";
		run = true;
		Scanner scan;
		
		scan = new Scanner (System.in);
		System.out.printf("%nEvaluationExpression Program by Dante Anzalone%n%n=============================================================================%n");
		
		while (run == true)
		{
			System.out.printf("%nBefore entering your expression, make sure to follow the Program's Rules:%n"
					+ "%n1. Simple operations only (*, /, +, and -)"
					+ "%n2. Must be in Infix Expression form (e.g. 5 + 2 / 10 + 3)%n"
					+ "%n=============================================================================%n");
			
			System.out.printf("%nInput an equation to solve: %n");
			equation = scan.nextLine();
			
			unseperated = equation;
			
			if (isEquationValid(equation) == true)
			{
				equation = equation.replace(" ", ""); // Ensures there is no white space
				scan.close();
				run = false;
			}
			else
			{
				System.out.printf("%nREREAD THE RULES%n");
			}
		}
		
		scan.close();
		return equation;
	}
	
	/**
	 * isEquationValid Method - Ensures that the equation is valid and in Infix expression 
	 * Alters the postFixConversion method to check for spaces and invalidity 
	 * @param equation - Equation given by the user
	 * @return true if a valid Infix equation according to program specifications else false
	 */
	private boolean isEquationValid (String equation)
	{
		ArrayList <String> checkValid; // Normal Infix equation
		boolean run;
		String value;
		char character;
					
		checkValid = new ArrayList <String> ();

		for (int i = 0; i < equation.length(); i++) // Scanning the String from left to right
		{
			value = "";
			character = equation.charAt(i); // Referenced, more efficient
			run = true;

			if (character == ' ') 
			{
				// Does nothing 
			}
			else if (character != '*' && character != '/' && character != '+' && character != '-') // If the character is an operand... (not an operation)
			{
				while (run == true) // Checking it a single digit or not
				{
					if (character != '*' && character != '/' && character != '+' && character != '-' && character != ' ')
					{
						value += "" + character;
						i++; // Index moves up to check inside while loop to see if it is a digit > 9
						
						if (i < equation.length()) // Ensuring not out of bounds
						{
							character = equation.charAt(i); // Gets next value
						}
						else
						{
							run = false; // Out of bounds, end while loop
						}
					}
					else // It was an operation
					{
						i--; // Since it was an operation, move index back down to reenter the main if statement (else it will skip operation)
						run = false;
					}
				}
				// ...put it in the checkValid ArrayList
				checkValid.add(value);
			}
			else // If an operation was scanned
			{
				checkValid.add("" + character);
			}
		}
		
		// Now checking for validity 
		
		System.out.printf("%nInfix ArrayList: %s%n", checkValid);
		
		for (int i = 0; i < checkValid.size(); i++)
		{
			value = checkValid.get(i);
			character = value.charAt(0);
			
			if (i % 2 == 0) // even
			{
				if (!(Character.isDigit(character)))
				{
					System.out.printf("%nWAS NOT IN INFIX FORM --> EXPECTED VALUE AT INDEX = %s, BUT FOUND | %s |%n", i, checkValid.get(i));
					return false;
				}
			}
			else // odd
			{
				if (character != '*' && character != '/' && character != '+' && character != '-')
				{
					System.out.printf("%nWAS NOT IN INFIX FORM%nEXPECTED VALID OPERATOR AT INDEX = %s, BUT FOUND | %s |%n", i, checkValid.get(i));
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * postFixConversion Method - Converts Infix Expression to PostFix for easier evaluation 
	 * using a stack of Characters.
	 * @param equation
	 * @return none
	 * @referenced - https://www.geeksforgeeks.org/convert-infix-expression-to-postfix-expression/#
	 */
	public void postFixConversion (String equation)
	{	
		Stack <Character> operation;
		boolean run;
		String value;
		char character;
			
		operation = new Stack <Character> ();
		
		for (int i = 0; i < equation.length(); i++) // Scanning the String from left to right
		{
			value = "";
			character = equation.charAt(i); // Referenced, more efficient
			run = true;

			// If the character is an operand... (not an operation)
			if (character != '*' && character != '/' && character != '+' && character != '-')
			{
				while (run == true) // Checking it a single digit or not
				{
					if (character != '*' && character != '/' && character != '+' && character != '-')
					{
						value += "" + character;
						i++; // Index moves up to check inside while loop to see if it is a digit > 9
						
						if (i < equation.length()) // Ensuring not out of bounds
						{
							character = equation.charAt(i); // Gets next value
						}
						else
						{
							run = false; // Out of bounds, end while loop
						}
					}
					else // It was an operation
					{
						i--; // Since it was an operation, move index back down to reenter the main if statement (else it will skip operation)
						run = false;
					}
				}
				// ...put it in the PostFix expression
				postfix.add(value);
			}
			else // If an operation was scanned
			{
				if (operation.isEmpty()) // Case for if the stack is empty 
				{
					operation.push(character);
				}
				else
				{
					// Checks precedence - If the next operator has less precedence, then it will pop out operations from the stack
					// into the PostFix array until the operator has less precedence
					while (!operation.empty() && (precedence(equation.charAt(i)) < precedence(operation.peek()) || precedence(equation.charAt(i)) == precedence(operation.peek())))
					{
						postfix.add("" + operation.pop());
					}
					operation.push(character);
				}
			}
		}
		
		while (operation.empty() == false) // Gathers the rest of the elements from the stack
		{
			postfix.add("" + operation.pop());
		}
	}
	
	/**
	 * precedence Method - Responsible for returning the precedence of operators to complete 
	 * PostFix Conversion. Called via the postFixConversion method.
	 * @param character - A character representing an operator 
	 * @return integer - Representing order of precedence 
	 * @referenced - https://www.geeksforgeeks.org/convert-infix-expression-to-postfix-expression/#
	 */
	private int precedence (char character)
	{
		if (character == '*' || character == '/')
		{
			return 2;
		}
		else if (character == '-' || character == '+')
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
	
	/**
	 * evaluation Method - Will get the PostFix Conversion and evaluate the expression
	 * @return result - The evaluated expression
	 * @referenced - PseudoCode via https://www.youtube.com/watch?v=UKuIw8cKKsc&ab_channel=EZCSE
	 */
	public double evaluation ()
	{	
		double tempResult, tempValue, valueOne, valueTwo;
		
		tempValue = 0; valueOne = 0; valueTwo = 0; tempResult = 0;
		
		for (int i = 0; i < postfix.size(); i++)
		{
			// Not an operator but an operand...
			if (!(postfix.get(i).equals("*")) && !(postfix.get(i).equals("/")) && !(postfix.get(i).equals("+")) && !(postfix.get(i).equals("-")))
			{
				tempValue = Double.parseDouble(postfix.get(i)); // Parsing into a double
				values.push(tempValue); // Pushed onto the stack
			}
			else if ((postfix.get(i).equals("*")) || (postfix.get(i).equals("/")) || (postfix.get(i).equals("+")) || (postfix.get(i).equals("-")))
			{
				valueTwo = values.pop(); // Pop "second" value from stack
				valueOne = values.pop(); // Pop "first" value from stack
				tempResult = performOperation(valueOne, valueTwo, postfix.get(i)); 
				values.push(tempResult); // Pushes it back onto the stack
			}
		}
		return values.pop(); // The last value
	}
	
	/**
	 * performOperation Method - Will perform the operation as determined in the evaulation's method
	 * via incoming parameters. Will return the result back to the public method.
	 * @param valueOne - First value
	 * @param valueTwo - Second value
	 * @param operator - Operator Type
	 * @return result - The result of the operation
	 */
	private double performOperation (double valueOne, double valueTwo, String operator)
	{
		double result;
		result = 0;
				
		if (operator.equals("+"))
		{
			result = valueOne + valueTwo;
		}
		else if (operator.equals("-"))
		{
			result = valueOne - valueTwo;
		}
		else if (operator.equals("*"))
		{
			result = valueOne * valueTwo;
		}
		else if (operator.equals("/"))
		{
			result = valueOne / valueTwo;
		}
		else
		{
			System.out.printf("%nError in performing operation - No valid operator%n");
			System.exit(0);
		}
		
		equationsWithAnswer.add(valueOne + " " + operator + " " + valueTwo);
		equationsWithAnswer.add("  " + result);

		return result;
	}
	
	/**
	 * printInTreeForm Method - Prints the user equation and the step-by-step process 
	 * @param None
	 * @return None
	 */
	private void print ()
	{
		System.out.printf("Output of Expressions%n"
				+ "%n%s", unseperated);
		
		for (int i = 0; i < equationsWithAnswer.size(); i++)
		{
				System.out.printf("%n%s", equationsWithAnswer.get(i));
		}
		System.out.printf("%n=============================================================================%n");
	}
}

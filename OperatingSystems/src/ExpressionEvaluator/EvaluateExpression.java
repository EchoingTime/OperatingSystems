package ExpressionEvaluator;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
/**
 * EvaluateExpression - A Class that takes an expression given by the user and with 
 * accordance to order of precedence, prints out the operations formatted like a tree data 
 * structure but using stacks. The program will also print out the correct solution of the 
 * expression. This program is for simple operations only, such as multiplication, division, addition, 
 * and subtraction, expressions provided by the user must be in InFlix form ((e.g. 5 + 2 / 10 + 3), and
 * the expression must contain single-digit values (e.g. no greater than 9)
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
	private ArrayList <String> equationsWithAnswer; // For the Tree Formatting 
	
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
		postFixConversion(equation);	// Converts InFix Expression to PostFix --> Computer's way of reading expressions
		result = evaluation();			// Finally evaluates the expression
		
		System.out.printf("%n=============================================================================%n"
				+ "%nPostFix Expression: %s%n", postfix);
		System.out.printf("%nAnswer: %s%n"
				+ "%n=============================================================================%n", result);
		
		printInTreeForm();
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
	 * printInTreeForm Method - Prints the user equation and the step-by-step process in tree form 
	 * @param None
	 * @return None
	 */
	private void printInTreeForm ()
	{
		System.out.printf("Output of Expressions"
				+ "%n%s", unseperated);
		
		for (int i = 0; i < equationsWithAnswer.size(); i++)
		{
				System.out.printf("%n%s", equationsWithAnswer.get(i));
		}
		System.out.printf("%n=============================================================================%n");
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
		String value;
		char character;
		
		operation = new Stack <Character> ();
		
		for (int i = 0; i < equation.length(); i++) // Scanning the String from left to right
		{
			value = "";
			character = equation.charAt(i); // Referenced, more efficient
			
			// If the character is an operand... (not an operation)
			if (character != '*' && character != '/' && character != '+' && character != '-')
			{
				value += character;
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
	 * userPrompt Method - Asks the user for an equation to solve
	 * @param None
	 * @return equation - A string given via user and formatted to include no white
	 * spaces
	 */
	public String userPrompt ()
	{
		String equation;
		Scanner scan;
		
		scan = new Scanner (System.in);
		System.out.printf("%nEvaluationExpression Program by Dante Anzalone%n%n=============================================================================%n"
				+ "%nBefore entering your expression, make sure to follow the Program's Rules:%n"
				+ "%n1. Simple operations only (*, /, +, and -)"
				+ "%n2. Must be in Inflix Expression form (e.g. 5 + 2 / 10 + 3)"
				+ "%n3. Deals with single digit numbers (e.g. Don't do any value no greater than 9)%n"
				+ "%n=============================================================================%n");
		
		System.out.printf("%nInput an equation to solve: ");
		equation = scan.nextLine();
		unseperated = equation;
		equation = equation.replace(" ", ""); // Ensures there is no white space
		
		scan.close();
		return equation;
	}
}

package mcross.algorithm.infix;

import java.io.IOException;

/**
 * AbstractEvaluator provides public functions that aid 
 * in the evaluation of math expressions.
 * 
 * @author Matt Cross
 *
 */
public abstract class AbstractEvaluator {
	public final static char RS_SQRT  = 1;
	public final static char RS_ABS   = 2;
	public final static char RS_SIN   = 3;
	public final static char RS_COS   = 4;
	public final static char RS_TAN   = 5;
	public final static char RS_ASIN  = 6;
	public final static char RS_ACOS  = 7;
	public final static char RS_ATAN  = 8;
	public final static char RS_LOG   = 9;
	
	public final static String[] RS_WORDS = {"sqrt",
											 "abs",
											 "sin",
											 "cos",
											 "tan",
											 "asin",
											 "acos",
											 "atan",
											 "log"};
	
	public static String mLastVar = null;
	
	/**
	 * Determines if the math operator is binary or unary
	 * 
	 * @param operator predefined math operator
	 * @return true if operator is binary, otherwise false
	 */
	final public static boolean isBinaryOperator(char operator) {
		boolean isBinary = true;
		
		switch(operator) {
			case RS_ASIN: case RS_ACOS: case RS_ATAN:
			case RS_SQRT: case RS_ABS: case RS_LOG:
			case RS_SIN: case RS_COS: case RS_TAN:
			case '!':
				isBinary = false;
		}
		
		return isBinary;
	}
	
	/**
	 * Returns the precedence value (in integers) 
	 * of the given operator.
	 * 
	 * @param operator a predefined math operator
	 * @return the precedence value in integers (0 = low)
	 */
	final public static int getPrecedence(char operator) {
		int precedence = 0;
		
		switch(operator) {
			case RS_SQRT: case RS_ABS: case RS_LOG:
			case RS_SIN: case RS_COS: case RS_TAN:
			case '!': case '%': case '^':
				precedence = 5;
			break;
		
			case '/':
				precedence = 4;
			break;
			
			case '*':
				precedence = 3;
			break;
			
			case '-':
				precedence = 2;
			break;
			
			case '+':
				precedence = 1;
			break;
		}
		
		return precedence;
	}
	
	/**
	 * Parses a unary text and returns the result
	 * 
	 * @param operator a predefined math operator
	 * @param value left operand 
	 * @return the result of the operation
	 */
	final public static float parseUnaryExpression(char operator, float value)
	{
		float result = 0.f;
		
		switch(operator) {
			case RS_ASIN:
			{
				result = (float)Math.asin(value);
			}
			break;
			
			case RS_ACOS:
			{
				result = (float)Math.acos(value);
			}
			break;
			
			case RS_ATAN:
			{
				result = (float)Math.atan(value);
			}
			break;
		
			//Radical
			case RS_SQRT:
			{
				result = (float) Math.sqrt(value);
			}
			break;
			
			//Absolute Value
			case RS_ABS:
			{
				result = Math.abs(value);
			}
			break;
			
			//Sin
			case RS_SIN:
			{
				result = (float) Math.sin(value);
			}
			break;
			
			case RS_COS:
			{
				result = (float) Math.cos(value);
			}
			break;
			
			case RS_TAN:
			{
				result = (float) Math.tan(value);
			}
			break;
			
			case RS_LOG:
			{
				result = (float) Math.log(value);
			}
			break;
		
			//Factorial
			case '!':
			{
				result = value;
				
				while((value-=1.f) > 0.f) {
					result *= value;
				}
			}
			break;
		}
		
		return result;
	}
	
	/**
	 * Parse a binary math text and return
	 * the value of the result
	 * 
	 * @param operator a predefined math operator
	 * @param valueA left operand 
	 * @param valueB right operand 
	 * @return the result of the operation
	 */
	final public static float parseBinaryExpression(char operator, float valueA, float valueB) {
		float result = 0.f;
		
		switch(operator) {
			//Remainder
			case '%':
				result = valueA % valueB;
			break;
			
			//Exponentiate
			case '^':
			{
				result = valueA;
				
				for(int index=1; index < valueB; index++)
					result *= valueA;
			}
			break;
		
			//Divide
			case '/':
				result = valueA / valueB;
			break;
			
			//Multiply
			case '*':
				result = valueA * valueB;
				System.out.println("Result: " + result);
			break;
			
			//Subtract
			case '-':
				result = valueA - valueB;
			break;
			
			//Add
			case '+':
				result = valueA + valueB;
			break;
		}
		
		return result;
	}
	
	/**
	 * Parses a Java String into a integer value 
	 * then pushes it onto the stack. 
	 * <p>
	 * NOTE: This method does not return the 
	 * numeric value that is converted. Rather 
	 * it returns the index offset corresponding
	 * to the number of characters read + the 
	 * original index value;
	 * 
	 * @param textIndex current index value of text
	 * @param text A Java String containing the text to be parsed
	 * @return the text index offset
	 * @throws IOException
	 */
	public static int parseNumeric(int textIndex, String text, Expression expr) throws IOException 
	{
		char character = text.charAt(textIndex);
		
		String result = "";
		int length = text.length();
		
		while(Character.isDigit(character) || 
			  character == '.' || 
			  character == '-') 
		{
			//Append digit onto number
			result += character;
			
			//If not end of string continue
			if((++textIndex) < length)
				character = text.charAt(textIndex);
			else
				break;
		}
		
		//Push numeric onto stack
		expr.valueStack.push(Double.parseDouble(result));
		
		return textIndex;
	}
	
	public static int parseVariable(int textIndex, String text, Expression expr) throws Exception 
	{
		char character = text.charAt(textIndex);
		
		while(Character.isWhitespace(character)) {
			character = text.charAt(textIndex++);
		}
		
		String key = "";
		int length = text.length();
		
		while(Character.isLetter(character)) {
			//Append digit onto number
			key += character;
				
			//If not end of string continue
			if((++textIndex) < length)
				character = text.charAt(textIndex);
			else
				break;
		}
		
		char operator = '\0';
		
		if((operator = convertToOperator(key)) != '\0') {
			expr.operatorStack.push(operator);
		}
		else {
			double value = 0.d;
			
			//Retrieve variable
			if(expr.variableList.containsKey(key)) 
				value = expr.variableList.get(key);
			
			expr.valueStack.push(value);
		}

		return textIndex;
	}
	
	public static char convertToOperator(String key) {
		char operator = '\0';
		
		for(int index=0; index < RS_WORDS.length; index++) {
			if(RS_WORDS[index].compareTo(key) == 0) {
				operator = (char)(index+1);
				break;
			}
		}
		return operator;
	}
	
	
	/**
	 * Evaluates a mathematical text and 
	 * returns the result of the text
	 * 
	 * @param text the math text to be parsed
	 * @return the result of the text
	 * @throws Exception
	 */
	public abstract Expression evaluate(String text, Expression expr) throws Exception;
}

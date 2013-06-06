package mcross.algorithm.infix;

public class InfixEvaluator extends AbstractEvaluator {
	
	private void processOperator(Expression expr) throws Exception {
		 float valueA = 0;
		 float valueB = 0;
		 double result = 0;
		 
		 char operator = expr.operatorStack.pop();
		 
		 if(operator == '(' || operator == ')')
			 return;
		 
		 else if(isBinaryOperator(operator)) {
			 valueA = expr.valueStack.pop().floatValue();
			 valueB = expr.valueStack.pop().floatValue();
			 
			 result  = parseBinaryExpression(operator, valueB, valueA);
		 }
		 
		 else {
			 valueA = expr.valueStack.pop().floatValue();
			 
			 result = parseUnaryExpression(operator, valueA);
		 }
		 
		 expr.valueStack.push(result);
	}
	
	private void traverseTo(char destOperator, Expression expr) throws Exception {
		 char topOperator = expr.operatorStack.peek();
 		 
		 while(topOperator != destOperator) {
			 processOperator(expr);
			 
			 topOperator = expr.operatorStack.peek();
		 }
	}
	
	public double run(Expression expr) throws Exception {
		while(!expr.operatorStack.isEmpty()) {
			 processOperator(expr);
		}
		
		return expr.valueStack.peek().floatValue();
	}

	@Override
	public Expression evaluate(String text, Expression expr) throws Exception {
		if((text == null) || (text.length() == 0))
			throw new Exception("Could not evaluate text. Expression is empty");
		
		//Expression index and counter
		int index = 0;
		int count = text.length();
		
		char nextChar    = '\0';
		
		//While index is less than length of text
		while(index < count) {
			nextChar = text.charAt(index);
			
			if(Character.isLetter(nextChar)) {
				index = parseVariable(index, text, expr);
			}
			else if(Character.isDigit(nextChar) ||
					(nextChar == '.')) 
			{
				index = parseNumeric(index, text, expr);
			} 
			else if((nextChar == '-') && 
					((Character.isDigit(text.charAt(index+1))) ||
					(text.charAt(index+1) == '.'))) 
			{
				index = parseNumeric(index, text, expr);
			}
			else  {
				switch(nextChar) {
				 	case '^': case '%': case '!':
					 	expr.operatorStack.push(new Character(nextChar));
					break;
				 
				 	case '+': case '-': case '*': case '/':
				 	{
				 		while((!expr.operatorStack.isEmpty()) &&
				 			  (getPrecedence(nextChar) <= getPrecedence(expr.operatorStack.peek())))
				 		{
				 			processOperator(expr);
				 		}
				 		expr.operatorStack.push(new Character(nextChar));
				 	 }
				 	 break;
					 	 
				 	 case '(': case '[': case '{':
				 		 expr.operatorStack.push(new Character(nextChar));
				 	 break;
					 	 
				 	 case ')':
				 		 traverseTo('(', expr);
					 break;
					 
				 	 case ']':
				 		 traverseTo('[', expr);
				 	 break;
				 	 
				 	 case '}':
				 		 traverseTo('{', expr);
				 	 break;
					 	 
				 	 default:
					 	 break;
				 }
				 
				 index++;
			 }//end switch
		}//end if(Character.isDigit())
		 
		return expr;
	}
}


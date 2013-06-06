package mcross.algorithm.infix;

import java.util.HashMap;
import java.util.Stack;

public class Expression {
	public HashMap<String, Double> variableList;
	
	public Stack<Character> operatorStack;
	public Stack<Double>    valueStack;
	
	public Expression() {
		variableList  = new HashMap<String, Double>();
		
		operatorStack = new Stack<Character>();
		valueStack    = new Stack<Double>();
	}
}

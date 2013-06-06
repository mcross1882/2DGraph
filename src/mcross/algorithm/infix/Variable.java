package mcross.algorithm.infix;

import mcross.Graph.CString;

public class Variable {
	private static final int VARIABLE_NAME_SIZE = 32;
	
	public final CString ident;
	
	public double value;
	
	public Variable(String ident, double value) {
		this.ident = new CString(VARIABLE_NAME_SIZE);
		
		if(ident != null)
			this.ident.copyString(ident);
		
		this.value = value;
	}
}

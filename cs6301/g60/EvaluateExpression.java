package cs6301.g60;

import java.util.Stack;
import java.util.StringTokenizer;

public class EvaluateExpression {
	
	private static Num[] numArray;
	
	//stores last assigned or printed variable index to be used for printlist
	private int lastIndex = -1;
	
	EvaluateExpression(){
		numArray = new Num[26];
		for(int i = 0; i < numArray.length; i++){
			numArray[i] = null;
		}
	}
	
	/**
	 * Takes string as an input converts into tokens and gets postfix evaluated
	 * @param input string
	 * @return string
	 * @throws Exception throws exception in case of illegal postix
	 */
	public String parseExpression(String input) throws Exception{
		StringTokenizer tokens = new StringTokenizer(input);
		String output = evaluatePostfix(tokens);
		return output;
	}
	
	/**
	 * Takes the tokens of postfix and and evaluates. returns the value obtained from evaluation
	 * if no expression is given and only variable is given, then it prints the value of that variable 
	 * @param tokens
	 * @return string that is evaluated
	 * @throws Exception in case of wrong input
	 */
	private String evaluatePostfix(StringTokenizer tokens) throws Exception{
		Stack<Num> stack = new Stack<>();
		boolean lhs = true;
		int varIndex = -1;
		while(tokens.hasMoreTokens()){
			String s = tokens.nextToken();
			if(s.matches("\\d+")) {  
			    stack.push(new Num(s));
			} else if(s.matches("[a-z]")) {
				int index = s.charAt(0) - 'a';
				if (lhs){
					if(varIndex != -1){
						throw new Exception("multiple variables in lhs and its not supported");
					}
					varIndex = index;
				}else{
				    stack.push(numArray[index]);	
				}
			} else if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%") || s.equals("^") || s.equals("|")) {
			    stack.push(evaluate(s,stack));
			} else if(s.equals("=")) {
			    lhs = false;
			} else if(s.equals(";")) {
				break;
			} else {  // Error
			    throw new Exception("Unknown token: " + s);
			}
		}
		this.lastIndex = varIndex;
		Num out = null;
		if (lhs){
			out = numArray[varIndex];
		}else {
			if(stack.size() != 1){
				throw new Exception("Not a valid postfix expression");
			}
			numArray[varIndex] = stack.pop();
			out = numArray[varIndex];
		}
		if(stack.size() > 0){
			throw new Exception("Not a valid postfix expression");
		}
		if(out == null){
			throw new Exception("variable is not defined");
		}
		return out.toString();
	}
	
	/**
	 * For each operator performs required operation 
	 * @param op operator
	 * @param stack used to get the operands
	 * @return Num class
	 * @throws Exception if the operator is not supported
	 */
	private Num evaluate(String op, Stack<Num> stack) throws Exception{
		Num operand1 = null;
		Num operand2 = null;
		if(op.equals("|")){
			if(stack.size() < 1){
				throw new Exception("Wrong postfix expression ");
			}
			operand1 = stack.pop();
		}else{
			if(stack.size() < 2){
				throw new Exception("Wrong postfix expression ");
			}
			operand2 = stack.pop();
			operand1 = stack.pop();
		}
		switch(op){
		    case "+":
		    	return Num.add(operand1, operand2);
		    case "-":
		    	return Num.subtract(operand1, operand2);
		    case "*":
		    	return Num.product(operand1, operand2);
		    case "/":
		    	return Num.divide(operand1, operand2);
		    case "%":
		    	return Num.mod(operand1, operand2);
		    case "^":
		    	return Num.power(operand1, operand2);
		    case "|":
		    	return Num.squareRoot(operand1);
		    default:
		    	throw new Exception("Operator not supported ");
		}
	}
	
	/**
	 * When the whole calculation is done this function is used to print list
	 * of the variable last assigned or printed
	 */
	public void exitEvaluation(){
		numArray[lastIndex].printList(); 
	}

}

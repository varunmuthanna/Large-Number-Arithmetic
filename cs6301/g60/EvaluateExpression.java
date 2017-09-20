package cs6301.g60;

import java.util.Stack;
import java.util.StringTokenizer;

public class EvaluateExpression {
	
	private static Num[] numArray;
	
	private int lastIndex = -1;
	
	EvaluateExpression(){
		numArray = new Num[26];
		for(int i = 0; i < numArray.length; i++){
			numArray[i] = null;
		}
	}
		
	public String parseExpression(String input) throws Exception{
		StringTokenizer tokens = new StringTokenizer(input);
		String output = evaluatePostfix(tokens);
		return output;
	}
	
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
		if (lhs){
			return numArray[varIndex].toString();
		}else {
			if(numArray[varIndex] == null){
				this.lastIndex = varIndex;
			}
			numArray[varIndex] = stack.pop();
			String out = numArray[varIndex].toString();
			System.out.println(out);
			return out;
		}
	}
	
	private Num evaluate(String op, Stack<Num> stack) throws Exception{
		Num operand1 = null;
		Num operand2 = null;
		if(op == "|"){
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
	
	public void exitEvaluation(){
		numArray[lastIndex].printList(); 
	}

}

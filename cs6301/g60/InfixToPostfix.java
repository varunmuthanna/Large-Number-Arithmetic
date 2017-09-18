package cs6301.g60;

import java.util.*;

/**
 * Created by shivan on 9/4/17.
 */
public class InfixToPostfix {
    public String convertInfixToPostfix(String expression) throws Exception {
        Stack<String> stack = new Stack<>();
        Queue<String> queue = new LinkedList<>();

        String[] tokens = expression.split(" ");
        for(int i=0;i<tokens.length;i++){
            String curr = tokens[i];
            Tokenizer.Token token = Tokenizer.tokenize(tokens[i]);
            switch (token){
                case OP: {

                    while (!stack.isEmpty() && (getPrecendence(stack.peek()) >= getPrecendence(curr))) {
                        queue.add(stack.pop());
                    }
                    stack.add(curr);
                    break;

                }
                case OPEN:
                    stack.add(curr);
                    break;
                case CLOSE:
                    String c = stack.peek();
                    while (stack.peek() != "(") {
                        queue.add(stack.pop());
                    }
                    stack.pop();
                    break;
                default:
                    queue.add(curr);
                    break;
            }
        }
        while(!stack.isEmpty()){
            queue.add(stack.pop());
        }
        //System.out.println(queue);

        return getString(queue);
    }

    private String getString(Queue<String> queue){
        StringBuilder sb = new StringBuilder();
        for(String s : queue){
            sb.append(s+" ");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    private static int getPrecendence(String operator){
        switch (operator){
            case "+":
            case "-":
                return 1;

            case "*":
            case "/":
            case "%":
                return 2;
            case "^":
                return 3;
            case "|":
                return 4;

            default:
                return -1;
        }
    }

    /*public static void main(String[] st){
        String expression = "(2+3)!*(6)/(1^3)/7-1";
        new InfixToPostfix<String>().convertInfixToPostfix(expression);
    }*/
}

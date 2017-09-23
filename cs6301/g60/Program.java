package cs6301.g60;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shivan on 9/18/17.
 */
public class Program {
    static List<String> list = new ArrayList<>();
    static Map<Integer, Integer> mapLineNumberToListIndex = new HashMap<>();

    static public void processList() throws Exception {
        EvaluateExpression evaluateExpression = new EvaluateExpression();
        for(int i=0;i<list.size();i++){
            String line = list.get(i);
            String[] tokens = line.split(" ");

            Tokenizer.Token token = Tokenizer.tokenize(tokens[0]);
            //this means it's a L4 line
            if(token == Tokenizer.Token.NUM){
                // tokens[0] would contain the line number defined by the user
                // i is an index to the list
                mapLineNumberToListIndex.put(Integer.parseInt(tokens[0]), i-1);
                for(int j=1;j<tokens.length;j++){
                    token = Tokenizer.tokenize(tokens[j]);
                    if(token == Tokenizer.Token.OP) {
                        //call shunting yard and the exp back into the list
                        InfixToPostfix sy = new InfixToPostfix();
                        String[] part = line.split("= ");
                        list.set(i, part[0]+" = "+sy.convertInfixToPostfix(part[1]));
                    }

                }
            }
        }

        //Control structre for the program
        /**
         * Start by reading the list
         * If there is a number check if it's a goto statement
         *          or evaluate it
         *
         * If it's a goto statement then check for the condition and if true
         * get the arraylist index from the map.
         *
         * Stop when we read to the end of the list; So a for loop should work here.
         */
        
        //precondition: all the lines of the program are in postfix form with a line
        //              number preceding every line

        for(int i=0;i<list.size();i++){
            String line = list.get(i);
            
            Tokenizer.Token token = Tokenizer.tokenize(String.valueOf(line.charAt(0)));
            // if it's a l4 statement
            if(token == Tokenizer.Token.NUM){
                //presence of '?' indicates possible loop
                String[] arr = line.split(" ");
                //System.out.println(arr[2]);
                if(arr[2].equals("?")){
                    //evaluate the statement here arr[1]
                    String s = evaluateExpression.parseExpression(arr[1]);
                    if(!s.equals("0")){
                        i = mapLineNumberToListIndex.get(Integer.parseInt(arr[3]));
                    }else if(arr.length> 4 && arr[4].equals(":")){
                        i = mapLineNumberToListIndex.get(Integer.parseInt(arr[5]));
                    }
                }else{
                    //strip of the line number and send it to the evaluator
                	System.out.println(evaluateExpression.parseExpression(removeLineNumber(line)));
                }
            }else{
                //evaluate this line as level 3
                System.out.println(evaluateExpression.parseExpression(line));
            }
        }

        evaluateExpression.exitEvaluation();
    }

    private static String removeLineNumber(String line){
        String[] lineArr = line.split("=");
        StringBuilder sb = new StringBuilder();
        sb.append(lineArr[0].split(" ")[1]).append(" =").append(lineArr[1]);
        return sb.toString();
    }

}

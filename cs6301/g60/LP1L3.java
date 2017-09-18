// Starter code for Level 3 driver for lp1

// Change following line to your group number
package cs6301.g60;
import java.util.Scanner;

public class LP1L3 {

    public static void main(String[] args) throws Exception {
	    Scanner in = new Scanner(System.in);
	    LP1L3 x = new LP1L3();
	    EvaluateExpression ee = new EvaluateExpression();
	
//	    while(in.hasNext()) {
//	        String word = in.next();
//	        String[] expr = word.split(";");
//	        for(int i = 0; i < expr.length; i++){
//	        	ee.parseExpression(expr[i]);
//	        }
//	    }
	    while(in.hasNextLine()){
	    	String line = in.nextLine();
	    	String[] expr = line.split(";");
	    	if(expr.length == 0){
    			return;
    		}
	    	for(int i = 0; i < expr.length; i++){
	        	ee.parseExpression(expr[i]);
	        }
	    }
    }
}

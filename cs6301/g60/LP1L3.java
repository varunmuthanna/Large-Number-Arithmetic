// Starter code for Level 3 driver for lp1

// Change following line to your group number
package cs6301.g60;
import java.util.Scanner;

public class LP1L3 {

    public static void main(String[] args) {
	    Scanner in = new Scanner(System.in);
	    EvaluateExpression ee = new EvaluateExpression();

	    while(in.hasNextLine()){
	    	String line = in.nextLine();
	    	String[] expr = line.split(";");
	    	if(expr.length == 0){
	    		ee.exitEvaluation();
    			break;
    		}
	    	for(int i = 0; i < expr.length; i++){
	    		try{
	        	    String out = ee.parseExpression(expr[i]);
	        	    System.out.println(out);
	    		}catch(Exception e){
	    			System.out.println("error : " + e);
	    			in.close();
	    			return;
	    		}
	        }
	    }
        in.close();
    }
}

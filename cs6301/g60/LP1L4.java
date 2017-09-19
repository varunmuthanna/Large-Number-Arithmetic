// Starter code for Level 4 driver for lp1

// Change following line to your group number
package cs6301.g60;
import java.util.Scanner;

public class LP1L4 {

    public static void main(String[] args) throws Exception {
	    Scanner in;
        if (args.length > 0) {
            int base = Integer.parseInt(args[0]);
            //TODO: confirm if this is correct. Use above base for all numbers (except I/O, which is in base 10)
            Num.base = base;
        }

        in = new Scanner(System.in);

        while(in.hasNextLine()) {
            String word = in.nextLine();
            if(word.equals(";")) {
                Program.processList();
                break;
            }
            // -1 to take the final semi colon out of the string
            Program.list.add(word.substring(0, word.length() - 1) );
        }
    }
}

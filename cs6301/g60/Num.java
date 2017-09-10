
// Starter code for lp1.

// Change following line to your group number
// Changed type of base to long: 1:15 PM, 2017-09-08.
package cs6301.g60;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

public class Num  implements Comparable<Num> {

    static long defaultBase = 10;  // This can be changed to what you want it to be.
    long base = 16;  // Change as needed

    List<Long> list;

    int MaxChunkSize = 0;

    /* Start of Level 1 */
    Num(String s) {
        // change the list type here
        list = new LinkedList<>();
        MaxChunkSize = determineMaxChunkSize();

        for (int i = s.length() ; i >= 0; i = i - MaxChunkSize) {
            //this gets the chunk based on max chunk size.
            Long chunkNumber = Long.parseLong(s.substring((i - MaxChunkSize) >= 0 ? i - MaxChunkSize : 0, i));
            System.out.println(chunkNumber+": "+convertFromDecimalToBase(chunkNumber, base));
            list.addAll(convertFromDecimalToBase(chunkNumber, base));
        }
        System.out.println(list);
    }

    Num(long x) {
        list = new LinkedList<>();
        long quotient = 0, remainder=0;

        while(true){
            quotient = x / base;
            remainder = x % base;

            list.add(remainder);
            if(quotient<base){
                list.add(quotient);
                break;
            }
            x = quotient;
        }
    }

    static Num add(Num a, Num b) {
        return null;
    }

    static Num subtract(Num a, Num b) {
        return null;
    }

    // Implement Karatsuba algorithm for excellence credit
    static Num product(Num a, Num b) {
        return null;
    }

    // Use divide and conquer
    static Num power(Num a, long n) {
        return null;
    }
    /* End of Level 1 */

    /* Start of Level 2 */
    static Num divide(Num a, Num b) {
        return null;
    }

    static Num mod(Num a, Num b) {
        return null;
    }

    // Use divide and conquer
    static Num power(Num a, Num n) {
        return null;
    }

    static Num squareRoot(Num a) {
        return null;
    }
    /* End of Level 2 */


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
        return 0;
    }

    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    void printList() {
        ArrayDeque<Long> stack = new ArrayDeque<>();
        for(Long number :list){
            stack.addFirst(number);
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            sb.append(stack.pop()+" ");
        }
        System.out.println(base+": "+sb.toString());
    }

    // Return number to a string in base 10
    public String toString() {
        return null;
    }

    public long base() { return base; }


    /**
     * All helper methods
     */
    private int getUnprocessedStringLength(String str, int chunksProcessed){
        return str.length() - chunksProcessed * (MaxChunkSize);
    }
    private int determineMaxChunkSize(){
        return 2;
    }

    public static List<Long> convertFromDecimalToBase(Long number, long base) {
        ArrayDeque<Long> stack = new ArrayDeque<>();
        List<Long> list = new LinkedList<>();

        if(number<base) {
            list.add(number);
            return list;
        }
        //sets up the quotient to the original number and remainder to 0
        Long quotient = number;
        Long remainder = 0L;

        /**
         * while quotient is greater than base
         * there is a scope to convert to a given base
         *
         * On exit: the quotient would the final remainder
         *          which is < base. So we add that explicitly
         *          onto the stack
         */
        while(quotient>=base){
            quotient = number/base;
            remainder = number%base;
            stack.addFirst(remainder);

            number = quotient;
        }
        stack.addFirst(quotient);

        while(!stack.isEmpty()){
            list.add(stack.pop());
        }

        return list;
    }
}



// Starter code for lp1.

// Change following line to your group number
// Changed type of base to long: 1:15 PM, 2017-09-08.
package cs6301.g60;

import java.util.List;

public class Num  implements Comparable<Num> {

    static long defaultBase = 10;  // This can be changed to what you want it to be.
    long base = defaultBase;  // Change as needed

    List<Long> list;


    /* Start of Level 1 */
    Num(String s) {
        
    }

    Num(long x) {
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
    }

    // Return number to a string in base 10
    public String toString() {
        return null;
    }

    public long base() { return base; }
}

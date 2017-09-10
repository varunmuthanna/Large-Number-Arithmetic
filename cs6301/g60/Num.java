
// Starter code for lp1.

// Change following line to your group number
// Changed type of base to long: 1:15 PM, 2017-09-08.
package cs6301.g60;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Num  implements Comparable<Num> {

    static long defaultBase = 10;  // This can be changed to what you want it to be.
    static long base = 16;  // Change as needed
    private boolean negative = false;

    private List<Long> list;

	int MaxChunkSize = 0;
	
	Num(){
		list = new LinkedList<>();
	}

    /* Start of Level 1 */
    Num(String s) {
        // change the list type here
        list = new LinkedList<>();
        MaxChunkSize = determineMaxChunkSize();

        for (int i = s.length() ; i > 0; i = i - MaxChunkSize) {
            //this gets the chunk based on max chunk size.
            Long chunkNumber = Long.parseLong(s.substring((i - MaxChunkSize) >= 0 ? i - MaxChunkSize : 0, i));
            //System.out.println(chunkNumber+": "+convertFromDecimalToBase(chunkNumber, base));
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
    	Iterator<Long> aIter = a.getListIterator();
    	Iterator<Long> bIter = b.getListIterator();
    	Num out = new Num();
    	List<Long> outList = out.getList();
    	outList = add(aIter, bIter, outList);
        return out;
    }

    static Num subtract(Num a, Num b) {
    	Iterator<Long> aIter = a.getListIterator();
    	Iterator<Long> bIter = b.getListIterator();
    	Num out = new Num();
    	List<Long> outList = out.getList();
    	int gt = findGreaterList(a.getList(), b.getList());
    	if(gt == 1){
    	    outList = subtract(aIter, bIter, outList);
    	}else if(gt == 2){
    		outList = subtract(bIter, aIter, outList);
    		out.negative = true;
    	}else{
    		outList.add(0L);
    	}
        return out;
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
    
    public Iterator<Long> getListIterator() {
		return list.iterator();
	}
    
    public List<Long> getList() {
		return list;
	}
    /* End of Level 2 */


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
    	if(this.negative && !other.negative){
    		return -1;
    	}else if(!this.negative && other.negative){
    		return +1;
    	}else{
    		int gt = findGreaterList(this.list, other.list);
    		if(gt == 1){
    			return +1;
    		}else if(gt == 2){
    			return -1;
    		}
    	}
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
        long base = base();
        List<Long> list = this.list;
        int chunk = MaxChunkSize;

        ArrayDeque<Long> stack = new ArrayDeque<>();

        //itr: keeps track on when to add the result to the stack
        //     if it is equal to the chunk size or the list size
        int itr = 0;
        long result = 0;
        long carry = 0;

        //normal counter on the list
        // if equals to the list size-time to add the result onto the stack
        long i = 1l;

        for(Long number : list){

            result = (long) (result + number * Math.pow(base, itr));
            itr++;

            if(itr==chunk || i==list.size()){
                //the result contains the final answer in decimal
                result = result + carry;
                carry = (int) result / (long)Math.pow(10, MaxChunkSize);
                result = result % (long) (Math.pow(10, MaxChunkSize));

                stack.addFirst(result);

                //after processing one chunk
                result = 0;
                itr = 0;
            }
            i++;
        }

        StringBuilder output = new StringBuilder();
        while(!stack.isEmpty()){
            output.append(stack.pop()+" ");
        }

        return output.toString();
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

    public List<Long> convertFromDecimalToBase(Long number, long base) {

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
            list.add(remainder);

            number = quotient;
        }
        list.add(quotient);

        return list;
    }
    
    private static Long next(Iterator<Long> it){
    	return it.hasNext()? it.next() : 0;
    }
    
    private static List<Long> add(Iterator<Long> aIter, Iterator<Long> bIter, List<Long> outList){
    	Long carry = 0L;
    	while(aIter.hasNext() || bIter.hasNext() || carry > 0){
    		Long sum = next(aIter) + next(bIter) + carry;
    		List<Long> sumList = convertFromDecimalToBase(sum, Num.base);
    		outList.add(sumList.get(0));
    		carry = sumList.size() > 1 ? sumList.get(1) : 0;
    	}
    	System.out.println("sum" + outList);
    	return outList;
    }
    
    private static List<Long> subtract(Iterator<Long> aIter, Iterator<Long> bIter, List<Long> outList){
    	Long carry = 0L;
    	while(aIter.hasNext()){
    		Long a = next(aIter) - carry;
    		Long b = next(bIter);
    		if(a < b){
    			a += Num.base;
    			carry = 1L;
    		}else{
    			carry = 0L;
    		}
    		outList.add(a - b);
    	}
    	System.out.println("difference" + outList);
    	return outList;
    }
    
    private static int findGreaterList(List<Long> first, List<Long> second){
    	int flag = 0;
    	if(first.size() > second.size()){
    		return 1;
    	}else if(first.size() < second.size()){
    		return 2;
    	}else{
    		Iterator<Long> it1 = first.iterator();
    		Iterator<Long> it2 = second.iterator();
    		while(it1.hasNext() && it2.hasNext()){
    			Long firstVal = next(it1);
    			Long secondVal = next(it2);
    			if(firstVal > secondVal){
    				flag = 1;
    			}else if(firstVal < secondVal){
    				flag = 2;
    			}
    		}
    		return flag;
    	}
    }
}


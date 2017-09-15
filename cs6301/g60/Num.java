
// Starter code for lp1.

// Change following line to your group number
// Changed type of base to long: 1:15 PM, 2017-09-08.
package cs6301.g60;


import java.util.*;

public class Num  implements Comparable<Num> {

    static long defaultBase = 10;  // This can be changed to what you want it to be.
    static long base = 3;  // Change as needed
    private boolean negative = false;

    private List<Long> list;

    private int MaxChunkSize = 2;

    Num() {
        list = new LinkedList<>();
    }

    Num(Num copy) {
        this.list = copy.list;
        this.defaultBase = copy.defaultBase;
        this.negative = copy.negative;
        this.MaxChunkSize = copy.MaxChunkSize;
        this.base = copy.base;

    }

    /* Start of Level 1 */

    Num(String s) {
        // change the list type here
        if (s.length() == 0) {
            throw new NullPointerException("Invalid number");
        }
        list = new LinkedList<>();

        Num base10 = new Num(10L);
        char[] arr = new StringBuilder(s).toString().toCharArray();
        Num num = this;
        for (char current : arr) {
            Num first = Num.product(num, base10);
            Num second = new Num(current - '0');
            num = Num.add(first, second);
        }
        System.out.println("_____________________________________"+num.list);
        // copies the num back to object "this"
        this.list = num.list;
        this.defaultBase = num.defaultBase;
        this.negative = num.negative;
        this.MaxChunkSize = num.MaxChunkSize;
        this.base = num.base;
    }

    Num(long x) {
        list = new LinkedList<>();
        long quotient = base + 1, remainder = 0;

        if (x < base) {
            list.add(x);
        } else {
            while (x >= base) {
                quotient = x / base;
                remainder = x % base;
                list.add(remainder);

                x = quotient;
            }
            if (quotient != 0) {
                list.add(quotient);
            }
        }
        System.out.println(list.size() + "  " + list);
    }

    static Num add(Num a, Num b) {
    	Num out = new Num();
    	List<Long> outList = out.getList();
    	add(a.getList(), b.getList(), outList,base);
    	System.out.println("sum" + outList);
        return out;
    }

    static Num subtract(Num a, Num b) {
        Num out = new Num();
        List<Long> outList = out.getList();
        int gt = findGreaterList(a.getList(), b.getList());
        if (gt == 1) {
            subtract(a.getList(), b.getList(), outList);
        } else if (gt == 2) {
            subtract(b.getList(), a.getList(), outList);
            out.negative = true;
        } else {
            outList.add(0L);
        }
        System.out.println("difference" + outList);
        return out;
    }

    // Implement Karatsuba algorithm for excellence credit
    static Num product(Num a, Num b) {
    	Num out = new Num();
    	List<Long> outList = out.getList();
    	product(a.getList(), b.getList(), outList, Num.base);
    	System.out.println("product" + outList);
        return out;
    }

    // Use divide and conquer
    static Num power(Num a, long n) {
        Num out = new Num();
        getPower(a.getList(), n, out.getList());
        System.out.println("power" + out.list);
        return out;
    }
    /* End of Level 1 */

    /* Start of Level 2 */
    //a:dividend b : divisor
    static Num divide(Num a, Num b) {
        //if a<b : chechking the list size
        if (getMagnitude(a).compareTo(getMagnitude(b)) < 0) {
            return new Num(0L);
        }

        Num originalNumerator = a, originalDenominator = b;
        Num quotient = null;

        while (getMagnitude(a).compareTo(originalDenominator) >= 0) {
            Long num = getLastElement(b.getList());

            List<Long> list = getSubListForNumerator(a.getList(), b.getList());
            if (quotient == null) {
                quotient = Num.singleDigitDivision(list, num);
            } else {
                if (a.negative)
                    quotient = Num.subtract(quotient, Num.singleDigitDivision(list, num));
                else
                    quotient = Num.add(quotient, Num.singleDigitDivision(list, num));
            }

            Num intermediateResult = Num.product(quotient, originalDenominator);
            removeLeadingZerosFromList(intermediateResult);
            Num remainder = Num.subtract(originalNumerator, intermediateResult);
            a = removeLeadingZerosFromList(remainder);
        }
        if (a.negative) {
            quotient = Num.subtract(quotient, new Num(1L));
            a = Num.subtract(a, originalDenominator);
            a.negative = false;
        }

        System.out.println("Quotient: -----------------  " + quotient.getList());
        return quotient;
    }

    static Num mod(Num a, Num b) {

        if (getMagnitude(a).compareTo(getMagnitude(b)) == 0) {
            return a;
        }

        Num originalNumerator = new Num(a), originalDenominator = new Num(b);
        Num quotient = null;
        while (getMagnitude(a).compareTo(originalDenominator) >= 0) {
            Long num = getLastElement(b.getList());

            List<Long> list = getSubListForNumerator(a.getList(), b.getList());
            if (quotient == null) {
                quotient = Num.singleDigitDivision(list, num);
            } else {
                if (a.negative)
                    quotient = Num.subtract(quotient, Num.singleDigitDivision(list, num));
                else
                    quotient = Num.add(quotient, Num.singleDigitDivision(list, num));
            }

            Num intermediateResult = Num.product(quotient, originalDenominator);
            removeLeadingZerosFromList(intermediateResult);
            Num remainder = Num.subtract(originalNumerator, intermediateResult);
            a = removeLeadingZerosFromList(remainder);
        }


        //if(a.getList().size()>0 &&a.compareTo(new Num(0L))<0 ){
        if (a.negative) {
            quotient = Num.subtract(quotient, new Num(1L));
            a = Num.subtract(a, originalDenominator);
            a.negative = false;
        }

        System.out.println("Remainder:--------------------  " + a.getList());
        return a;
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
        if (this.negative && !other.negative) {
            return -1;
        } else if (!this.negative && other.negative) {
            return +1;
        } else {
            int gt = findGreaterList(this.list, other.list);
            if (gt == 1) {
                return +1;
            } else if (gt == 2) {
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
        for (Long number : list) {
            stack.addFirst(number);
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop() + " ");
        }
        System.out.println(base + ":  " + list.size() + "  " + sb.toString());
    }

    // Return number to a string in base 10
    public String toString() {
        Num base = new Num();
        base.list = convertFromDecimalToBase(base(),10);

        StringBuilder result = new StringBuilder();
        List<Long> list = this.getList();


        // if the list is of size 0
        if (list.size() == 0) {
            return new String("0");
        }
        Num resultNum = new Num();
        ListIterator<Long> it = list.listIterator(list.size());

        while (it.hasPrevious()) {
            resultNum = Num.add(Num.product(resultNum, base), new Num(it.previous()) );
        }

        System.out.println("result: " + resultNum.getList());
        return result.toString();
    }

    public static long base() {
        return base;
    }


    /**
     * All helper methods
     */

    private int determineMaxChunkSize() {
        return MaxChunkSize;
    }

    public static List<Long> convertFromDecimalToBase(Long number, long base) {

        List<Long> list = new LinkedList<>();

        if (number < base) {
            list.add(number);
            if (number == 0L) {
                list.add(0L);
            }
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
        while (quotient >= base) {
            quotient = number / base;
            remainder = number % base;
            list.add(remainder);

            number = quotient;
        }
        list.add(quotient);

        return list;
    }

    private static Long next(Iterator<Long> it){
    	return it.hasNext()? it.next() : 0L;
    }
    private static Long nextForNull(Iterator<Long> it){
        return it.hasNext()? it.next() : null;
    }
    
    private static void add(List<Long> a, List<Long> b, List<Long> outList, long base){
    	Iterator<Long> aIter = a.iterator();
    	Iterator<Long> bIter = b.iterator();
    	Long carry = 0L;
    	while(aIter.hasNext() || bIter.hasNext() || carry > 0){
    		Long sum = next(aIter) + next(bIter) + carry;
    		List<Long> sumList = convertFromDecimalToBase(sum, base);
    		outList.add(sumList.get(0));
    		carry = sumList.size() > 1 ? sumList.get(1) : 0L;
    	}
    }
    
    private static void subtract(List<Long> aList, List<Long> bList, List<Long> outList){
    	Iterator<Long> aIter = aList.iterator();
    	Iterator<Long> bIter = bList.iterator();
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
    }
    
    private static List<Long> product(List<Long> a, List<Long> b, List<Long> out, long base){
    	int gt = findGreaterList(a,b);
    	if(gt == 2){
    		out = multiply(b, a, out, base);
    	}else{
    		out = multiply(a, b, out, base);
    	}
    	return out;
    }
    
    private static List<Long> getPower(List<Long> a, long n, List<Long> out){
    	if(n == 1){
    		out.addAll(a);
    		return out;
    	}else if(n == 2){
    		multiply(a,a,out, Num.base);
    		return out;
    	}
    	
    	if(n % 2 == 0){
    		getPower(getPower(a,n/2,new LinkedList<Long>()), 2L ,out);
    	}else{
    		multiply(getPower(a,n/2,new LinkedList<Long>()), a, out, Num.base);
    	}
    	return out;
    }
    
    private static List<Long> multiply(List<Long> a, List<Long> b, List<Long> out, long base){
    	List<Long> addzero = new LinkedList<>();
    	List<Long> sum = new LinkedList<>();
    	for(Long bVal: b){
    		List<Long> prod = new LinkedList<>();
    		prod.addAll(addzero);
    	    multiplySingle(a, bVal,prod,base);
    	    List<Long>tempSum = new LinkedList<>();
    	    add(sum,prod,tempSum,base);
    	    sum = tempSum;
    	    addzero.add(0L);
    	}
    	out.addAll(sum);
    	return out;
    }
    
    private static void multiplySingle(List<Long> a, Long b, List<Long> out, long base){
    	Iterator<Long> aIter = a.iterator();
    	Long carry = 0L;
    	while(aIter.hasNext()){
    		Long prod = (next(aIter) * b) + carry;
    		List<Long> prodList = convertFromDecimalToBase(prod, base);
    		out.add(prodList.get(0));
    		carry = prodList.size() > 1 ? prodList.get(1) : 0L;		
    	}
    	if(carry > 0){
    		out.add(carry);
    	}
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

    private int getLeadingZeros(String number){
        int count = 0;
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == '0') {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    public static Num singleDigitDivision(List<Long> numerator, Long denominator) {
        Collections.reverse(numerator);

        if (denominator == 0L) {
            throw new NullPointerException("Denominator is zero");
        }
        if (numerator.size() == 0 || isZero(numerator)) {
            return new Num(0L);
        }

        Num quotient = null;
        Iterator<Long> it = numerator.iterator();
        Long num = 0L;
        Long q = null, r = 0L;
        Long i = 0l;
        while (it.hasNext()) {

            StringBuilder newNumber = new StringBuilder();

            Long nextNum = nextForNull(it);
            newNumber.append(String.valueOf(r)).append(String.valueOf(nextNum));
            num = Long.parseLong(newNumber.toString());

            if (num >= denominator) {
                q = Math.floorDiv(num, denominator);
                r = Math.floorMod(num, denominator);
                if (quotient == null) {
                    quotient = new Num(q);
                } else {
                    Num nq = new Num(q);
                    quotient = Num.product(quotient, new Num(base()));
                    quotient = Num.add(quotient, nq);
                }

            } else {
                if (quotient != null) {
                    quotient = Num.product(quotient, new Num(base()));
                }
                r = num;
            }

        }
        if (quotient == null) {
            quotient = new Num(r);
        }
        System.out.println("single digit quotient:  " + quotient);
        Collections.reverse(numerator);
        return quotient;
    }

    private static List<Long> getSubListForNumerator(List<Long> num, List<Long> den) {
        return num.subList(den.size() - 1, num.size());
    }

    private static <T> T getLastElement(List<T> elements) {
        final Iterator<T> itr = elements.iterator();
        T lastElement = itr.next();

        while (itr.hasNext()) {
            lastElement = itr.next();
        }

        return lastElement;
    }

    private static Num getMagnitude(Num orgNum) {
        Num copyNum = new Num(orgNum);
        copyNum = removeLeadingZerosFromList(copyNum);
        copyNum.negative = false;
        return copyNum;
    }

    public static Num removeLeadingZerosFromList(Num num) {
        List<Long> list = num.getList();
        for (int i = list.size() - 1; i > -1; i--) {
            if (list.get(i) == 0L) {
                list.remove(i);
            } else {
                break;
            }
        }

        return num;
    }

    private static boolean isZero(List<Long> list) {
        Long sum = 0l;
        for (Long node : list) {
            sum = sum + node;
            if (sum > 0) {
                return false;
            }
        }

        return true;
    }


}


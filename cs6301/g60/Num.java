
// Starter code for lp1.

// Change following line to your group number
// Changed type of base to long: 1:15 PM, 2017-09-08.
package cs6301.g60;


import java.util.*;

public class Num  implements Comparable<Num> {

    static long defaultBase = 10;  // This can be changed to what you want it to be.
    static long base = 16;  // Change as needed
    private boolean negative = false;

    private List<Long> list;

    private int MaxChunkSize = 4;

    Num() {
        list = new LinkedList<>();
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
            if(current=='-'){
                this.negative = true;
            }else {
                Num first = Num.product(num, base10);
                Num second = new Num(current - '0');
                num = Num.add(first, second);
            }
        }
        System.out.println(this.negative+"_____________________________________"+num.list);
        // copies the num back to object "this"
        this.list = num.list;
        this.defaultBase = num.defaultBase;
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
        //System.out.println(list.size() + "  " + list);
    }

    static Num add(Num a, Num b) {
    	Num out = new Num();
    	List<Long> outList = out.getList();
    	add(a.getList(), b.getList(), outList,base);
    	//System.out.println("sum" + outList);
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
        //System.out.println("difference" + outList);
        return out;
    }

    // Implement Karatsuba algorithm for excellence credit
    static Num product(Num a, Num b) {
    	Num out = new Num();
    	List<Long> outList = out.getList();
    	product(a.getList(), b.getList(), outList, Num.base);
    	//System.out.println("product" + outList);
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
        //if denominator equals 0;
        if(b.getList().size()==0){
            throw new NullPointerException("Denominator is zero");
        }
        if(a.getList().size()==0){
            return new Num(0L);
        }
        Num result = divideAndMod(a, b);
        if((!a.negative&& b.negative) || (a.negative&& !b.negative)){
            result.negative = true;
        }

       return result;
    }

    static Num mod(Num a, Num b) {
        //if denominator equals 0;
        if(b.getList().size()==0){
            throw new NullPointerException("Denominator is zero");
        }
        if(a.getList().size()==0){
            return new Num(0L);
        }
        return Num.subtract(Num.product(divideAndMod(a, b), b), a);
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
    //TODO: take any one off
    public int compareToNum(Num other) {
        int gt = findGreaterList(this.list, other.list);
        if (gt == 1) {
            return +1;
        } else if (gt == 2) {
            return -1;
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
        System.out.println(base + ":  " + sb.toString());
    }

    // Returns a string in base 10
    public String toString() {
        List<Long> ourBase = convertFromDecimalToBase(base(),defaultBase);
        StringBuilder result = new StringBuilder();
        List<Long> list = this.getList();
        List<Long> resultList = new LinkedList<>();

        // if the list is of size 0
        if (list.size() == 0) {
            return new String("0");
        }

        ListIterator<Long> it = list.listIterator(list.size());

        while (it.hasPrevious()) {
            List<Long> l1 = new LinkedList<>();
            Num.multiply(resultList, ourBase, l1, defaultBase);
            resultList.clear();
            Num.add(l1, convertFromDecimalToBase(it.previous(), defaultBase), resultList, defaultBase);

        }


        //take out the leading zeros and traverse from the last
        removeLeadingZerosFromList(resultList);
        it = resultList.listIterator(resultList.size());

        if(this.negative){
            result.append('-');
        }

        while(it.hasPrevious()){
            result.append(it.previous());
        }
        if(resultList.size() == 0) {
            return "0";
        }
        return result.toString();
    }

    public static long base() {
        return base;
    }


    /**
     * All helper methods
     */

    private static List<Long> convertFromDecimalToBase(Long number, long base) {

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
        //System.out.println("this is the list: "+ list);
        return list;
    }

    private static Long next(Iterator<Long> it){
    	return it.hasNext()? it.next() : 0L;
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

    private static Num singleDigitDivision(List<Long> numerator, Long denominator) {
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

            Long nextNum = next(it);
            num = r*base()+nextNum;

            //start
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

            //end

        }
        if (quotient == null) {
            quotient = new Num(r);
        }
        System.out.println("single digit quotient:  " + quotient);
        Collections.reverse(numerator);
        return quotient;
    }

    private static void removeLeadingZerosFromList(List list) {
        ListIterator<Long> it = list.listIterator(list.size());

        while(it.hasPrevious()){
            if(it.previous()==0l){
                it.remove();
            }else{
                break;
            }
        }
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

    private static Num divideAndMod(Num a, Num b){

        Num left = new Num(0L);
        Num right = b;
        Num num1 = new Num(1L);

        Num middleNum = singleDigitDivision(a.getList(), 2L);

        boolean b1 = Num.product(b,middleNum).compareToNum(a)<=0;
        boolean b2 = Num.product(b,Num.add(middleNum, num1)).compareToNum(a)>0;

        while(true){
            if((!b1&&b2)){
                right = middleNum;
            } else if (b1&&!b2) {
                left = Num.add(middleNum, num1);;
            }else if(b1&& b2){
                break;
            }
            middleNum = singleDigitDivision(Num.add(left,right).getList(), 2L);

            b1 = Num.product(b,middleNum).compareToNum(a)<=0;
            b2 = Num.product(b,Num.add(middleNum, num1)).compareToNum(a)>0;

            System.out.println("quotient:   "+middleNum.getList());
        }
        return middleNum;
    }

}


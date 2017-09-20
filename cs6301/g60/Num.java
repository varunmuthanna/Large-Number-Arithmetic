
// Starter code for lp1.

// Change following line to your group number
// Changed type of base to long: 1:15 PM, 2017-09-08.
package cs6301.g60;


import java.util.*;

public class Num  implements Comparable<Num> {

    static long defaultBase = 10;  // This can be changed to what you want it to be.
    static long base = 10000;  // Change as needed
    private boolean negative = false;
    static boolean karatsuba = true;

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
    	Num out = null;
        if(a.negative && !b.negative){
        	out = subtractHelper(b, a);
        }else if(a.negative && b.negative){
        	out = addHelper(b, a);
        	out.negative = true;
        }else if(!a.negative && b.negative){
        	out = subtractHelper(a, b);
        }else{
        	out = addHelper(a, b);
        }
        return out;
    }

    static Num subtract(Num a, Num b) {
        Num out = null;
        if(a.negative && !b.negative){
        	out = addHelper(a, b);
        	out.negative = true;
        }else if(a.negative && b.negative){
        	out = subtractHelper(b, a);
        }else if(!a.negative && b.negative){
        	out = addHelper(a, b);
        }else{
        	out = subtractHelper(a, b);
        }
        return out;
    }

    // Implement Karatsuba algorithm for excellence credit
    static Num product(Num a, Num b) {
    	Num out = null;
    	if(a.getList().size() == 0 || b.getList().size() == 0){
    		return new Num(0L);
    	}
    	if(karatsuba){
    		if(findGreaterList(a.getList(),b.getList()) >= 0){
    		    out = karMultiply(a,b);
    		}else{
    			out = karMultiply(b,a);
    		}
    	}else{
    	    out = new Num();
    	    List<Long> outList = out.getList();
    	    out.getList().addAll(product(a.getList(), b.getList(), Num.base));
    	    //System.out.println("product" + outList);
            return out;
    	}
    	out.negative = a.negative ^ b.negative;
    	return out;
    }

    // Use divide and conquer
    static Num power(Num a, long n) {
        Num out = getPower(a, n);
        if(n % 2 != 0 && a.negative){
        	out.negative = true;
        }
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
    	if(n.negative){
    		return null;
    	}
    	Num two = new Num(2L);
    	Num zero = new Num(0L);
    	Num out = getPower(a,n);
    	if (Num.mod(n, two).compareTo(zero) != 0 && a.negative){
    		out.negative =true;
    	}
    	return out;
    }

    static Num squareRoot(Num a) {
    	if(a.negative){
    		return null;
    	}
    	return getSquareRoot(a);
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
            return findGreaterList(this.list, other.list);
           
        }
    }
    //TODO: take any one off
    public int compareToNum(Num other) {
        return findGreaterList(this.list, other.list);
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
            List<Long> l1 = multiply(resultList, ourBase, defaultBase);
            resultList.clear();
            Num.add(l1, convertFromDecimalToBase(it.previous(), defaultBase), resultList);

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
    
    private static Num subtractHelper(Num a, Num b){
    	Num out;
    	int comp = findGreaterList(a.getList(), b.getList());
        if (comp > 0) {
            out = subtract(a.getList(), b.getList());
        } else if (comp < 0) {
            out = subtract(b.getList(), a.getList());
            out.negative = true;
        } else {
            out = new Num(0L);
        }
        //System.out.println("difference" + outList);
        return out;
    }
    
    private static Num addHelper(Num a, Num b){
    	Num out = new Num();
    	add(a.getList(), b.getList(),out.getList());
    	return out;
    }

    private static void add(List<Long> a, List<Long> b, List<Long> outList){
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
    
    private static Num subtract(List<Long> aList, List<Long> bList){
    	Num out = new Num();
    	List<Long> outList = out.getList();
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
    	return out;
    }
    
    private static Num karMultiply(Num a, Num b){
    	if(b.getList().size() == 1){
    		return multiplySingle(a,b.getList().get(0), Num.base);
    	}
    	int k = b.getList().size()/2;
    	Num ah = new Num();
    	ah.getList().addAll(a.getList().subList(k, a.getList().size()));
    	
    	Num bh = new Num();
    	bh.getList().addAll(b.getList().subList(k, b.getList().size()));
    	
    	Num al = new Num();
    	al.getList().addAll(a.getList().subList(0,k));
    	
    	Num bl = new Num();
    	bl.getList().addAll(b.getList().subList(0,k));
    	
    	Num msbPart = Num.product(ah,bh);
    	Num lsbPart = Num.product(al, bl);
    	Num aSum = Num.add(ah, al);
    	Num bSum = Num.add(bh, bl);
    	Num abSumProd = Num.product(aSum, bSum);
    	
    	Num middle = shiftBase(Num.subtract(Num.subtract(abSumProd, msbPart),lsbPart),k);
    	Num first = shiftBase(msbPart,2*k);
    	
    	return Num.add(Num.add(first, middle),lsbPart);
    }
    
    private static List<Long> product(List<Long> a, List<Long> b, long base){
    	List<Long> out = new LinkedList<>(); 
    	int gt = findGreaterList(a,b);
    	if(gt == 2){
    		out = multiply(b, a, base);
    	}else{
    		out = multiply(a, b, base);
    	}
    	return out;
    }
    
    private static Num getPower(Num a, long n){
    	Num out = new Num();
    	List<Long> outList = out.getList();
    	if (n==0){
    		outList.add(1L);
    		return out;
    	}
    	if(n == 1){
    		outList.addAll(a.getList());
    		return out;
    	}else if(n == 2){
    		return Num.product(a,a);
    	}
    	
    	if(n % 2 == 0){
    		out = getPower(getPower(a,n/2), 2L);
    	}else{
    		out = Num.product(getPower(a,n/2), a);
    	}
    	return out;
    }
    
    private static Num getPower(Num a, Num b){    	
    	Num zero = new Num(0L);
    	Num one = new Num(1L);
    	Num two = new Num(2L);
    	if(b.compareTo(zero) == 0){
    		return one;
    	}
    	if(b.compareTo(one) == 0){
    		Num out = new Num();
    		out.getList().addAll(a.getList());
    		return out;
    	}else if (b.compareTo(two) == 0){
    		return Num.product(a, a);
    	}
    	
    	if(Num.mod(b, two).compareTo(zero) == 0){
    		return getPower(getPower(a,Num.divide(b, two)), two);
    	}else{
    		return Num.product(getPower(a,Num.subtract(b, one)),a);
    	}  	
    }
    
    private static Num getSquareRoot(Num a){
    	Num one = new Num(1L);
    	Num two = new Num(2L);
    	
    	Num mid;
    	Num left = new Num(0L);
    	Num right = a;
    	
    	while(true){
    		mid = Num.divide(Num.add(left, right), two);
    		if(a.compareTo(Num.power(mid,2L)) >= 0){
    			if(a.compareTo(Num.power(Num.add(mid, one), 2L)) < 0){
    				break;
    			}else{
    				left = Num.add(mid, one);
    			}
    		}else{
    			right = mid;
    		}
    	}
    	
    	return mid;
    }
    
    private static List<Long> multiply(List<Long> a, List<Long> b, long base){
    	List<Long> out = new LinkedList<>();
    	List<Long> addzero = new LinkedList<>();
    	List<Long> sum = new LinkedList<>();
    	for(Long bVal: b){
    		List<Long> prod = new LinkedList<>();
    		prod.addAll(addzero);
    		multiplySingle(a, bVal,prod,base);
    	    List<Long>tempSum = new LinkedList<>();
    	    add(sum,prod,tempSum);
    	    sum = tempSum;
    	    addzero.add(0L);
    	}
    	out.addAll(sum);
    	return out;
    }
    
    private static Num multiplySingle(Num a, long b, long base){
    	Num out = new Num();
    	List<Long> outList = out.getList();
    	Iterator<Long> aIter = a.getList().iterator();
    	Long carry = 0L;
    	while(aIter.hasNext()){
    		Long prod = (next(aIter) * b) + carry;
    		List<Long> prodList = convertFromDecimalToBase(prod, base);
    		outList.add(prodList.get(0));
    		carry = prodList.size() > 1 ? prodList.get(1) : 0L;		
    	}
    	if(carry > 0){
    		outList.add(carry);
    	}
    	
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
		Iterator<Long> it1 = first.iterator();
		Iterator<Long> it2 = second.iterator();
		while(it1.hasNext() || it2.hasNext()){
			Long firstVal = next(it1);
			Long secondVal = next(it2);
			if(firstVal > secondVal){
				flag = 1;
			}else if(firstVal < secondVal){
				flag = -1;
			}
		}
		return flag;
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
        //System.out.println("single digit quotient:  " + quotient);
        Collections.reverse(numerator);
        return quotient;
    }

    private static void removeLeadingZerosFromList(List<Long> list) {
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
    
    private static Num shiftBase(Num a, long n){
    	Num out = new Num();
    	List<Long> outList = out.getList();
    	outList.addAll(a.getList());
    	for(long i = 0; i < n; i++){
    	  outList.add(0,0L);
    	}
    	return out;
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

            //System.out.println("quotient:   "+middleNum.getList());
        }
        return middleNum;
    }

}


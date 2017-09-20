// Sample code for Level 2 driver for lp1

// Change following line to your group number
package cs6301.g60;

import java.math.BigInteger;

public class LP1L2 {
    public static void main(String[] args) {
	/*Num x = new Num(999);
	Num y = new Num("8");
	Num z = Num.add(x, y);
	System.out.println(z);
	Num a = Num.power(x, y);
	System.out.println(a);
	z.printList();*/

//	Num n = new Num("100817928689169569327569273659236058621912769625961957619562196592169763912639812739814568765787863461973468916394687198276987139871298359217538625379691283760826489162487512497251947527983691254308560238650823548162408");
//    Num m = new Num("189991928613569137564");
//    System.out.println("output"+Num.divideAndMod(n, m, true));

        Num n = new Num("918264023086108356117294691246712647816724596791826402308610835611254691826402308610835619182640230861083561712945129674596125412412412");
        Num m = new Num("647816724596791826402308610835611254691826402308610835619182");
        System.out.println("output:  "+Num.divide(n, m));

        //Num.singleDigitDivision(new Num("1200").getList(), new Num("2").getList().get(0));

        BigInteger xx = new BigInteger("918264023086108356117294691246712647816724596791826402308610835611254691826402308610835619182640230861083561712945129674596125412412412");
        BigInteger yy = new BigInteger("647816724596791826402308610835611254691826402308610835619182");

        System.out.println("Correct output: " + xx.divide(yy));

    }
}

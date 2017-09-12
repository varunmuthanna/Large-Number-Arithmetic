// Sample code for Level 1 driver for lp1

// Change following line to your group number
package cs6301.g60;

import java.math.BigInteger;

public class LP1L1 {
    public static void main(String[] args) throws Exception {
        /*Num x = new Num(999);
        Num y = new Num("8");
        Num z = Num.add(x, y);
        System.out.println(z);
        Num a = Num.power(x, 8);
        System.out.println(a);
        z.printList();*/
        /*Num n = new Num(9223372036854775807L);
        n.printList();*/


        Num x = new Num("44");

        Num y = new Num("24");
        Num z = Num.add(x, y);

        System.out.println(z);

        Num z1 = Num.product(y, x);
        System.out.println(z1);
        
        Num z2 = Num.power(y, 8L);
        System.out.println(z2);
        
        //Num z1 = Num.subtract(x, y);
        System.out.println(z1);

        BigInteger b = new BigInteger("10000000000000000000000000000000000000");
        BigInteger b1 = new BigInteger("100000000000000000000000000000");
        b.divide(b1);
        //System.out.println(Num.convertFromDecimalToBase(123123L,2));
    }
}

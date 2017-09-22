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


        //Num x = new Num(new StringBuilder("12412412421").reverse().toString());

//        Num x1 = new Num(7812603692831612412L);
//        x1.printList();

        //System.out.println();System.out.println();System.out.println();

        Num a = new Num("-350861387498247691694873195743187987654568987548948765567876567867856784534321232345456787899898789890987679876543");
        Num b = new Num("-35086138749824769169487319574318798765456898754894876556787656786785678453432123234545678789989878989098767987654");
        Num result = Num.divide(a, b);

        System.out.println(result);



        BigInteger x1 = new BigInteger("0");
        BigInteger y1 = new BigInteger("100");

        System.out.println(x1.mod(y1));

        //Constructor testing
//       Num one = new Num("0");
//       System.out.println("is Negetive:  "+ one.negative);
//       one.printList();
//       System.out.println("toString output "+one);


        /*Num x = new Num(8712387612837861823L);
        Num y = new Num(1L);*/

        //System.out.println(Num.convertFromDecimalToBase(35L, 16));

        //Num z = Num.subtract(x, y);
        //System.out.println(z);

        /*Num z1 = Num.product(y, x);
        System.out.println(z1);

        Num z2 = Num.power(y, 8L);
        System.out.println(z2);

        //Num z1 = Num.subtract(x, y);
        System.out.println(z1);*/

        //Collections.reverse(x.getList());
        //System.out.println(x.getList());
        //Num.singleDigitDivision(x.getList(), 3l);
        /*Num.divide(x,y);
        Num.mod(x, y);

        BigInteger b1 = new BigInteger("8712387612837861823");
        BigInteger b2 = new BigInteger("1");
        System.out.println("True q: " + b1.divide(b2));
        System.out.println("True r: "+b1.mod(b2));*/

        //Num.singleDigitDivision(x.getList(), y.getList().get(0));
        //System.out.println(b1.divide(b2));

        /*Num a = new Num("85849037612648764376549098612765874365348765673543");
        Num b = new Num("566983648761476308476145");*/
        
        //System.out.println("a power b = " + Num.power(a, b));
        Num ans =Num.mod(a, b);
        System.out.println("a product b = " + ans);
        
        
        BigInteger aa = new BigInteger("85849037612648764376549098612765874365348765673543");
        BigInteger bb = new BigInteger("566983648761476308476145");
        System.out.println(aa.mod(bb));

    }
}

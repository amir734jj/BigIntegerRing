import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.hesamian.ring.*;

public class Main {

    public static void main(String[] args) {

        Monomial test1 = new Monomial(BigInteger.valueOf(23));
        Monomial test2 = new Monomial(BigInteger.valueOf(2), BigInteger.valueOf(0), "x");
        Monomial test3 = new Monomial(BigInteger.valueOf(5), BigInteger.valueOf(2), "y");
        Ring ring = new Ring(test1, Operation.getOperation("add"), test2);
        ring.addMonomial(Operation.getOperation("-"), test3);
        System.out.println(test1);
        System.out.println(test2);
        System.out.println(ring);
        Map<String, BigInteger> map = new HashMap<String, BigInteger>();
        map.put("x", BigInteger.valueOf(2));
        map.put("y", BigInteger.valueOf(4));
        System.out.println(ring.evaluate(map));
        new Ring("x^2 + 23");
        new Ring("x^2 + 23 + x^4");
        new Ring("x^2 + 23 + 2");
        new Ring("x^2 + 23 - 443f");
        new Ring("x^2 + 23 *3y");
        System.out.println(Monomial.getMonomial("56x"));
        System.out.println(Monomial.getMonomial("x^2"));
        System.out.println(Monomial.getMonomial("245"));
        System.out.println(Monomial.getMonomial("3x^2"));
        Monomial monomials[] = ring.getMonomials();
        System.out.println(Arrays.toString(monomials));
        Arrays.sort(monomials);
        System.out.println(Arrays.toString(monomials));
        Arrays.sort(monomials, Collections.reverseOrder());
        System.out.println(Arrays.toString(monomials));

        System.out.println(new Ring("x^2 + 23 *3y + x^3y^z"));
        System.out.println(new Ring("x^2 + 23 *3y + x*3y+z"));
        System.out.println(new Ring("x^2 + (23 *3y) + x^3y^z"));
        System.out.println(new Ring("x^2 + (23 *(3y + x)*3y)+z"));

        Ring ring1 = new Ring("x^2 + 23 *3y + x^3y^z");
        Ring ring2 = new Ring("x^2 + 23 *3y + x*3y+z");
        Ring ring3 = new Ring("x^2 + (23 *3y) + x^3y^z");
        Ring ring4 = new Ring("x^2 + (23 *(3y + x)*3y)+z");

        Map<String, BigInteger> dummyMap = new HashMap<String, BigInteger>() {
            {
                put("x", BigInteger.valueOf(1));
                put("y", BigInteger.valueOf(2));
                put("z", BigInteger.valueOf(3));
            }
        };

        System.out.println(ring1.evaluate(dummyMap));
        System.out.println(ring2.evaluate(dummyMap));
        System.out.println(ring3.evaluate(dummyMap));


    }
}

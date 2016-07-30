package com.hesamian.ring.test;

import static org.junit.Assert.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.Test;
import com.hesamian.ring.*;

public class testRing {

    @Test
    public void testProperties() {
        Monomial test1 = new Monomial(BigInteger.valueOf(23));
        Monomial test2 = new Monomial(BigInteger.valueOf(2), BigInteger.valueOf(0), "x");
        Monomial test3 = new Monomial(BigInteger.valueOf(5), BigInteger.valueOf(2), "y");

        Operation operation1 = Operation.getOperation("add");
        Operation operation2 = Operation.getOperation("*");

        Ring ring = new Ring(test1, operation1, test2);
        ring.addMonomial(operation2, test3);

        assertTrue(Arrays.equals(ring.getArray(), new Object[]{test1, operation1, test2, operation2, test3}));
    }

    @Test
    public void testEvaluate() {
        Monomial test1 = new Monomial(BigInteger.valueOf(23));
        Monomial test2 = new Monomial(BigInteger.valueOf(2), BigInteger.valueOf(0), "x");
        Monomial test3 = new Monomial(BigInteger.valueOf(5), BigInteger.valueOf(2), "y");

        Operation operation1 = Operation.getOperation("add");
        Operation operation2 = Operation.getOperation("*");

        Ring ring = new Ring(test1, operation1, test2);
        ring.addMonomial(operation2, test3);

        assertTrue(ring.evaluate(new HashMap<String, BigInteger>() {
            {
                put("x", BigInteger.valueOf(1));
                put("y", BigInteger.valueOf(2));
            }
        }).equals(BigInteger.valueOf(63)));

        assertTrue(new Ring(new Monomial(BigInteger.valueOf(123))).evaluate(new HashMap<String, BigInteger>()).equals(BigInteger.valueOf(123)));
    }

    @Test
    public void testStringConstructor() {
        Monomial test1 = new Monomial(BigInteger.valueOf(23));
        Monomial test2 = new Monomial(BigInteger.valueOf(2), BigInteger.valueOf(0), "x");
        Monomial test3 = new Monomial(BigInteger.valueOf(5), BigInteger.valueOf(2), "y");

        Operation operation1 = Operation.getOperation("add");
        Operation operation2 = Operation.getOperation("*");

        Ring ring = new Ring(test1, operation1, test2);
        ring.addMonomial(operation2, test3);

        assertTrue(ring.equals(new Ring("23 + 2x^0 * 5y^2")));
        assertTrue(new Ring(new Monomial(BigInteger.valueOf(123))).equals(new Ring("123")));
        assertTrue(new Ring(new Monomial(BigInteger.valueOf(123)), operation1, new Monomial(BigInteger.valueOf(321))).equals(new Ring("123 + 321")));
        assertTrue(new Ring(new Monomial(BigInteger.valueOf(123)), operation2, new Monomial(BigInteger.valueOf(321))).equals(new Ring("123 * 321")));
        assertTrue(new Ring(test1, operation2, test3).equals(new Ring("23 * 5y^2")));
    }
}

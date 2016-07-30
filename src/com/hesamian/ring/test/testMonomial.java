package com.hesamian.ring.test;

import static org.junit.Assert.*;
import java.math.BigInteger;
import java.util.HashMap;
import org.junit.Test;
import com.hesamian.ring.*;

public class testMonomial {

	@Test
	public void testProperties() {
		assertTrue(new Monomial(BigInteger.valueOf(123)).getCoefficient()
				.equals(BigInteger.ONE));
		assertTrue(new Monomial(BigInteger.valueOf(123)).getExponent().equals(
				BigInteger.ZERO));
		assertTrue(new Monomial(BigInteger.valueOf(123),
				BigInteger.valueOf(321), "x").getCoefficient().equals(
				BigInteger.valueOf(123)));
		assertTrue(new Monomial(BigInteger.valueOf(123),
				BigInteger.valueOf(321), "x").getExponent().equals(
				BigInteger.valueOf(321)));
	}

	@Test
	public void testEvaluate() {
		assertTrue(new Monomial(BigInteger.valueOf(123)).evaluate(
				new HashMap<String, BigInteger>()).equals(
				BigInteger.valueOf(123)));

		assertTrue(new Monomial(BigInteger.valueOf(123), BigInteger.valueOf(1),
				"x").evaluate(new HashMap<String, BigInteger>() {
			{
				put("x", BigInteger.valueOf(1));
			}
		}).equals(BigInteger.valueOf(123)));

		assertTrue(new Monomial(BigInteger.valueOf(0), BigInteger.valueOf(2),
				"x").evaluate(new HashMap<String, BigInteger>() {
			{
				put("x", BigInteger.valueOf(3));
				put("y", BigInteger.valueOf(2));
			}
		}).equals(BigInteger.valueOf(0)));

		assertTrue(new Monomial(BigInteger.valueOf(2), BigInteger.valueOf(3),
				"x").evaluate(new HashMap<String, BigInteger>() {
			{
				put("x", BigInteger.valueOf(4));
				put("y", BigInteger.valueOf(2));
			}
		}).equals(BigInteger.valueOf(128)));
	}

	@Test
	public void testGetMonomial() {
		assertTrue(new Monomial(BigInteger.valueOf(123)).equals(Monomial
				.getMonomial("123")));
		assertTrue(new Monomial(BigInteger.valueOf(1), BigInteger.valueOf(1),
				"x").equals(Monomial.getMonomial("x")));
		assertTrue(new Monomial(BigInteger.valueOf(123), BigInteger.valueOf(1),
				"x").equals(Monomial.getMonomial("123x")));
		assertTrue(new Monomial(BigInteger.valueOf(1), BigInteger.valueOf(2),
				"x").equals(Monomial.getMonomial("x^2")));
		assertTrue(new Monomial(BigInteger.valueOf(2), BigInteger.valueOf(3),
				"x").equals(Monomial.getMonomial("2x^3")));
	}
}

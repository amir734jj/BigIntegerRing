package com.hesamian.ring;

import java.math.BigInteger;
import java.util.Map;

public class Monomial implements Comparable<Monomial> {
	private BigInteger coefficient = null;
	private BigInteger exponent = null;
	private BigInteger constant = null;
	private String name = null;

	public Monomial(BigInteger coefficient, BigInteger exponent, String name) {
		super();
		this.coefficient = coefficient;
		this.exponent = exponent;
		this.name = name;
	}

	public Monomial(BigInteger constant) {
		super();
		this.constant = constant;
		this.name = "";
		this.coefficient = BigInteger.ONE;
		this.exponent = BigInteger.ZERO;
	}

	public BigInteger evaluate(Map<String, BigInteger> map) {
		if (constant == null) {
			return coefficient.multiply(map.get(name).pow(exponent.intValue()));
		} else {
			return constant;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((coefficient == null) ? 0 : coefficient.hashCode());
		result = prime * result
				+ ((constant == null) ? 0 : constant.hashCode());
		result = prime * result
				+ ((exponent == null) ? 0 : exponent.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Monomial other = (Monomial) obj;
		if (coefficient == null) {
			if (other.coefficient != null)
				return false;
		} else if (!coefficient.equals(other.coefficient))
			return false;
		if (constant == null) {
			if (other.constant != null)
				return false;
		} else if (!constant.equals(other.constant))
			return false;
		if (exponent == null) {
			if (other.exponent != null)
				return false;
		} else if (!exponent.equals(other.exponent))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String toString() {
		if (constant == null) {
			return coefficient + name + "^" + exponent;
		} else {
			return constant.toString();
		}
	}

	public int compareTo(Monomial monomial) {
		return exponent.compareTo(monomial.exponent);
	}

	public BigInteger getCoefficient() {
		return coefficient;
	}

	public BigInteger getExponent() {
		return exponent;
	}

	public BigInteger getConstant() {
		return constant;
	}

	public String getName() {
		return name;
	}

	public static Monomial getMonomial(String str) {
		BigInteger _coefficient;
		BigInteger _exponent = null;
		String _name = null;

		if (str.matches("([0-9]*[a-zA-z]*\\^[0-9]*)|([0-9]*[a-zA-Z]*)|([0-9]*)")) {
			if (Utility.indexOf("[a-zA-Z]+", str) < 0) {
				return new Monomial(new BigInteger(str));
			} else {
				if (str.substring(0, Utility.indexOf("[a-zA-Z]+", str))
						.isEmpty()) {
					_coefficient = BigInteger.ONE;
				} else {
					_coefficient = new BigInteger(str.substring(0,
							Utility.indexOf("[a-zA-Z]+", str)));
				}

				if (str.indexOf("^") < 0) {
					_name = str.substring(Utility.indexOf("[a-zA-Z]+", str),
							str.length());
					_exponent = BigInteger.ONE;
				} else {
					_name = str.substring(Utility.indexOf("[a-zA-Z]+", str),
							str.indexOf("^"));
					_exponent = new BigInteger(str.substring(
							str.indexOf("^") + 1, str.length()));
				}

				return new Monomial(_coefficient, _exponent, _name);
			}
		} else {
			return null;
		}
	}
}

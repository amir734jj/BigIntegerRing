package com.hesamian.ring;

import java.lang.reflect.Method;
import java.math.BigInteger;

public class Operation {

	private enum Type {

		exponentiation(0, "pow", "pow, exponentiation, exp,^", "^"), multiplication(
				1, "multiply", "multiply, multiplication, *", "*"), addition(2,
				"add", "add, addition, +", "+"), division(3, "divide",
				"divide, division, /", "/"), subtraction(4, "subtract",
				"subtract, subtraction, -", "-");

		private int integerValue = -1;
		private String method = null;
		private String alternateNames = null;
		private String printName = null;

		private Type(int value, String method, String alternateNames,
				String printName) {
			this.integerValue = value;
			this.method = method;
			this.alternateNames = alternateNames;
			this.printName = printName;
		}

		public BigInteger evaluateExpression(BigInteger value1,
				BigInteger value2) {
			return (BigInteger) suppressException(method, value1, value2);
		}

		private static Object suppressException(String str, BigInteger value1,
				BigInteger value2) {

			try {
				Method method = value1.getClass().getMethod(str,
						str.equals("pow") ? int.class : BigInteger.class);

				return (str.equals("pow")) ? method.invoke(value1,
						value2.intValue()) : method.invoke(value1, value2);

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		public static Type resolveType(String str) {
			for (Type type : Type.class.getEnumConstants()) {
				if (type.alternateNames.contains(str.toLowerCase())) {
					return type;
				}
			}
			return null;
		}

	}

	private Type value;

	private Operation(Type value) {
		super();
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Operation other = (Operation) obj;
		if (value != other.value)
			return false;
		return true;
	}

	public String toString() {
		return value.printName;
	}

	public int intValue() {
		return value.integerValue;
	}

	public BigInteger evaluateExpression(BigInteger value1, BigInteger value2) {
		try {
			return value.evaluateExpression(value1, value2);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Operation getOperation(String value) {
		return new Operation(Type.resolveType(value));
	}

	public static int countOfTypes() {
		return Type.class.getEnumConstants().length;
	}

	public static String getOperationPrintName() {
		String str = "";
		for (Type type : Type.class.getEnumConstants()) {
			str += type.printName;
		}

		return str;
	}

	public static int existPrintName(String str) {
		int index = -1;

		for (Type type : Type.class.getEnumConstants()) {
			if (str.indexOf(type.printName) > -1) {
				return str.indexOf(type.printName);
			}
		}

		return index;
	}
}

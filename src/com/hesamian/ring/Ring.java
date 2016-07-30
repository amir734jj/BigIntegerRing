package com.hesamian.ring;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class Ring {
    private ArrayList<Object> array = null;
    private String raw = null;

    public Ring() {
        super();
        if (array == null) {
            array = new ArrayList<Object>();
        }
    }

    public Ring(Monomial monomial) {
        this();
        if (array == null) {
            array = new ArrayList<Object>();
        }

        array.add(monomial);
        checkStructure();
    }

    public Ring(Operation operation, Monomial monomial) {
        this();
        if (array == null) {
            array = new ArrayList<Object>();
        }

        array.add(operation);
        array.add(monomial);
        checkStructure();
    }

    public Ring(Monomial monomial1, Operation operation, Monomial monomial2) {
        super();
        if (array == null) {
            array = new ArrayList<Object>();
        }

        array.add(monomial1);
        array.add(operation);
        array.add(monomial2);
        checkStructure();
    }

    public Ring(String str) {
        this();

        String tokens[] = processComplexExpression(str);

        for (int index = 0; index < tokens.length; index++) {
            tokens[index] = tokens[index].replaceAll(" ", "");

            if (tokens[index].contains("(") || tokens[index].contains(")")) {
                array.add(new Ring(tokens[index].substring(tokens[index].indexOf("(") + 1, tokens[index].lastIndexOf(")"))));
            } else if (index % 2 == 0) {
                array.add(Monomial.getMonomial(tokens[index]));
            } else {
                array.add(Operation.getOperation(tokens[index]));
            }
        }

        checkStructure();
    }
    public static String[] processComplexExpression(String str) {
        ArrayList<String> list = new ArrayList<String>();

        str = str.replaceAll(" ", "");

        String temp = "";
        boolean expression = false;
        int countOfExpressions = 0;

        for (int index = 0; index < str.length(); index++) {
            if (str.charAt(index) == '(' || str.charAt(index) == ')' || expression) {
                temp += str.charAt(index);
                countOfExpressions = (str.charAt(index) == '(') ? (countOfExpressions + 1) : (str.charAt(index) == ')' ? countOfExpressions - 1 : countOfExpressions);
                expression = (str.charAt(index) == ')' && countOfExpressions == 0) ? false : true;
            } else if (Character.isAlphabetic(str.charAt(index)) || Character.isDigit(str.charAt(index))) {
                temp += str.charAt(index);
            } else if (str.charAt(index) == '^' && index + 1 < str.length() && Character.isDigit(str.charAt(index + 1))) {
                temp += str.charAt(index);
            } else if (Operation.existPrintName(Character.toString(str.charAt(index))) > -1) {
                list.add(temp);
                list.add(Character.toString(str.charAt(index)));
                temp = "";
            }
        }

        if (!temp.isEmpty()) {
            list.add(temp);
        }

        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()) {
            temp = iterator.next();

            if (temp.contains("^") && temp.substring(temp.indexOf("^") + 1).matches(".*[a-zA-Z]+.*")) {
                int index = list.indexOf(temp);

                list.set(index, temp.substring(0, temp.indexOf("^")));
                list.add(index + 1, "^");
                list.add(index + 2, temp.substring(temp.indexOf("^") + 1));

                iterator = list.iterator();
            }
        }

        return list.toArray(new String[list.size()]);
    }

    public Ring addMonomial(Operation operation, Monomial monomial) {
        array.add(operation);
        array.add(monomial);

        checkStructure();

        return this;
    }

    public BigInteger evaluate(Map<String, BigInteger> map) {
        if (array.size() == 0) {
            return BigInteger.ZERO;
        } else if (array.size() == 1) {
            return ((Monomial) array.get(0)).evaluate(map);
        } else {
            int passNumber = 0;
            Object clonedArray[] = array.toArray();

            BigInteger value1 = null;
            BigInteger value2 = null;

            Operation operation = null;

            while (passNumber < Operation.countOfTypes()) {

                for (int index = 0; index < clonedArray.length; index++) {
                    passNumber = (clonedArray[index] instanceof Operation && ((Operation) clonedArray[index]).intValue() < passNumber) ? 0 : passNumber;

                    if (clonedArray[index] instanceof Operation && passNumber == ((Operation) clonedArray[index]).intValue()) {

                        operation = (Operation) clonedArray[index];

                        if (clonedArray[index - 1] instanceof BigInteger) {
                            value1 = (BigInteger) clonedArray[index - 1];
                        } else if (clonedArray[index - 1] instanceof Monomial) {
                            value1 = ((Monomial) clonedArray[index - 1]).evaluate(map);
                        } else if (clonedArray[index - 1] instanceof Monomial) {
                            value1 = ((Ring) clonedArray[index - 1]).evaluate(map);
                        } else {
                            System.out.println("Critical error during evaluation.");
                        }

                        if (clonedArray[index + 1] instanceof BigInteger) {
                            value2 = (BigInteger) clonedArray[index + 1];
                        } else if (clonedArray[index + 1] instanceof Monomial) {
                            value2 = ((Monomial) clonedArray[index + 1]).evaluate(map);
                        } else if (clonedArray[index + 1] instanceof Monomial) {
                            value1 = ((Ring) clonedArray[index + 1]).evaluate(map);
                        } else {
                            System.out.println("Critical error during evaluation.");
                        }

                        Object tempArray[] = new Object[clonedArray.length - 2];

                        if (index > 1) {
                            System.arraycopy(clonedArray, 0, tempArray, 0, index - 1);
                        }

                        tempArray[index - 1] = operation.evaluateExpression(value1, value2);

                        if (index + 2 < clonedArray.length) {
                            System.arraycopy(clonedArray, index + 2, tempArray, index, clonedArray.length - index - 2);
                        }

                        clonedArray = tempArray;
                    }
                }

                passNumber++;
            }

            checkStructure();

            return (BigInteger) clonedArray[0];
        }
    }
    public Monomial[] getMonomials() {
        ArrayList<Monomial> monomials = new ArrayList<Monomial>();

        for (Object object : array) {
            if (object instanceof Monomial) {
                monomials.add((Monomial) object);
            }
        }

        return monomials.toArray(new Monomial[monomials.size()]);
    }

    public Object[] getArray() {
        return array.toArray();
    }

    private void checkStructure() {
        int counter = 0;

        for (Object object : array) {
            if ((counter % 2 == 1 && object instanceof Monomial) || (counter % 2 == 1 && object instanceof Ring) || (counter % 2 == 0 && object instanceof Operation)) {
                System.out.println("Data structure is corrupt.");
            }
            counter++;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((array == null) ? 0 : array.hashCode());
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
        Ring other = (Ring) obj;
        if (array == null) {
            if (other.array != null)
                return false;
        } else if (!array.equals(other.array))
            return false;
        return true;
    }

    public String toString() {
        String str = "";
        Iterator<Object> iterator = array.iterator();
        Object object = null;

        while (iterator.hasNext()) {
            object = iterator.next();
            if (object instanceof Ring) {
                str += "(" + object + ")";
            } else {
                str += object;
            }

            str += iterator.hasNext() ? " " : "";
        }
        return str;
    }

}
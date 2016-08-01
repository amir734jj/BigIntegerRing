package com.hesamian.ring;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * This class handles Polynomial ring. Ring is a combination of Monomials,
 * Operations and even nested Rings.
 * 
 * @author Seyedamirhossein Hesamian
 * @since 08/30/2016
 */

public class Ring {
    private ArrayList<Object> array = null;

    /**
     * This is a basic constructor that initializes Ring.
     * 
     * @param None
     * @return None
     */
    public Ring() {
        super();
        array = new ArrayList<Object>();
    }

    /**
     * This constructor initializes Ring and adds the Monomial to the ring.
     * After this, you can use addMonomial (which supports method chaining) to
     * add Operation and Monomials to the Ring.
     * 
     * @param None
     * @return None
     */
    public Ring(Monomial monomial) {
        this();
        array.add(monomial);
        checkStructure();
    }

    /**
     * This constructor initializes Ring and adds the Monomial1, Operation,
     * Monomial2 to the ring. After this, you can use addMonomial (which
     * supports method chaining) to add Operation and Monomials to the Ring.
     * 
     * @param Monomial1
     * @param Operation
     * @param Monomial2
     * @return None
     */
    public Ring(Monomial monomial1, Operation operation, Monomial monomial2) {
        this();
        array.add(monomial1);
        array.add(operation);
        array.add(monomial2);
        checkStructure();
    }

    /**
     * This constructor initializes Ring using a String. Make sure String is
     * properly formatted. Currently, code supports parenthesis as well. Please
     * review jUnit tests to see the capabilities of the code. After this, you
     * can use addMonomial (which supports method chaining) to add Operation and
     * Monomials to the Ring.
     * 
     * @param None
     * @return None
     */
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

    /**
     * This method processes a adds a Operation and Monomial to the Ring. This
     * method supports method chaining.
     * 
     * @param Operation
     * @param Monomial
     *            to be added to the Ring
     * @return Ring
     */
    public Ring addMonomial(Operation operation, Monomial monomial) {
        array.add(operation);
        array.add(monomial);

        checkStructure();

        return this;
    }

    /**
     * This method processes a adds a Operation and "Monomial" (actually another
     * Ring) to the Ring. This method supports method chaining.
     * 
     * @param Operation
     * @param "Monomial" (Ring) to be added to the Ring
     * @return Ring
     */
    public Ring addMonomial(Operation operation, Ring ring) {
        array.add(operation);
        array.add(ring);

        checkStructure();

        return this;
    }

    /**
     * This method gets as argument Map<String, String> and it will loop through
     * the Ring and replace old variables with new one. For example: x -> y.
     * Avoid over complicating or misusing this method. For instance replacing x
     * -> y^2 will result in: 2x^3 -> 2y^2^3 which is invalid. This method
     * supports method chaining.
     * 
     * @param Map
     *            <String, String>
     * @return Ring
     */
    public Ring replaceVariables(Map<String, String> map) {
        String raw = this.toString();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            raw = raw.replaceAll(key, value);
        }

        this.array = new Ring(raw).array;

        return this;
    }

    /**
     * This method evaluates a Ring. It uses a Map<String, BigInteger> to
     * evaluate the Ring.
     * 
     * @param Map
     *            <String, BigInteger> to lookup Monomial variable name and it's
     *            respective BigInteger value
     * @return BigInteger result
     */
    public BigInteger evaluate(Map<String, BigInteger> map) {
        if (array.size() == 0) {
            return BigInteger.ZERO;
        } else if (array.size() == 1) {
            if (array.get(0) instanceof Ring) {
                return ((Ring) array.get(0)).evaluate(map);
            } else {
                return ((Monomial) array.get(0)).evaluate(map);
            }
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
                        } else if (clonedArray[index - 1] instanceof Ring) {
                            value1 = ((Ring) clonedArray[index - 1]).evaluate(map);
                        } else {
                            System.out.println("Critical error during evaluation." + clonedArray[index - 1]);
                        }

                        if (clonedArray[index + 1] instanceof BigInteger) {
                            value2 = (BigInteger) clonedArray[index + 1];
                        } else if (clonedArray[index + 1] instanceof Monomial) {
                            value2 = ((Monomial) clonedArray[index + 1]).evaluate(map);
                        } else if (clonedArray[index + 1] instanceof Ring) {
                            value2 = ((Ring) clonedArray[index + 1]).evaluate(map);
                        } else {
                            System.out.println("Critical error during evaluation." + clonedArray[index + 1]);
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

    /**
     * This method returns Monomials in the Ring as well as it's nested Rings.
     * 
     * @param None
     * @return Array of Monomial
     */
    public Monomial[] getMonomials() {
        ArrayList<Monomial> monomials = new ArrayList<Monomial>();

        for (Object object : array) {
            if (object instanceof Monomial) {
                monomials.add((Monomial) object);
            } else if (object instanceof Ring) {
                monomials.addAll(Arrays.asList(((Ring) object).getMonomials()));
            }
        }

        return monomials.toArray(new Monomial[monomials.size()]);
    }

    /**
     * This method returns array of object that Ring is based on. Note that
     * return value is cloned of ArrayList.toArray().
     * 
     * @param None
     * @return Array of Objects
     */
    public Object[] getArray() {
        return array.toArray().clone();
    }

    /**
     * This method checks if data structure is corrupt or not. Note that it does
     * not throws an exception. However, it simply prints and error message.
     * 
     * @param None
     * @return None
     */
    private void checkStructure() {
        int counter = 0;

        for (Object object : array) {
            if ((counter % 2 == 1 && object instanceof Monomial) || (counter % 2 == 1 && object instanceof Ring) || (counter % 2 == 0 && object instanceof Operation)) {
                System.out.println("Data structure is corrupt.");
            }
            counter++;
        }
    }

    /**
     * Overridden hashCode method auto generated by eclipse IDE.
     * 
     * @param None
     * @return Hash value
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((array == null) ? 0 : array.hashCode());
        return result;
    }

    /**
     * Overridden equals method auto generated by eclipse IDE.
     * 
     * @param None
     * @return Boolean equality
     */
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

    /**
     * Overridden toString method auto generated by eclipse IDE.
     * 
     * @param None
     * @return String representation value
     */
    @Override
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

    /**
     * This method processes a String (expression that was given to Ring(String)
     * constructor) and returns String array. Note that it does not processes
     * parenthesis. Parenthesis are processed recursively.
     * 
     * @param String
     *            that was given to Ring(String) constructor
     * @return Processes String array
     */
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
}

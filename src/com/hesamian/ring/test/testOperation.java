package com.hesamian.ring.test;

import static org.junit.Assert.*;
import java.math.BigInteger;
import org.junit.Test;
import com.hesamian.ring.*;

public class testOperation {

    @Test
    public void testEvaluateExpression() {
        assertTrue(Operation.getOperation("+").evaluateExpression(BigInteger.valueOf(2), BigInteger.valueOf(4)).equals(BigInteger.valueOf(6)));
    }

    @Test
    public void testGetOperation() {
        assertTrue(Operation.getOperation("+").equals(Operation.getOperation("add")));
        assertTrue(Operation.getOperation("*").equals(Operation.getOperation("multiply")));
        assertTrue(Operation.getOperation("/").equals(Operation.getOperation("divide")));
        assertTrue(Operation.getOperation("-").equals(Operation.getOperation("subtract")));
    }

}

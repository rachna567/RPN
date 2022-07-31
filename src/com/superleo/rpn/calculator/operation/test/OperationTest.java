package com.superleo.rpn.calculator.operation.test;

import java.math.BigDecimal;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import com.superleo.rpn.calculator.operation.Operation;

import junit.framework.TestCase;

public class OperationTest extends TestCase {
	Stack<BigDecimal> stack = null;

	@Before
	public void setUp() {
		stack = new Stack<BigDecimal>();
		stack.push(new BigDecimal(4));
		stack.push(new BigDecimal(2));
	}

	@Test
	public void testAddition() {
		Operation.addition(stack, null, "+", 1);
		assertEquals(new BigDecimal(6), stack.peek());
	}

	@Test
	public void testSubtract() {
		Operation.subtract(stack, null, "-", 1);
		assertEquals(new BigDecimal(2), stack.peek());
	}

	@Test
	public void testMultiply() {
		Operation.multiply(stack, null, "*", 1);
		assertEquals(new BigDecimal(8), stack.peek());
	}

	@Test
	public void testDivide() {
		Operation.divide(stack, null, "/", 1);
		assertEquals(new BigDecimal(2), stack.peek());
	}

	@Test
	public void testSqrt() {
		stack.push(new BigDecimal(4));
		Operation.sqrt(stack, null, "sqrt", 1);
		assertEquals(new BigDecimal(2), stack.peek());
	}

}

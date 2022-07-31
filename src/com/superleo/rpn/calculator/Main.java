package com.superleo.rpn.calculator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import com.superleo.rpn.calculator.constants.RPNConstant;
import com.superleo.rpn.calculator.exception.RPNCalcException;
import com.superleo.rpn.calculator.operation.Operation;

/**
 * @author rachna
 */
public class Main {
	private static Map<String, String> map = new HashMap<String, String>();
	private static Class aClass = null;

	static {
		map.put(RPNConstant.MULTIPLY, "multiply");
		map.put(RPNConstant.DIVIDE, "divide");
		map.put(RPNConstant.ADDITION, "addition");
		map.put(RPNConstant.SUBTRACT, "subtract");
		map.put(RPNConstant.SQRT, "sqrt");
		map.put(RPNConstant.UNDO, "undo");
		try {
			aClass = Class.forName("com.superleo.rpn.calculator.operation.Operation");
		} catch (ClassNotFoundException e) {
			System.out.println("JVM cannot load the Operation class successfully. Just terminate it.");
			System.exit(-1);
		}

	}

	/**
	 * Call the required operation method on the entered String
	 * 
	 * @param arr
	 * @return
	 */
	private static void rpnCalc(Stack<BigDecimal> stack, String expr) throws RPNCalcException {
		Map<Integer, String> mapExpr = new LinkedHashMap<Integer, String>();

		String[] strings = expr.split(RPNConstant.WHITESPACE);

		for (int pos = 0; pos < strings.length; pos++) {
			String token = strings[pos];

			BigDecimal tokenNum = null;
			try {
				if (!testOperand(token)) {
					tokenNum = new BigDecimal(token);
				}
			} catch (NumberFormatException e) {
				throw new RPNCalcException("Invalid input: " + e.getMessage());
			}

			if (tokenNum != null) {
				stack.push(Operation.toPlainBigDecimal(tokenNum, 15));

			} else if (Arrays.asList(RPNConstant.MULTIPLY, RPNConstant.ADDITION, RPNConstant.SUBTRACT,
					RPNConstant.DIVIDE, RPNConstant.SQRT).contains(token)) {
				Class[] cArg = new Class[4];
				cArg[0] = Stack.class;
				cArg[1] = Map.class;
				cArg[2] = String.class;
				cArg[3] = Integer.class;

				try {
					Method method = aClass.getMethod(map.get(token), cArg);
					method.invoke(null, stack, mapExpr, token, pos);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					System.out.println("Invalid Operation: " + e.getMessage());
				}
			} else if (RPNConstant.UNDO.equals(token)) {
				Operation.undo(stack, expr, token, pos);
			} else if (RPNConstant.CLEAR.equals(token)) {
				stack.clear();
			}
		}
		System.out.println("Stack : " + stack);
	}

	/**
	 * Read the command line input and call rpnCalc method to do required calculation
	 * 
	 * @param arr
	 * @return
	 */
	public static void main(String[] args) {
		boolean repeat = true;
		Scanner scanner = new Scanner(System.in);
		Stack<BigDecimal> stack = new Stack<BigDecimal>();
		while (repeat) {
			String expr = scanner.nextLine();
			try {
				rpnCalc(stack, expr);
			} catch (RPNCalcException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Operator must exist in the desired application operand list
	 * 
	 * @param arr
	 * @return
	 */
	private static boolean testOperand(String operand) {
		if (Arrays.asList(RPNConstant.MULTIPLY, RPNConstant.ADDITION, RPNConstant.SUBTRACT, RPNConstant.DIVIDE,
				RPNConstant.SQRT, RPNConstant.UNDO, RPNConstant.CLEAR).contains(operand)) {
			return true;
		}
		return false;
	}
}
package com.superleo.rpn.calculator.operation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.superleo.rpn.calculator.constants.RPNConstant;

public class Operation {

	static Stack<BigDecimal> lastStackState = new Stack<BigDecimal>();

	static String lastToken;

	interface BinaryAction {
		BigDecimal execute(BigDecimal firstOperand, BigDecimal secondOperand);
	}

	interface UnaryAction {
		BigDecimal execute(BigDecimal firstOperand);
	}

	/**
	 * Common template for the binary operation such as multiply, divide,
	 * addition, subtract
	 * 
	 * @param arr
	 * @return
	 */
	public static void template(BinaryAction action, Stack<BigDecimal> stack, Map<Integer, String> mapExpr,
			String token, Integer position) {
		BigDecimal secondOperand = null;
		BigDecimal firstOperand = null;

		setLastStackState(stack);
		setLastToken(token);

		if (!stack.empty())
			secondOperand = stack.pop();

		if (!stack.empty())
			firstOperand = stack.pop();

		if (secondOperand != null && firstOperand != null) {
			stack.push(toPlainBigDecimal(action.execute(firstOperand, secondOperand), 10));
		} else {
			StringBuilder sb = new StringBuilder();
			mapExpr.entrySet().forEach(c -> {
				sb.append(c + " and ");
			});

			System.out.println("operator " + token + " (position: " + position + "): insufficient parameters stack: \n"
					+ "(the values were not pushed on to the stack due to the previous error)");
		}
	}

	/**
	 * Common template for the Unary operation such as sqrt
	 * 
	 * @param arr
	 * @return
	 */
	public static void template(UnaryAction action, Stack<BigDecimal> stack, Map<Integer, String> mapExpr, String token,
			Integer position) {
		BigDecimal firstOperand = null;

		setLastStackState(stack);
		setLastToken(token);

		if (!stack.empty())
			firstOperand = stack.pop();

		if (firstOperand != null) {
			stack.push(toPlainBigDecimal(action.execute(firstOperand), 10));
		} else {
			StringBuilder sb = new StringBuilder();
			mapExpr.entrySet().forEach(c -> {
				sb.append(c + " and ");
			});

			System.out
					.println("operator " + token + " (position: " + position + "): insufficient parameters stack: 11\n"
							+ "(the values were not pushed on to the stack due to the previous error)");
		}
	}

	public static void addition(Stack<BigDecimal> stack, Map<Integer, String> mapExpr, String token, Integer position) {
		template((firstOperand, secondOperand) -> firstOperand.add(secondOperand), stack, mapExpr, token, position);
	}

	public static void subtract(Stack<BigDecimal> stack, Map<Integer, String> mapExpr, String token, Integer position) {
		template((firstOperand, secondOperand) -> firstOperand.subtract(secondOperand), stack, mapExpr, token,
				position);
	}

	public static void multiply(Stack<BigDecimal> stack, Map<Integer, String> mapExpr, String token, Integer position) {
		template((firstOperand, secondOperand) -> firstOperand.multiply(secondOperand), stack, mapExpr, token,
				position);
	}

	public static void divide(Stack<BigDecimal> stack, Map<Integer, String> mapExpr, String token, Integer position) {
		template((firstOperand, secondOperand) -> firstOperand.divide(secondOperand, 10, BigDecimal.ROUND_DOWN), stack,
				mapExpr, token, position);
	}

	public static void sqrt(Stack<BigDecimal> stack, Map<Integer, String> mapExpr, String token, Integer position) {
		template((firstOperand) -> BigDecimal.valueOf(Math.sqrt(firstOperand.doubleValue())), stack, mapExpr, token,
				position);
	}

	/**
	 * This method checks the value of previous token. If the token was not any
	 * operator then It will the top value from stack otherwise it will make the
	 * last Stack State as current stack state
	 * 
	 * @param arr
	 * @return
	 */
	public static void undo(Stack<BigDecimal> stack, String expr, String token, Integer position) {
		List listOfOperand = Arrays.asList(RPNConstant.MULTIPLY, RPNConstant.ADDITION, RPNConstant.SUBTRACT,
				RPNConstant.DIVIDE, RPNConstant.SQRT, RPNConstant.UNDO, RPNConstant.CLEAR);
		if (!stack.empty()) {
			if (!listOfOperand.contains(getLastToken())) {
				stack.pop();
			} else {
				stack.clear();
				stack.addAll(lastStackState);
			}
		} else {
			System.out.println("Operator " + token + " Position " + position + ": insufficient parameters");
		}
	}

	/**
	 * This method helps to set the precision of BigDecimal value
	 * 
	 * @param arr
	 * @return
	 */
	public static BigDecimal toPlainBigDecimal(BigDecimal val, int precision) {
		return new BigDecimal(val.setScale(precision, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toPlainString());
	}

	@SuppressWarnings("unchecked")
	public static void setLastStackState(Stack<BigDecimal> stack) {
		lastStackState = (Stack<BigDecimal>) stack.clone();
	}

	public static Stack<BigDecimal> getLastStackState(Stack<BigDecimal> stack) {
		return lastStackState;
	}

	public static Stack<BigDecimal> getLastStackState() {
		return lastStackState;
	}

	public static String getLastToken() {
		return lastToken;
	}

	public static void setLastToken(String lastToken) {
		Operation.lastToken = lastToken;
	}

}

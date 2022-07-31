package com.superleo.rpn.calculator.exception;

public class RPNCalcException extends Exception {
	public RPNCalcException() {
	}

	public RPNCalcException(String message) {
		super(message);
	}

	public RPNCalcException(Throwable cause) {
		super(cause);
	}

	public RPNCalcException(String message, Throwable cause) {
		super(message, cause);
	}
}
package com.finance.LoanAdvisor.config;

@SuppressWarnings("serial")
public class ValidationException  extends RuntimeException{
	public ValidationException() {
	}
	
	public ValidationException(String message) {
		super(message);
	}
	
	
}

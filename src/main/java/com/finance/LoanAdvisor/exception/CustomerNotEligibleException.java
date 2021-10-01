package com.finance.LoanAdvisor.exception;

public class CustomerNotEligibleException extends RuntimeException {

public CustomerNotEligibleException() {
		
	}
	
    public CustomerNotEligibleException(String message) {
    	super(message);
	}
}

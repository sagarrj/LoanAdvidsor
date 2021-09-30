package com.finance.LoanAdvisor.config;

public class CustomerNotEligibleException extends RuntimeException {

public CustomerNotEligibleException() {
		
	}
	
    public CustomerNotEligibleException(String message) {
    	super(message);
	}
}

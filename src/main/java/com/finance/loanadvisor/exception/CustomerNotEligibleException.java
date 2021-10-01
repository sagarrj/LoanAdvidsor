package com.finance.loanadvisor.exception;

public class CustomerNotEligibleException extends RuntimeException {

public CustomerNotEligibleException() {
		
	}
	
    public CustomerNotEligibleException(String message) {
    	super(message);
	}
}

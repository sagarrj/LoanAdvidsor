package com.finance.LoanAdvisor.config;

@SuppressWarnings("serial")
public class DataNotFoundException extends RuntimeException {

	public DataNotFoundException() {
		
	}
	
    public DataNotFoundException(String message) {
    	super(message);
	}
	
	
	
}

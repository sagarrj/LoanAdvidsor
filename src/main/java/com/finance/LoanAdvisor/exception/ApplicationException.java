package com.finance.LoanAdvisor.exception;


/**
 * @author priypawa This class contains customized exception
 *         {@link ApplicationException} which extends {@link RuntimeException}. It
 *         contains default and parameterized constructor. It throws an
 *         exception with customized message.
 */
@SuppressWarnings("serial")
public class ApplicationException extends RuntimeException {

	public ApplicationException() {

	}

    public ApplicationException(String message) {
    	super(message);
	}
	
	
	
}

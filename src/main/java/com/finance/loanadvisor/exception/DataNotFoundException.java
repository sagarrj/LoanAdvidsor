package com.finance.loanadvisor.exception;


/**
 * @author priypawa This class contains customized exception
 *         {@link DataNotFoundException} which extends {@link RuntimeException}. It
 *         contains default and parameterized constructor. It throws an
 *         exception with customized message.
 */
@SuppressWarnings("serial")
public class DataNotFoundException extends RuntimeException {

	public DataNotFoundException() {
		
	}
	
    public DataNotFoundException(String message) {
    	super(message);
	}

	public DataNotFoundException(Integer loanId) {
		// TODO Auto-generated constructor stub
	}
	
	
	
}

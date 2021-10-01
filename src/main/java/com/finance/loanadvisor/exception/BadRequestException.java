package com.finance.loanadvisor.exception;
 

/**
 * 
 * @author pkhedkar
 * This class contains customized exception
 *        {@link BadRequestException} which extends {@link RuntimeException}. It
 *         contains default and parameterized constructor. It throws an
 *         exception with customized message.
 *
 */
@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException{

	public BadRequestException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BadRequestException(String message) {
		super(message);
		
		// TODO Auto-generated constructor stub
	}

}

package com.eatza.customer.exception;

/**
 * 
 * @author Vitthal Baburao
 *
 */
public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException() {
		super();
	}

	public CustomerNotFoundException(String msg) {
		super(msg);
	}
}

package com.patterns_playground.proxy;

public class AuthenticationFailedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public AuthenticationFailedException(String errorMessage) {
		super(errorMessage);
	}

}
 	
package com.account.management.exception;

public class NoAccountsLinkedException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoAccountsLinkedException(String message) {
        super(message);
    }

}

package com.account.management.exception;

public class DatabaseAccessException  extends RuntimeException {
	public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

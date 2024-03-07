package com.skilldistillery.petconnectapp.exceptions;

public class UsernameExistsException extends RuntimeException {

	public UsernameExistsException(String message) {
		super(message);
	}

	public UsernameExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}

package com.waio.exceptions;

import org.springframework.http.HttpStatus;

public class AnthenticationException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public AnthenticationException() {
		super();
	}

	public AnthenticationException(final String message, HttpStatus statusCode) {
		super(message);
	}
}
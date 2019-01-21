package com.waio.exceptions;
public class AnthenticationException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public AnthenticationException() {
		super();
	}

	public AnthenticationException(final String message) {
		super(message);
	}
}
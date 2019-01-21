package com.waio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

	private static final long serialVersionUID = -9079454849611061074L;

	public BadRequestException() {
		super();
	}
	public BadRequestException(final String message) {
		super(message);
	}
}
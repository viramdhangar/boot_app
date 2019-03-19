package com.waio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT)
public class ConflictedException extends Exception {

	private static final long serialVersionUID = -9079454849611061074L;

	public ConflictedException() {
		super();
	}
	public ConflictedException(final String message) {
		super(message);
	}
}
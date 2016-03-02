package com.nie.elevator.controller.exception;

/**
 * for HTTP 400 errors
 */
public final class DataFormatException extends RuntimeException {

	private static final long serialVersionUID = -3565690153946119678L;

	public DataFormatException() {
		super();
	}

	public DataFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataFormatException(String message) {
		super(message);
	}

	public DataFormatException(Throwable cause) {
		super(cause);
	}
}
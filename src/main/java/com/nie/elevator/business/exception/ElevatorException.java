package com.nie.elevator.business.exception;

public final class ElevatorException extends Exception {

	private static final long serialVersionUID = -3565690153946119678L;

	public ElevatorException() {
		super();
	}

	public ElevatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElevatorException(String message) {
		super(message);
	}

	public ElevatorException(Throwable cause) {
		super(cause);
	}
}

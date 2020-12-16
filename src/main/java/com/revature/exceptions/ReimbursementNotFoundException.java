package com.revature.exceptions;

public class ReimbursementNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -4091386983867020410L;

	public ReimbursementNotFoundException() {
	}

	public ReimbursementNotFoundException(String message) {
		super(message);
	}

	public ReimbursementNotFoundException(Throwable cause) {
		super(cause);
	}

	public ReimbursementNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimbursementNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

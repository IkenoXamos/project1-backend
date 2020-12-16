package com.revature.exceptions;

public class ReimbursementNotPendingException extends RuntimeException {

	private static final long serialVersionUID = 5225450402019422031L;

	public ReimbursementNotPendingException() {
	}

	public ReimbursementNotPendingException(String message) {
		super(message);
	}

	public ReimbursementNotPendingException(Throwable cause) {
		super(cause);
	}

	public ReimbursementNotPendingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimbursementNotPendingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

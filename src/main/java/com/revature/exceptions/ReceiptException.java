package com.revature.exceptions;

public class ReceiptException extends RuntimeException {

	private static final long serialVersionUID = 4624614255224551457L;

	public ReceiptException() {
	}

	public ReceiptException(String message) {
		super(message);
	}

	public ReceiptException(Throwable cause) {
		super(cause);
	}

	public ReceiptException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReceiptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

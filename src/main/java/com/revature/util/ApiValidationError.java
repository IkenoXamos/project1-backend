package com.revature.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApiValidationError extends ApiSubError {
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;

	public ApiValidationError(String object, String message) {
		this.object = object;
		this.message = message;
	}
	
	public ApiValidationError(String object, String message, String field) {
		this(object, message);
		this.field = field;
	}
	
	public ApiValidationError(String object, String message, String field, Object rejectedValue) {
		this(object, message, field);
		this.rejectedValue = rejectedValue;
	}
}

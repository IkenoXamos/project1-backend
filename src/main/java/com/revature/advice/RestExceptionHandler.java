package com.revature.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.revature.exceptions.AuthorizationException;
import com.revature.exceptions.ReceiptException;
import com.revature.exceptions.ReimbursementNotFoundException;
import com.revature.exceptions.ReimbursementNotPendingException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.util.ApiError;
import com.revature.util.ApiValidationError;

/**
 * This class is responsible for intercepting exceptions thrown throughout the
 * application then building appropriate responses. Provides helpful messages
 * for debugging purposes.
 * 
 * @author MatthewOberlies
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return ResponseEntity.status(apiError.getStatus()).body(apiError);
	}

	/*
	 * Intercepts exceptions that are caused by invalid JSON
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String error = "Malformed JSON Request";

		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	/*
	 * Intercepts exceptions caused by Hibernate Validation
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String error = "Request failed validation";

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex);
		for (FieldError e : ex.getFieldErrors()) {
			apiError.addSubError(new ApiValidationError(e.getObjectName(), e.getDefaultMessage(), e.getField(),
					e.getRejectedValue()));
		}

		return buildResponseEntity(apiError);
	}

	/*
	 * The below handlers intercept custom business logic scenarios
	 */

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {

		String error = "No User Found";

		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, ex));
	}

	@ExceptionHandler(ReimbursementNotFoundException.class)
	public ResponseEntity<Object> handleReimbursementNotFound(ReimbursementNotFoundException ex) {

		String error = "No Reimbursement Found";

		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, ex));
	}

	@ExceptionHandler(ReimbursementNotPendingException.class)
	public ResponseEntity<Object> handleReimbursementNotPending(ReimbursementNotPendingException ex) {

		String error = "Reimbursement is not Pending";

		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	@ExceptionHandler(ReceiptException.class)
	public ResponseEntity<Object> handleReceipt(ReceiptException ex) {

		String error = "An error occured while processing the image";

		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<Object> handleAuth(AuthorizationException ex) {
		
		String error = "Not Authorized";

		return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, error, ex));
	}
}

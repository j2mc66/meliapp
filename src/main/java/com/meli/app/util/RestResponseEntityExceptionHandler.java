package com.meli.app.util;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author James
 * @version: 2021/06/15
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handler exception ConstraintViolationException
	 * @param ex exception to handler
	 * @param request from rest service
	 * @param httpRequest from rest service
	 * @return Object with message exception
	 */
	@ExceptionHandler(value = { ConstraintViolationException.class })
	protected ResponseEntity<Object> handleConflict(ConstraintViolationException ex, WebRequest request,  HttpServletRequest httpRequest) {

		return handleExceptionInternal(ex,
				new RestException(HttpStatus.BAD_REQUEST, ex.getMessage(), httpRequest.getRequestURI()), new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}
}

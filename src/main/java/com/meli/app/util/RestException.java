package com.meli.app.util;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * @author James
 * @version: 2021/06/15
 */
@Getter
public class RestException {
	
	private final LocalDateTime timestamp;
	
	private final int status;
	
	private final String error;
	
	private final String message;
	
	private final String path;
	
	public RestException(HttpStatus httpStatus, String message, String path) {
		this.timestamp = LocalDateTime.now();
		this.status = httpStatus.value();
		this.error = httpStatus.getReasonPhrase();
		this.message = message;
		this.path = path;
	}
	
}

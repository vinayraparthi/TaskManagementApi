package com.vinay.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFound extends RuntimeException{
	
	private String message;
	
	public UserNotFound(String message) {
		super(message);
		this.message = message;
	}

}

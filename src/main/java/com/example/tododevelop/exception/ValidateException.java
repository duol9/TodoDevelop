package com.example.tododevelop.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidateException extends RuntimeException {
	private final HttpStatus httpStatus;
	private final int statusValue;

	public ValidateException(ResponseCode responseCode){
		super(responseCode.getMessage());
		this.httpStatus = responseCode.getHttpStatus();
		this.statusValue = responseCode.getHttpStatus().value();
	}
}

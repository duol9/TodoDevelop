package com.example.tododevelop.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.tododevelop.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ValidateException.class)
	public ResponseEntity<ApiResponse<String>> handleValidateException(ValidateException e) {
		return ResponseEntity.status(e.getHttpStatus())
			.body(ApiResponse.error(e.getStatusValue(), e.getMessage()));
	}
}

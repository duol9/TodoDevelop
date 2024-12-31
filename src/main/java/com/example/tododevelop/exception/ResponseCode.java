package com.example.tododevelop.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
	ID_MISMATCH(HttpStatus.UNAUTHORIZED, "권한이 존재하지 않습니다."),
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
	TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 일정을 찾을 수 없습니다."),
	EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "입력하신 아이디를 찾을 수 없습니다. 다시 확인해주세요."),
	PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다. 다시 입력해주세요.");


	private final HttpStatus httpStatus;
	private final String message;

}


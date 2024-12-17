package com.example.tododevelop.controller;

import com.example.tododevelop.dto.UserResponseDto;
import com.example.tododevelop.dto.UserSignUpRequestDto;
import com.example.tododevelop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 생성(회원가입)
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserSignUpRequestDto signUpRequestDto) {
        UserResponseDto userResponseDto = userService.signUp(signUpRequestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }
}

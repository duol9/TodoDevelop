package com.example.tododevelop.controller;

import com.example.tododevelop.dto.*;
import com.example.tododevelop.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    // 유저 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        // 유저 조회
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);

        // 세션 생성 (세션이 존재하면 반환, 없으면 새로 생성)
        HttpSession httpSession = httpServletRequest.getSession();
        // 세션에 유저 id 저장
        httpSession.setAttribute("userId", loginResponseDto.getId());
        // 쿠키에 세션id 추가
        Cookie cookie = new Cookie("SESSIONID", httpSession.getId());
        // 세션쿠키, 종료 시 로그아웃
        httpServletResponse.addCookie(cookie);

        return new ResponseEntity<>("로그인 성공했습니다.", HttpStatus.OK);
    }

    // 유저 전체 조회
    @GetMapping("/user_list")
    public ResponseEntity<AllUserResponseDto> findAllUser() {
        AllUserResponseDto allUserResponseDto = userService.findAllUser();
        return new ResponseEntity<>(allUserResponseDto, HttpStatus.OK);
    }

    // 유저 단건 조회
    @GetMapping("/user_list/{id}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.findUser(id);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 유저 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> modifyUserInfo(@PathVariable Long id, @RequestBody UserModifyRequestDto modifyRequestDto) {
        UserResponseDto userResponseDto = userService.modifyUserInfo(id, modifyRequestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

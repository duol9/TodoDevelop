package com.example.tododevelop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tododevelop.dto.ApiResponse;
import com.example.tododevelop.dto.user.AllUserResponseDto;
import com.example.tododevelop.dto.user.LoginRequestDto;
import com.example.tododevelop.dto.user.LoginResponseDto;
import com.example.tododevelop.dto.user.SignUpRequestDto;
import com.example.tododevelop.dto.user.UserModifyRequestDto;
import com.example.tododevelop.dto.user.UserResponseDto;
import com.example.tododevelop.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	// 유저 생성(회원가입)
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<String>> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
		userService.signUp(signUpRequestDto);
		return ResponseEntity.ok(
			ApiResponse.success(201, "회원가입 성공", "회원가입 되었습니다."));
	}

	// 유저 로그인
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequestDto loginRequestDto,
		HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
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

		return ResponseEntity.ok(
			ApiResponse.success(200, "로그인 성공", "로그인 되었습니다.")
		);
	}

	// 유저 전체 조회
	@GetMapping("/user_list")
	public ResponseEntity<ApiResponse<AllUserResponseDto>> findAllUser() {
		AllUserResponseDto allUserResponseDto = userService.findAllUser();
		return ResponseEntity.ok(
			ApiResponse.success(200, "유저 전체 조회 성공", allUserResponseDto));
	}

	// 유저 단건 조회
	@GetMapping("/user_list/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>> findUser(@PathVariable Long id) {
		UserResponseDto userResponseDto = userService.findUser(id);
		return ResponseEntity.ok(
			ApiResponse.success(200, "유저 단건 조회 성공", userResponseDto));
	}

	// 유저 정보 수정
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>> modifyUserInfo(@PathVariable Long id,
		@Valid @RequestBody UserModifyRequestDto modifyRequestDto, HttpServletRequest httpServletRequest) {
		// 세션 get. 새로 생성하지는 X
		HttpSession httpSession = httpServletRequest.getSession(false);
		// 세션에서 로그인 한 유저의 id(식별자) get
		Long userId = (Long)httpSession.getAttribute("userId");

		UserResponseDto userResponseDto = userService.modifyUserInfo(id, userId, modifyRequestDto);
		return ResponseEntity.ok(
			ApiResponse.success(200, "유저 정보 수정 성공", userResponseDto));
	}

	// 유저 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id, HttpServletRequest httpServletRequest) {
		// 세션 get. 새로 생성하지는 X
		HttpSession httpSession = httpServletRequest.getSession(false);
		// 세션에서 로그인 한 유저의 id(식별자) get
		Long userId = (Long)httpSession.getAttribute("userId");

		userService.deleteUser(id, userId);

		return ResponseEntity.ok(
			ApiResponse.success(200, "회원탈퇴 성공", null));
	}
}

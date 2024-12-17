package com.example.tododevelop.controller;

import com.example.tododevelop.dto.AllUserResponseDto;
import com.example.tododevelop.dto.UserModifyRequestDto;
import com.example.tododevelop.dto.UserResponseDto;
import com.example.tododevelop.dto.UserSignUpRequestDto;
import com.example.tododevelop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

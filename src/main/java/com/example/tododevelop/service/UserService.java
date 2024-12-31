package com.example.tododevelop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.tododevelop.config.PasswordEncoder;
import com.example.tododevelop.dto.user.AllUserResponseDto;
import com.example.tododevelop.dto.user.LoginRequestDto;
import com.example.tododevelop.dto.user.LoginResponseDto;
import com.example.tododevelop.dto.user.SignUpRequestDto;
import com.example.tododevelop.dto.user.UserModifyRequestDto;
import com.example.tododevelop.dto.user.UserResponseDto;
import com.example.tododevelop.entity.UserEntity;
import com.example.tododevelop.exception.ResponseCode;
import com.example.tododevelop.exception.ValidateException;
import com.example.tododevelop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입(유저생성)
    public void signUp(SignUpRequestDto signUpRequestDto) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new ValidateException(ResponseCode.EMAIL_NOT_FOUND);
        }

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        signUpRequestDto.setPassword(encodePassword);

        // dto -> entity
        UserEntity userEntity = UserEntity.signUpDtoOfUserEntity(signUpRequestDto);
        // 정보 등록
        userRepository.save(userEntity);
    }

    // 로그인
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // 일치하는 이메일이 없으면
        if (!userRepository.existsByEmail(loginRequestDto.getEmail())) {
            throw new ValidateException(ResponseCode.EMAIL_NOT_FOUND);
        }

        // 이메일과 일치하는 유저 정보 get
        UserEntity user = userRepository.findByEmail(loginRequestDto.getEmail());
        // 비밀번호 일치 검사
        if(passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            return new LoginResponseDto(user.getId());
        } else {
            throw new ValidateException(ResponseCode.PASSWORD_MISMATCH);
        }
    }

    // 전체 회원 조회
    public AllUserResponseDto findAllUser() {
        List<UserEntity> findAllUser = userRepository.findAll();
        List<UserResponseDto> usersDto = findAllUser.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
        return new AllUserResponseDto(usersDto);
    }

    // 유저 조회
    public UserResponseDto findUser(Long id){
        UserEntity findUser = findByIdOrElseThrow(id);
        return new UserResponseDto(findUser);
    }

    // 유저 정보 수정
    public UserResponseDto modifyUserInfo(Long id, Long userId, UserModifyRequestDto dto) {
        if (!id.equals(userId)) {
            throw new ValidateException(ResponseCode.ID_MISMATCH);
        }
        UserEntity findUser = findByIdOrElseThrow(id);
        findUser.modifyUserInfo(dto);
        return new UserResponseDto(findUser);
    }

    // 유저 삭제
    public void deleteUser(Long id, Long userId) {
        if (!id.equals(userId)) {
            throw new ValidateException(ResponseCode.ID_MISMATCH);
        }
        UserEntity findUser = findByIdOrElseThrow(id);
        userRepository.delete(findUser);
    }

    // 유저 조회 후 예외처리
    public UserEntity findByIdOrElseThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ValidateException(ResponseCode.ID_MISMATCH));
    }
}

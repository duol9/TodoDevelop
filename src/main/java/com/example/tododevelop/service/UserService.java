package com.example.tododevelop.service;

import com.example.tododevelop.dto.*;
import com.example.tododevelop.entity.UserEntity;
import com.example.tododevelop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 회원가입(유저생성)
    public UserResponseDto signUp(UserSignUpRequestDto signUpRequestDto) {
        UserEntity userEntity = UserEntity.signUpDtoOfUserEntity(signUpRequestDto);
        UserEntity signUpUser = userRepository.save(userEntity);
        return new UserResponseDto(signUpUser);
    }

    // 로그인
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        UserEntity user = userRepository.findIdByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        // 로그인 실패
        if (user.getId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return new LoginResponseDto(user.getId());
    }

    // 전체 회원 조회
    public AllUserResponseDto findAllUser() {
        List<UserEntity> findAllUser = userRepository.findAll();
        return new AllUserResponseDto(findAllUser);
    }

    // 유저 조회
    public UserResponseDto findUser(Long id){
        UserEntity findUser = findByIdOrElseThrow(id);
        return new UserResponseDto(findUser);
    }

    // 유저 정보 수정
    public UserResponseDto modifyUserInfo(Long id, UserModifyRequestDto dto) {
        UserEntity findUser = findByIdOrElseThrow(id);
        findUser.modifyUserInfo(dto);
        return new UserResponseDto(findUser);
    }

    // 유저 삭제
    public void deleteUser(long id) {
        UserEntity findUser = findByIdOrElseThrow(id);
        userRepository.delete(findUser);
    }

    // 유저 조회 후 예외처리
    public UserEntity findByIdOrElseThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id " + id));
    }
}

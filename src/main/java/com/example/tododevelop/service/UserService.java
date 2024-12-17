package com.example.tododevelop.service;

import com.example.tododevelop.dto.AllUserResponseDto;
import com.example.tododevelop.dto.UserResponseDto;
import com.example.tododevelop.dto.UserSignUpRequestDto;
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

    // 유저 조회 후 예외처리
    public UserEntity findByIdOrElseThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id " + id));
    }
}

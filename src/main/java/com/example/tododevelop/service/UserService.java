package com.example.tododevelop.service;

import com.example.tododevelop.config.PasswordEncoder;
import com.example.tododevelop.dto.user.*;
import com.example.tododevelop.entity.UserEntity;
import com.example.tododevelop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입(유저생성)
    public void signUp(SignUpRequestDto signUpRequestDto) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
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
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이 다시 확인해주세요.");
        }

        // 이메일과 일치하는 유저 정보 get
        UserEntity user = userRepository.findByEmail(loginRequestDto.getEmail());
        // 비밀번호 일치 검사
        if(passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            return new LoginResponseDto(user.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
    }

    // 전체 회원 조회
    public AllUserResponseDto findAllUser() {
        List<UserEntity> findAllUser = userRepository.findAll();
        //
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
        UserEntity findUser = findByIdOrElseThrow(id);
        if (findUser.getId() != userId) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인만 삭제 가능합니다.");
        }
        findUser.modifyUserInfo(dto);
        return new UserResponseDto(findUser);
    }

    // 유저 삭제
    public void deleteUser(long id, Long userId) {
        UserEntity findUser = findByIdOrElseThrow(id);
        if (findUser.getId() != userId) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인만 삭제 가능합니다.");
        }
        userRepository.delete(findUser);
    }

    // 유저 조회 후 예외처리
    public UserEntity findByIdOrElseThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id " + id));
    }
}

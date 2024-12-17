package com.example.tododevelop.entity;

import com.example.tododevelop.dto.UserModifyRequestDto;
import com.example.tododevelop.dto.UserSignUpRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String userName;
    private String email;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;

    // 최소한으로 접근을 제한하기 위해 접근 제어자 protected
    protected UserEntity() {}

    private UserEntity(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    // 유저생성requestDto -> UserEntity
    public static UserEntity signUpDtoOfUserEntity(UserSignUpRequestDto dto) {
        return new UserEntity(dto.getUserName(), dto.getEmail());
    }

    // 유저 정보 수정
    public void modifyUserInfo(UserModifyRequestDto dto){
        if (dto.getEmail() != null) {
            this.email = dto.getEmail();
        }
        if (dto.getUserName() != null) {
            this.userName = dto.getUserName();
        }
    }
}

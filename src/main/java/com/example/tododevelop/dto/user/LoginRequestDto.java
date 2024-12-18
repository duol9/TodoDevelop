package com.example.tododevelop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {
    @Email
    @NotBlank(message = "email을 입력해주세요.")
    private String email;
    @NotBlank(message = "password를 입력해주세요.")
    private String password;
}

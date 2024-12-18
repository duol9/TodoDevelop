package com.example.tododevelop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {
    @Size(max = 10, message = "이름은 10자를 초과할 수 없습니다.")
    @NotBlank
    private String userName;

    @Email
    private String email;

    @Setter
    @NotBlank
    private String password;
}

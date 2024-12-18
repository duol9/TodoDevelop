package com.example.tododevelop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserModifyRequestDto {
    @Size(max = 10, message = "이름은 10자를 초과할 수 없습니다.")
    private String userName;
    @Email
    private String email;
}

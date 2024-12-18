package com.example.tododevelop.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoCreateRequestDto {
    @Size(max = 20, message = "제목은 20자를 초과할 수 없습니다.")
    private String title;
    private String contents;
}

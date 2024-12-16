package com.example.tododevelop.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponseDto {
    private Long id;
    private String userName;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public TodoResponseDto(String userName, String title, String contents){
        this.userName = userName;
        this.title = title;
        this.contents = contents;
    }
}

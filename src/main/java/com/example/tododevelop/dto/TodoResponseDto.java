package com.example.tododevelop.dto;

import com.example.tododevelop.entity.TodoEntity;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoResponseDto {
    private Long id;
    private String userName;
    private String title;
    private String contents;
    private LocalDate createdAt;
    private LocalDate modifiedAt;

    public TodoResponseDto(TodoEntity todoEntity){
        this.id = todoEntity.getId();
        this.userName = todoEntity.getUserName();
        this.title = todoEntity.getTitle();
        this.contents = todoEntity.getContents();
        this.createdAt = todoEntity.getCreatAt();
        this.modifiedAt = todoEntity.getModifiedAt();
    }
}

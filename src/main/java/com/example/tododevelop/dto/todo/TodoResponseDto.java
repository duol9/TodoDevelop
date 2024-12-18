package com.example.tododevelop.dto.todo;

import com.example.tododevelop.entity.TodoEntity;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoResponseDto {
    private String name;
    private String title;
    private String contents;
    private LocalDate createdAt;
    private LocalDate modifiedAt;

    public TodoResponseDto(TodoEntity todoEntity){
        this.name = todoEntity.getUserEntity().getUserName();
        this.title = todoEntity.getTitle();
        this.contents = todoEntity.getContents();
        this.createdAt = todoEntity.getCreatedAt();
        this.modifiedAt = todoEntity.getModifiedAt();
    }
}

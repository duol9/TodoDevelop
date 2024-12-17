package com.example.tododevelop.dto;

import com.example.tododevelop.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllTodoResponseDto {
    private List<TodoEntity> allTodos;
}

package com.example.tododevelop.dto;

import com.example.tododevelop.entity.TodoEntity;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FindAllTodoResponseDto {
    private List<TodoEntity> allTodos;
}

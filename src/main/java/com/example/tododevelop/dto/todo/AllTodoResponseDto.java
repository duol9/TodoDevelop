package com.example.tododevelop.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllTodoResponseDto {
    private List<TodoResponseDto> allTodos;
}

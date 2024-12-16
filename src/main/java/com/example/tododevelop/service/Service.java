package com.example.tododevelop.service;

import com.example.tododevelop.dto.TodoRequestDto;
import com.example.tododevelop.dto.TodoResponseDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface Service {
    TodoResponseDto saveTodo(TodoRequestDto dto);
    ResponseEntity<List<TodoResponseDto>> findAllTodos(String name, LocalDate editDate);
    TodoResponseDto findTodoById(Long id);
    TodoResponseDto updateTodo(Long id, String name, String pw, String task);
    void deleteTodo(Long id, String pw);
}

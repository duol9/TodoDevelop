package com.example.tododevelop.controller;

import com.example.tododevelop.dto.TodoRequestDto;
import com.example.tododevelop.dto.TodoResponseDto;
import com.example.tododevelop.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto dto) {
        TodoResponseDto todoResponseDto = todoService.createTodo(dto);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.CREATED);
    }
}

package com.example.tododevelop.controller;

import com.example.tododevelop.dto.FindAllTodoResponseDto;
import com.example.tododevelop.dto.TodoRequestDto;
import com.example.tododevelop.dto.TodoResponseDto;
import com.example.tododevelop.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findById(@PathVariable Long id){
        TodoResponseDto todoResponseDto = todoService.findById(id);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<FindAllTodoResponseDto> findAllTodos(){
        FindAllTodoResponseDto allTodosDto = todoService.findAllTodos();
        return new ResponseEntity<>(allTodosDto, HttpStatus.FOUND);
    }
}

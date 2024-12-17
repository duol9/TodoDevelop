package com.example.tododevelop.controller;

import com.example.tododevelop.dto.*;
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

    // 할일생성
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoCreateRequestDto dto) {
        TodoResponseDto todoResponseDto = todoService.createTodo(dto);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.CREATED);
    }

    // 할일 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findById(@PathVariable Long id){
        TodoResponseDto todoResponseDto = todoService.findById(id);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.FOUND);
    }

    // 할일 전체 조회
    @GetMapping
    public ResponseEntity<FindAllTodoResponseDto> findAllTodos(){
        FindAllTodoResponseDto allTodosDto = todoService.findAllTodos();
        return new ResponseEntity<>(allTodosDto, HttpStatus.FOUND);
    }

    // 할일 수정
    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDto> modifyTodo(@PathVariable Long id, @RequestBody TodoModifyRequestDto dto) {
        TodoResponseDto todoResponseDto = todoService.modifyTodo(id, dto);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
    }

    // 할일 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

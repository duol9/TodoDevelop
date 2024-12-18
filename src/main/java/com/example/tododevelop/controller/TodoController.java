package com.example.tododevelop.controller;

import com.example.tododevelop.dto.todo.AllTodoResponseDto;
import com.example.tododevelop.dto.todo.TodoCreateRequestDto;
import com.example.tododevelop.dto.todo.TodoModifyRequestDto;
import com.example.tododevelop.dto.todo.TodoResponseDto;
import com.example.tododevelop.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoCreateRequestDto dto, HttpServletRequest httpServletRequest) {
        // 세션 get. 새로 생성하지는 X
        HttpSession httpSession = httpServletRequest.getSession(false);
        // 세션에서 로그인 한 유저의 id(식별자) get
        Long userId = (Long) httpSession.getAttribute("userId");

        TodoResponseDto todoResponseDto = todoService.createTodo(dto, userId);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.CREATED);
    }

    // 할일 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findById(@PathVariable Long id){
        TodoResponseDto todoResponseDto = todoService.findById(id);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
    }

    // 할일 전체 조회
    @GetMapping
    public ResponseEntity<AllTodoResponseDto> findAllTodos(){
        AllTodoResponseDto allTodosDto = todoService.findAllTodos();
        return new ResponseEntity<>(allTodosDto, HttpStatus.OK);
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

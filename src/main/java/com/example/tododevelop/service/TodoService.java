package com.example.tododevelop.service;

import com.example.tododevelop.dto.TodoRequestDto;
import com.example.tododevelop.dto.TodoResponseDto;
import com.example.tododevelop.entity.TodoEntity;
import com.example.tododevelop.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 필수 필드로만 이루어진 생성자
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto dto){
        TodoEntity todoEntity = new TodoEntity(dto.getUserName(), dto.getTitle(), dto.getContents());
        TodoEntity savedTodo = todoRepository.save(todoEntity);
        return new TodoResponseDto(savedTodo.getUserName(), savedTodo.getTitle(), savedTodo.getContents());
    }
}

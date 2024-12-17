package com.example.tododevelop.service;

import com.example.tododevelop.dto.FindAllTodoResponseDto;
import com.example.tododevelop.dto.TodoRequestDto;
import com.example.tododevelop.dto.TodoResponseDto;
import com.example.tododevelop.entity.TodoEntity;
import com.example.tododevelop.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // 필수 필드로만 이루어진 생성자
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto dto){
        TodoEntity todoEntity = new TodoEntity(dto.getUserName(), dto.getTitle(), dto.getContents());
        TodoEntity savedTodo = todoRepository.save(todoEntity);
        return new TodoResponseDto(savedTodo.getId(), savedTodo.getUserName(), savedTodo.getTitle(), savedTodo.getContents());
    }

    public TodoResponseDto findById(Long id) {
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(id);
        optionalTodoEntity.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id " + id));
        TodoEntity findTodo = optionalTodoEntity.get();
        return new TodoResponseDto(findTodo.getId(), findTodo.getUserName(), findTodo.getTitle(), findTodo.getContents());
    }

    public FindAllTodoResponseDto findAllTodos(){
        List<TodoEntity> findAllTodo = todoRepository.findAll();
        return new FindAllTodoResponseDto(findAllTodo);
    }

    // 할 일 수정
    @Transactional
    public TodoResponseDto modifyTodo(Long id, TodoRequestDto dto) {
        Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(id);
        optionalTodoEntity.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id " + id));
        TodoEntity findTodo = optionalTodoEntity.get();
        findTodo.modifyTodo(dto.getContents());
        return new TodoResponseDto(findTodo.getId(), findTodo.getUserName(), findTodo.getTitle(), findTodo.getContents());
    }


}

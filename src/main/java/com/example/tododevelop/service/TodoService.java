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

@Service
@RequiredArgsConstructor // 필수 필드로만 이루어진 생성자
public class TodoService {
    private final TodoRepository todoRepository;

    // 할 일 생성
    public TodoResponseDto createTodo(TodoRequestDto dto){
        TodoEntity todoEntity = new TodoEntity(dto.getUserName(), dto.getTitle(), dto.getContents());
        TodoEntity savedTodo = todoRepository.save(todoEntity);
        return new TodoResponseDto(savedTodo);
    }

    // 할일 단건 조회
    public TodoResponseDto findById(Long id) {
        TodoEntity findTodo = findByIdOrElseThrow(id);
        return new TodoResponseDto(findTodo);
    }

    // 할일 전체 조회
    public FindAllTodoResponseDto findAllTodos(){
        List<TodoEntity> findAllTodo = todoRepository.findAll();
        return new FindAllTodoResponseDto(findAllTodo);
    }

    // 할 일 수정
    @Transactional
    public TodoResponseDto modifyTodo(Long id, TodoRequestDto dto) {
        TodoEntity findTodo = findByIdOrElseThrow(id);
        findTodo.modifyTodo(dto.getContents());
        return new TodoResponseDto(findTodo);
    }

    // 할 일 삭제
    public void deleteTodo(Long id) {
        TodoEntity findTodo = findByIdOrElseThrow(id);
        todoRepository.delete(findTodo);
    }

    // 일정 조회 예외처리
    public TodoEntity findByIdOrElseThrow(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exist id " + id));
    }
}

package com.example.tododevelop.service;

import com.example.tododevelop.dto.todo.AllTodoResponseDto;
import com.example.tododevelop.dto.todo.TodoCreateRequestDto;
import com.example.tododevelop.dto.todo.TodoModifyRequestDto;
import com.example.tododevelop.dto.todo.TodoResponseDto;
import com.example.tododevelop.entity.TodoEntity;
import com.example.tododevelop.entity.UserEntity;
import com.example.tododevelop.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // 필수 필드로만 이루어진 생성자
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserService userService;

    // 할 일 생성
    public TodoResponseDto createTodo(TodoCreateRequestDto dto, Long userId){
        TodoEntity todoEntity = TodoEntity.createDtoOfTodoEntity(dto);

        // 할 일을 생성한 유저의 정보 저장
        UserEntity userEntity = userService.findByIdOrElseThrow(userId);
        todoEntity.setUserEntity(userEntity);

        TodoEntity savedTodo = todoRepository.save(todoEntity);

        return new TodoResponseDto(savedTodo);
    }

    // 할일 단건 조회
    public TodoResponseDto findById(Long id) {
        TodoEntity findTodo = findByIdOrElseThrow(id);
        return new TodoResponseDto(findTodo);
    }

    // 할일 전체 조회
    public AllTodoResponseDto findAllTodos(){
        List<TodoEntity> findAllTodo = todoRepository.findAll();
        List<TodoResponseDto> findTodoDtos = findAllTodo.stream()
                .map(TodoResponseDto::new)
                .collect(Collectors.toList());
        return new AllTodoResponseDto(findTodoDtos);
    }

    // 할 일 수정
    @Transactional
    public TodoResponseDto modifyTodo(Long id, Long userId, TodoModifyRequestDto dto) {
        TodoEntity findTodo = findByIdOrElseThrow(id);
        if (findTodo.getUserEntity().getId() != userId) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "작성자만 수정 가능합니다.");
        }
        findTodo.modifyTodo(dto.getContents());
        return new TodoResponseDto(findTodo);
    }

    // 할 일 삭제
    public void deleteTodo(Long id, Long userId) {
        TodoEntity findTodo = findByIdOrElseThrow(id);
        if (findTodo.getUserEntity().getId() != userId) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "작성자만 삭제 가능합니다.");
        }
        todoRepository.delete(findTodo);
    }

    // 조회 후 존재 여부 검사
   public TodoEntity findByIdOrElseThrow(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정을 찾을 수 없습니다."));
    }
}

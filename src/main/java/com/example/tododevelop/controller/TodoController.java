package com.example.tododevelop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tododevelop.dto.ApiResponse;
import com.example.tododevelop.dto.todo.AllTodoResponseDto;
import com.example.tododevelop.dto.todo.TodoCreateRequestDto;
import com.example.tododevelop.dto.todo.TodoModifyRequestDto;
import com.example.tododevelop.dto.todo.TodoResponseDto;
import com.example.tododevelop.service.TodoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
	private final TodoService todoService;

	// 할일생성
	@PostMapping
	public ResponseEntity<ApiResponse<TodoResponseDto>> createTodo(@RequestBody TodoCreateRequestDto dto,
		HttpServletRequest httpServletRequest) {
		// 세션 get. 새로 생성하지는 X
		HttpSession httpSession = httpServletRequest.getSession(false);
		// 세션에서 로그인 한 유저의 id(식별자) get
		Long userId = (Long)httpSession.getAttribute("userId");

		TodoResponseDto todoResponseDto = todoService.createTodo(dto, userId);
		return ResponseEntity.ok(
			ApiResponse.success(201, "일정 등록 성공", todoResponseDto));
	}

	// 할일 단건 조회
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<TodoResponseDto>> findById(@PathVariable Long id) {
		TodoResponseDto todoResponseDto = todoService.findById(id);
		return ResponseEntity.ok(
			ApiResponse.success(200, "일정 단건 조회 성공", todoResponseDto));
	}

	// 할일 전체 조회
	@GetMapping
	public ResponseEntity<ApiResponse<AllTodoResponseDto>> findAllTodos() {
		AllTodoResponseDto allTodosDto = todoService.findAllTodos();
		return ResponseEntity.ok(
			ApiResponse.success(200, "일정 전체 조회 성공", allTodosDto));
	}

	// 할일 수정
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<TodoResponseDto>> modifyTodo(@PathVariable Long id,
		@RequestBody TodoModifyRequestDto dto, HttpServletRequest httpServletRequest) {
		// 세션 get. 새로 생성하지는 X
		HttpSession httpSession = httpServletRequest.getSession(false);
		// 세션에서 로그인 한 유저의 id(식별자) get
		Long userId = (Long)httpSession.getAttribute("userId");

		TodoResponseDto todoResponseDto = todoService.modifyTodo(id, userId, dto);
		return ResponseEntity.ok(
			ApiResponse.success(200, "일정 수정 성공", todoResponseDto));
	}

	// 할일 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteTodo(@PathVariable Long id, HttpServletRequest httpServletRequest) {
		// 세션 get. 새로 생성하지는 X
		HttpSession httpSession = httpServletRequest.getSession(false);
		// 세션에서 로그인 한 유저의 id(식별자) get
		Long userId = (Long)httpSession.getAttribute("userId");

		todoService.deleteTodo(id, userId);
		return ResponseEntity.ok(
			ApiResponse.success(200, "일정 삭제 성공", null));
	}
}

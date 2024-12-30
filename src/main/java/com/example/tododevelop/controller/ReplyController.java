package com.example.tododevelop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tododevelop.dto.ApiResponse;
import com.example.tododevelop.dto.reply.AllReplyResponseDto;
import com.example.tododevelop.dto.reply.ReplyRequestDto;
import com.example.tododevelop.dto.reply.ReplyResponseDto;
import com.example.tododevelop.service.ReplyService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class ReplyController {

	private final ReplyService replyService;

	// 댓글 작성
	@PostMapping("/{todoId}/reply")
	public ResponseEntity<ApiResponse<ReplyResponseDto>> writeReply(@PathVariable Long todoId,
		@RequestBody ReplyRequestDto dto, HttpServletRequest httpServletRequest) {
		// 세션 get. 새로 생성하지는 X
		HttpSession httpSession = httpServletRequest.getSession(false);
		// 세션에서 로그인 한 유저의 id(식별자) get
		Long userId = (Long)httpSession.getAttribute("userId");

		ReplyResponseDto replyResponseDto = replyService.writeReply(dto, todoId, userId);
		return ResponseEntity.ok(
			ApiResponse.success(200, "댓글 등록 성공", replyResponseDto));
	}

	// 댓글 조회
	@GetMapping("/{todoId}/reply")
	public ResponseEntity<ApiResponse<AllReplyResponseDto>> ReplysByTodo(@PathVariable Long todoId) {
		AllReplyResponseDto allReplyResponseDto = replyService.findReplys(todoId);
		return ResponseEntity.ok(
			ApiResponse.success(200, "댓글 조회 성공", allReplyResponseDto));
	}

	// 댓글 수정
	@PutMapping("/{todoId}/reply/{replyId}")
	public ResponseEntity<ApiResponse<ReplyResponseDto>> modifyReply(@PathVariable Long todoId,
		@PathVariable Long replyId, @RequestBody ReplyRequestDto dto, HttpServletRequest httpServletRequest) {
		// 세션 get. 새로 생성하지는 X
		HttpSession httpSession = httpServletRequest.getSession(false);
		// 세션에서 로그인 한 유저의 id(식별자) get
		Long userId = (Long)httpSession.getAttribute("userId");

		ReplyResponseDto replyResponseDto = replyService.modifyReply(dto, replyId, todoId, userId);

		return ResponseEntity.ok(
			ApiResponse.success(200, "댓글 수정 성공", replyResponseDto));
	}

	// 댓글 삭제
	@DeleteMapping("/{todoId}/reply/{replyId}")
	public ResponseEntity<ApiResponse<Void>> deleteReply(@PathVariable Long todoId, @PathVariable Long replyId,
		@RequestBody ReplyRequestDto dto, HttpServletRequest httpServletRequest) {
		// 세션 get. 새로 생성하지는 X
		HttpSession httpSession = httpServletRequest.getSession(false);
		// 세션에서 로그인 한 유저의 id(식별자) get
		Long userId = (Long)httpSession.getAttribute("userId");

		replyService.deleteReply(dto, replyId, todoId, userId);

		return ResponseEntity.ok(
			ApiResponse.success(200, "댓글 삭제 성공", null));
	}
}

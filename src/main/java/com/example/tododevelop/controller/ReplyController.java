package com.example.tododevelop.controller;

import com.example.tododevelop.dto.reply.AllReplyResponseDto;
import com.example.tododevelop.dto.reply.ReplyResponseDto;
import com.example.tododevelop.dto.reply.ReplyRequestDto;
import com.example.tododevelop.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    // 댓글 작성
    @PostMapping("/{todoId}/reply")
    public ResponseEntity<ReplyResponseDto> writeReply (@PathVariable Long todoId, @RequestBody ReplyRequestDto dto, HttpServletRequest httpServletRequest) {
        // 세션 get. 새로 생성하지는 X
        HttpSession httpSession = httpServletRequest.getSession(false);
        // 세션에서 로그인 한 유저의 id(식별자) get
        Long userId = (Long) httpSession.getAttribute("userId");

        ReplyResponseDto replyResponseDto = replyService.writeReply(dto, todoId, userId);
        return new ResponseEntity<>(replyResponseDto, HttpStatus.OK);
    }

    // 댓글 조회
    @GetMapping("/{todoId}/reply")
    public ResponseEntity<AllReplyResponseDto> ReplysByTodo (@PathVariable Long todoId) {
        AllReplyResponseDto allReplyResponseDto = replyService.findReplys(todoId);
        return new ResponseEntity<>(allReplyResponseDto, HttpStatus.FOUND);
    }

    // 댓글 수정
    @PutMapping("/{todoId}/reply/{replyId}")
    public ResponseEntity<ReplyResponseDto> modifyReply (@PathVariable Long todoId, @PathVariable Long replyId, @RequestBody ReplyRequestDto dto, HttpServletRequest httpServletRequest) {
        // 세션 get. 새로 생성하지는 X
        HttpSession httpSession = httpServletRequest.getSession(false);
        // 세션에서 로그인 한 유저의 id(식별자) get
        Long userId = (Long) httpSession.getAttribute("userId");

        ReplyResponseDto replyResponseDto = replyService.modifyReply(dto, replyId, todoId, userId);

        return new ResponseEntity<>(replyResponseDto, HttpStatus.OK);
    }
}

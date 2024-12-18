package com.example.tododevelop.controller;

import com.example.tododevelop.dto.reply.ReplyResponseDto;
import com.example.tododevelop.dto.reply.WriteReplyRequestDto;
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

    @PostMapping("/{todoId}/reply")
    public ResponseEntity<ReplyResponseDto> writeReply (@PathVariable Long todoId, @RequestBody WriteReplyRequestDto dto, HttpServletRequest httpServletRequest) {
        // 세션 get. 새로 생성하지는 X
        HttpSession httpSession = httpServletRequest.getSession(false);
        // 세션에서 로그인 한 유저의 id(식별자) get
        Long userId = (Long) httpSession.getAttribute("userId");

        ReplyResponseDto replyResponseDto = replyService.writeReply(dto, todoId, userId);
        return new ResponseEntity<>(replyResponseDto, HttpStatus.OK);
    }
}

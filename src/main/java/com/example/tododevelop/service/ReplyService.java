package com.example.tododevelop.service;

import com.example.tododevelop.dto.reply.ReplyResponseDto;
import com.example.tododevelop.dto.reply.WriteReplyRequestDto;
import com.example.tododevelop.entity.ReplyEntity;
import com.example.tododevelop.entity.TodoEntity;
import com.example.tododevelop.entity.UserEntity;
import com.example.tododevelop.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final UserService userService;
    private final TodoService todoService;

    public ReplyResponseDto writeReply(WriteReplyRequestDto requestDto, Long todoId, Long userId) {
        ReplyEntity replyEntity = ReplyEntity.replyResponseDtoOfReplyEntity(requestDto);

        //댓글 단 일정 정보
        TodoEntity todoEntity = todoService.findByIdOrElseThrow(todoId);
        replyEntity.setTodoEntity(todoEntity);
        // 댓글 단 유저 정보
        UserEntity userEntity = userService.findByIdOrElseThrow(userId);
        replyEntity.setUserEntity(userEntity);

        ReplyEntity writeReplyEntity = replyRepository.save(replyEntity);

        return new ReplyResponseDto(writeReplyEntity);
    }
}

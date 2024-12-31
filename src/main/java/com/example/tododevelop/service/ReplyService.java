package com.example.tododevelop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.tododevelop.dto.reply.AllReplyResponseDto;
import com.example.tododevelop.dto.reply.ReplyRequestDto;
import com.example.tododevelop.dto.reply.ReplyResponseDto;
import com.example.tododevelop.entity.ReplyEntity;
import com.example.tododevelop.entity.TodoEntity;
import com.example.tododevelop.entity.UserEntity;
import com.example.tododevelop.exception.ResponseCode;
import com.example.tododevelop.exception.ValidateException;
import com.example.tododevelop.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final UserService userService;
    private final TodoService todoService;

    // 댓글 작성
    public ReplyResponseDto writeReply(ReplyRequestDto requestDto, Long todoId, Long userId) {
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

    // 댓글 조회
    public AllReplyResponseDto findReplys(Long todoId) {
        List<ReplyEntity> replys = replyRepository.findAllByTodoEntity_Id(todoId);
        List<ReplyResponseDto> replysDto = replys.stream()
                .map(ReplyResponseDto::new)
                .collect(Collectors.toList());
        return new AllReplyResponseDto(replysDto);
    }

    // 댓글 수정
    public ReplyResponseDto modifyReply(ReplyRequestDto requestDto, Long replyId, Long todoId, Long userId){
        TodoEntity todoEntity = todoService.findByIdOrElseThrow(todoId);
        ReplyEntity findReplyEntity = findByIdOrElseThrow(replyId);
        if (!findReplyEntity.getUserEntity().getId().equals(userId)) {
            throw new ValidateException(ResponseCode.ID_MISMATCH);
        }
        findReplyEntity.modifyReply(requestDto.getConment());

        return new ReplyResponseDto(findReplyEntity);
    }

    // 댓글 삭제
    public void deleteReply(ReplyRequestDto requestDto, Long replyId, Long todoId, Long userId){
        TodoEntity todoEntity = todoService.findByIdOrElseThrow(todoId);
        ReplyEntity findReplyEntity = findByIdOrElseThrow(replyId);
        if (!findReplyEntity.getUserEntity().getId().equals(userId)) {
            throw new ValidateException(ResponseCode.ID_MISMATCH);
        }
        replyRepository.delete(findReplyEntity);
    }

    // 댓글 조회 후 예외처리
    public ReplyEntity findByIdOrElseThrow(Long id) {
        return replyRepository.findById(id)
                .orElseThrow(() -> new ValidateException(ResponseCode.COMMENT_NOT_FOUND));
    }
}

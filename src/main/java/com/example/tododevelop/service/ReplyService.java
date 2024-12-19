package com.example.tododevelop.service;

import com.example.tododevelop.dto.reply.AllReplyResponseDto;
import com.example.tododevelop.dto.reply.ReplyResponseDto;
import com.example.tododevelop.dto.reply.ReplyRequestDto;
import com.example.tododevelop.entity.ReplyEntity;
import com.example.tododevelop.entity.TodoEntity;
import com.example.tododevelop.entity.UserEntity;
import com.example.tododevelop.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인만 수정 가능합니다.");
        }
        findReplyEntity.modifyReply(requestDto.getConment());

        return new ReplyResponseDto(findReplyEntity);
    }

    // 댓글 조회 후 예외처리
    public ReplyEntity findByIdOrElseThrow(Long id) {
        return replyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않은 댓글입니다. "));
    }
}

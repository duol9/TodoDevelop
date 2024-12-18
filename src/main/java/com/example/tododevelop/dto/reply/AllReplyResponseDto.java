package com.example.tododevelop.dto.reply;

import com.example.tododevelop.entity.ReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllReplyResponseDto {
    private List<ReplyEntity> replyEntityList;
}

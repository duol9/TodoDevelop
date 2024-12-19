package com.example.tododevelop.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllReplyResponseDto {
    private List<ReplyResponseDto> replyEntityList;
}

package com.example.tododevelop.dto.reply;

import com.example.tododevelop.entity.ReplyEntity;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReplyResponseDto {
    private String userName;
    private String todoTitle;
    private String comment;
    private LocalDate createdAt;
    private LocalDate modifiedAt;

    public ReplyResponseDto(ReplyEntity replyEntity) {
        this.userName = replyEntity.getUserEntity().getUserName();
        this.todoTitle = replyEntity.getTodoEntity().getTitle();
        this.comment = replyEntity.getComment();
        this.createdAt = replyEntity.getCreatedAt();
        this.modifiedAt = replyEntity.getModifiedAt();
    }
}

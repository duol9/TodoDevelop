package com.example.tododevelop.entity;

import com.example.tododevelop.dto.reply.WriteReplyRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "reply")
public class ReplyEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_Id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_user_id"))
    private UserEntity userEntity;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "todos_Id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_todos_id"))
    private TodoEntity todoEntity;

    protected ReplyEntity() {}

    private ReplyEntity(String comment) {
        this.comment = comment;
    }
    public static ReplyEntity replyResponseDtoOfReplyEntity(WriteReplyRequestDto dto){
        return new ReplyEntity(dto.getComment());
    }

    @Override
    public LocalDate getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    public LocalDate getModifiedAt() {
        return super.getModifiedAt();
    }
}

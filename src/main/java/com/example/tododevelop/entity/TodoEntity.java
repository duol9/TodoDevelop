package com.example.tododevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "todos")
public class TodoEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 10)
    private String userName;
    private String title;

    @Column(columnDefinition = "longtext")
    private String contents;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id") // 외래키 설정, User테이블의 id 참조
    private UserEntity userEntity;

    // 기본 생성자
    public TodoEntity() {
    }

    public TodoEntity(String userName, String title, String contents) {
        this.userName = userName;
        this.title = title;
        this.contents = contents;
    }

    @Override
    public LocalDate getCreatAt() {
        return super.getCreatAt();
    }

    @Override
    public LocalDate getModifiedAt() {
        return super.getModifiedAt();
    }

    // JPA를 사용해 할일 수정(setter)
    public void modifyTodo(String contents){
        this.contents = contents;
    }
}

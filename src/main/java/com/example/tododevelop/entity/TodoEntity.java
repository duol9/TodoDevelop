package com.example.tododevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "todos")
public class TodoEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userName;
    private String title;

    @Column(columnDefinition = "longtext")
    private String contents;

    // 기본 생성자
    public TodoEntity() {
    }

    public TodoEntity(String userName, String title, String contents) {
        this.userName = userName;
        this.title = title;
        this.contents = contents;
    }

    // JPA를 사용해 할일 수정(setter)
    public void modifyTodo(String contents){
        this.contents = contents;
    }
}

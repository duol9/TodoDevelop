package com.example.tododevelop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "todos")
public class TodoEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String title;

    @Column(columnDefinition = "longtext")
    private String contents;
}

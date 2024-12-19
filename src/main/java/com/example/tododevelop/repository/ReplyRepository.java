package com.example.tododevelop.repository;

import com.example.tododevelop.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    List<ReplyEntity> findAllByTodoEntity_Id (Long todoId);
}

package com.example.myhome.repository;

import com.example.myhome.model.Board;
import com.example.myhome.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByBoard_Id(Long board_id);
}

package com.example.myhome.service;

import com.example.myhome.model.Board;
import com.example.myhome.model.Comment;
import com.example.myhome.model.User;
import com.example.myhome.repository.BoardRepository;
import com.example.myhome.repository.CommentRepository;
import com.example.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment save(String username,Long id, Comment comment){
        User user = userRepository.findByUsername(username);
        Optional<Board> board = boardRepository.findById(id);

        comment.setUser(user);
        comment.setBoard(board.get());

        return commentRepository.save(comment);
    }
}

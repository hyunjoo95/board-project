package com.example.myhome.controller;

import com.example.myhome.model.Board;
import com.example.myhome.model.Comment;
import com.example.myhome.repository.BoardRepository;
import com.example.myhome.repository.CommentRepository;
import com.example.myhome.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/regist")
    public String commentForm(@Valid Comment comment, @RequestParam(required = true) Long id, Authentication authentication){
        String username = authentication.getName();
        commentService.save(username,id,comment);
        return "redirect:/board/form";
    }
}

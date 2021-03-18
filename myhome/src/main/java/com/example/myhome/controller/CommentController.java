package com.example.myhome.controller;

import com.example.myhome.model.Board;
import com.example.myhome.model.Comment;
import com.example.myhome.repository.BoardRepository;
import com.example.myhome.repository.CommentRepository;
import com.example.myhome.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;


    @PostMapping("/regist")
    public String commentForm(@Valid Comment comment, @RequestParam(required = true) Long id,
                              Authentication authentication,RedirectAttributes redirect){
        String username = authentication.getName();
        commentService.save(username,id,comment);
        redirect.addAttribute("id",id);

        return "redirect:/board/form";
    }

    @PostMapping("/delete")
    public String commentDelete(@RequestParam(required = true) Long id,@RequestParam(required = true) Long board_id,RedirectAttributes redirect){
        commentRepository.deleteById(id);

        redirect.addAttribute("id",board_id);

        return "redirect:/board/form";
    }
}

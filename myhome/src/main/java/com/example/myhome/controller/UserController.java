package com.example.myhome.controller;


import com.example.myhome.model.Board;
import com.example.myhome.model.User;
import com.example.myhome.repository.BoardRepository;
import com.example.myhome.repository.UserRepository;
import com.example.myhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm(){
        return "user/login";
    }


    @GetMapping("/join")
    public String joinForm(){
        return "user/join";
    }

    @PostMapping("/join")
    public String join(User user){
        userService.save(user);
        return "redirect:/";
    }
}

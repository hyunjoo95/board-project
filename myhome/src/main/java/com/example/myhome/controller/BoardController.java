package com.example.myhome.controller;


import com.example.myhome.model.Board;
import com.example.myhome.repository.BoardRepository;
import com.example.myhome.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    // 리스트 출력
    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable){
        Page<Board> boards = boardRepository.findAll(pageable);
        int startPage = Math.max(1,boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber() + 4);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    // 내용 입력 폼
    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null){
            // 신규 폼
            model.addAttribute("board", new Board());
        }else{
            // 수정 시 내용 가져오기
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }

        return "board/form";
    }
    @PostMapping("/form/save")
    public String saveForm(@Valid Board board, BindingResult bindingResult){
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }

        boardRepository.save(board);
        return "redirect:/board/list";
    }

    @PostMapping("/form/delete")
    public String deleteForm(@RequestParam(required = true) Long id){
        boardRepository.deleteById(id);
        return "redirect:/board/list";
    }
}

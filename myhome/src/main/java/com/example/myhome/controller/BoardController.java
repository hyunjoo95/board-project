package com.example.myhome.controller;


import com.example.myhome.model.Board;
import com.example.myhome.model.Comment;
import com.example.myhome.repository.BoardRepository;
import com.example.myhome.repository.CommentRepository;
import com.example.myhome.service.BoardService;
import com.example.myhome.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardValidator boardValidator;

    @Autowired
    private BoardService boardService;

    // 리스트 출력
    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText){
        //Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);

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
            List comment = commentRepository.findByBoard_Id(id);
            model.addAttribute("board", board);
            model.addAttribute("comments", comment);
        }

        return "board/form";
    }

    // 게시글 보여주기
    @GetMapping("/view")
    public String view(Model model, @RequestParam(required = false) Long id){

        // 수정 시 내용 가져오기
        Board board = boardRepository.findById(id).orElse(null);
        List comment = commentRepository.findByBoard_Id(id);
        model.addAttribute("board", board);
        model.addAttribute("comments", comment);

        return "board/view";
    }

    @PostMapping("/form")
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication){
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        String username = authentication.getName();
        boardService.save(username,board);

        return "redirect:/board/list";
    }

    @PostMapping("/form/delete")
    public String deleteForm(@RequestParam(required = true) Long id){
        boardRepository.deleteById(id);

        return "redirect:/board/list";
    }

}

package com.hee.board.controller;

import com.hee.board.entity.Board;
import com.hee.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm(){
        return "boardWrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board){ //entity형식을 그대로 가져올 수 있다.
        boardService.write(board);
        return "";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){
        model.addAttribute("list", boardService.boardList());//model 'list'에 게시글 목록을 담아서 반환시킴
        return "boardList";
    }

    @GetMapping("/board/detail") //localhost:8080/board/detail?id=1
    public String boardDetail(Model model, Integer id){
        model.addAttribute("board", boardService.boardDetail(id));
        return "boardDetail";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}") //@PathVariable - url에 변수로 사용됨
    public String boardModify(@PathVariable("id") Integer id,  Model model){
        model.addAttribute("board", boardService.boardDetail(id));
        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board){
        Board boardTemp = boardService.boardDetail(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);//사실 덮어 씌우면 안된다고한다...

        return "redirect:/board/list";
    }
}

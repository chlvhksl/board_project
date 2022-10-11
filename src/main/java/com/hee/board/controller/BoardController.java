package com.hee.board.controller;

import com.hee.board.entity.Board;
import com.hee.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm(){
        return "boardWrite";
    }

    @PostMapping("/board/writepro")
    public void boardWritePro(Board board, HttpServletResponse response, MultipartFile file) throws IOException { //entity형식을 그대로 가져올 수 있다.
        boardService.write(board, file);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('글 작성이 완료 되었습니다.'); location.replace('/board/list')</script>");
        out.flush();
    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword){
        Page<Board> list = null;

        if(searchKeyword == null){
            list = boardService.boardList(pageable);
        }else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1; //현재 페이지
        int startPage = Math.max(nowPage - 4, 1); //시작 페이지
        int endPage = Math.min(nowPage + 5, list.getTotalPages()); //끝 페이지

        model.addAttribute("list", boardService.boardList(pageable));//model 'list'에 게시글 목록을 담아서 반환시킴
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
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
    public String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws IOException {
        Board boardTemp = boardService.boardDetail(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);//사실 덮어 씌우면 안된다고한다...

        return "redirect:/board/list";
    }
}

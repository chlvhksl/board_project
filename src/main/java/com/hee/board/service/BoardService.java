package com.hee.board.service;

import com.hee.board.entity.Board;
import com.hee.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository  boardRepository;

    //글 작성
    public void write(Board board){
        boardRepository.save(board);
    }

    //게시글 목록
    public List<Board> boardList(){
        return boardRepository.findAll();
    }

    //게시글 상세
    public Board boardDetail(Integer id){
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }

}

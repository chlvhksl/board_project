package com.hee.board.service;

import com.hee.board.entity.Board;
import com.hee.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository  boardRepository;

    //글 작성
    public void write(Board board, MultipartFile file) throws IOException {
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files"; //저장할 경로 지정

        UUID uuid = UUID.randomUUID(); //파일 랜덤 이름 생성

        String fileName = uuid+"_"+file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);//저장될 파일 이름
        board.setFilepath("/files/"+fileName);//저장될 파일 경로
        
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

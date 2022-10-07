package com.hee.board.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // JPA에서 테이블을 의미한다.
@Data
public class Board {

    @Id //primary key를 의미한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //기본키 자동 생성 전략 중 하나로 기본키 생성을 db에게  위임하는 방식으로 id값을 따로 할당하지 않아도
    //db가 자동으로 auto_increment를 하여 기본키를 생성해준다.
    private Integer id;

    private String title;

    private String content;

    private String filename;

    private String filepath;

}

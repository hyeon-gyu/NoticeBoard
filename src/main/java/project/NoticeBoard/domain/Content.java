package project.NoticeBoard.domain;


import lombok.Data;

@Data
public class Content { // 게시글

    private int id; //user id
    private String title; //게시글 제목
    private String texts; //게시글 본문 내용
    private String writer; //게시자
    private String password; //게시글 비밀번호
    private String updateDate; //게시 날짜

}

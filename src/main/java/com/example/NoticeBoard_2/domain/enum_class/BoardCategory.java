package com.example.NoticeBoard_2.domain.enum_class;

public enum BoardCategory {
    GREETING, FREE, ADMIN, TOTAL;

    public static BoardCategory of(String category) {
        if (category.equalsIgnoreCase("greeting")) return BoardCategory.GREETING;
        else if (category.equalsIgnoreCase("free")) return BoardCategory.FREE;
        else if (category.equalsIgnoreCase("total")) return BoardCategory.TOTAL;
        else if (category.equalsIgnoreCase("admin")) return BoardCategory.ADMIN;
        else return null;
    }


}

/*  GREETING : 가입인사
    FREE : 자유게시판 (ASSOCIATE 회원은 작성 불가)
    ADMIN : 관리자 공지사항
    TOTAL : 전체게시판
*
*/

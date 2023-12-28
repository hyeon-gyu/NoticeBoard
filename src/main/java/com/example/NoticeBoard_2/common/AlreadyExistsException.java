package com.example.NoticeBoard_2.common;


import java.io.Serial;

public class AlreadyExistsException extends RuntimeException{


    @Serial
    private static final long serialVersionUID = -3934492069673352574L;

    public AlreadyExistsException(Long field){
        super(String.format("%s already exists", field));
    }
}

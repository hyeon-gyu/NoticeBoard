package com.example.NoticeBoard_2.common;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;



@NoArgsConstructor
@Getter
public class MemberException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public MemberException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }


}

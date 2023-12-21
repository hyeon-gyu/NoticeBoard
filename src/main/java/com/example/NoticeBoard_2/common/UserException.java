package com.example.NoticeBoard_2.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserException extends RuntimeException{

    private final HttpStatus status;
    private final String message;


}

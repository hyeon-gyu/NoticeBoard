package com.example.NoticeBoard_2.domain.dto;


import com.example.NoticeBoard_2.common.ApiStatus;

public record ApiResponse(
        ApiStatus status,
        String message,
        Object data
) {

    public static ApiResponse success(String message, Object data){
        return new ApiResponse(ApiStatus.SUCCESS, message, data);
    }
    public static ApiResponse success(String message){
        return new ApiResponse(ApiStatus.SUCCESS, message, null);
    }
    public static ApiResponse fail(String message){
        return new ApiResponse(ApiStatus.FAIL, message, null);
    }
    public static ApiResponse error(String message){
        return new ApiResponse(ApiStatus.ERROR, message, null);
    }
}

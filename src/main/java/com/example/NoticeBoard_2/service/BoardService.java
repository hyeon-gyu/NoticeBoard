package com.example.NoticeBoard_2.service;

import com.example.NoticeBoard_2.common.ApiResponse;
import com.example.NoticeBoard_2.domain.entity.Board;
import com.example.NoticeBoard_2.domain.enum_class.BoardCategory;
import com.example.NoticeBoard_2.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public ApiResponse getBoardByCategory(String category){
        List<Board> boardList = boardRepository.findByCategory(category);
        return ApiResponse.success("조회성공",boardList);
    }

}

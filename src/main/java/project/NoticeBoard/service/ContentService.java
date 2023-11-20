package project.NoticeBoard.service;


import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import project.NoticeBoard.domain.Content;
import project.NoticeBoard.repository.ContentRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    public void writeContent(Content content){  //게시글 작성 (본문추가 + 게시글번호 부여)
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm::ss"));
        content.setUpdateDate(formattedDate);

        contentRepository.save(content);
    }

    public void editContent(int id, String texts, String password){ //게시글 수정 (시간변경, 본문 수정)
        Content content = contentRepository.findById(id);
        if(!content.getPassword().equals(password)){
            return;
        }
        content.setTexts(texts);
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm::ss"));
        content.setUpdateDate(formattedDate);
        contentRepository.edit(id,content);
    }

    public void deleteContent(int id, String password){ //비밀번호 확인 후 삭제
        Content content = contentRepository.findById(id);
        if(!content.getPassword().equals(password)){
            return;
        }
        contentRepository.delete(id);
    }

    public List<Content> getAllContents(){ //전체조회
        return  contentRepository.findAll();
    }

    public Content getContent(int id){ //한건조회
        return contentRepository.findById(id);
    }
}

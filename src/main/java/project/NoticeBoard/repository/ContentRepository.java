package project.NoticeBoard.repository;


import org.springframework.stereotype.Repository;
import project.NoticeBoard.domain.Content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//step 1에서는 db 연결보단 컬렉션 중 map 자료구조를 활용해 메모리 영역 단에서 데이터를 저장하는 정도로만 할 예정
@Repository
public class ContentRepository {
    private static Map<Integer, Content> contents = new HashMap<>();

    /**
     * 저장,수정,삭제,조회 기능을 모두 map 자료구조 대상으로 진행 예정 (CRUD 기능 구현)
     */
    private static int sequence = 0;

    public void save(Content content){
        content.setId(++sequence);
        contents.put(content.getId(), content);
    }

    public void edit(int id, Content content){
        contents.put(id,content); //put 함수 활용 이유: 나중에 입력된 값으로 덮어 씌워지기 때문에
    }

    public void delete(int id){
        contents.remove(id);
    }

    public List<Content> findAll(){ //전체 불러오기
        return new ArrayList<>(contents.values());
    }

    public Content findById(int id){
        return contents.get(id);
    }


}

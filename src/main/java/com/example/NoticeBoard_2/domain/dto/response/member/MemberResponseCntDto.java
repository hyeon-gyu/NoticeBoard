package com.example.NoticeBoard_2.domain.dto.response.member;

import com.example.NoticeBoard_2.domain.enum_class.MemberRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class MemberResponseCntDto {

    private MemberRole memberRole;
    private Long memberCnt;

    @Builder
    public MemberResponseCntDto(MemberRole memberRole, Long memberCnt) {
        this.memberRole = memberRole;
        this.memberCnt = memberCnt;
    }

    public static List<MemberResponseCntDto> fromEntity(Map<MemberRole, Long> map){
        ArrayList<MemberResponseCntDto> memberCntDto = new ArrayList<>();
        for(Map.Entry<MemberRole, Long> entry : map.entrySet()){
            memberCntDto.add(MemberResponseCntDto.builder()
                    .memberRole(entry.getKey())
                    .memberCnt(entry.getValue())
                    .build());
        }
        return memberCntDto;
    }
}
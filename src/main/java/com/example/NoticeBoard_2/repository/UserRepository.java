package com.example.NoticeBoard_2.repository;


import com.example.NoticeBoard_2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    //Optional<User> findByLoginId(String loginId);
    Boolean existsByLoginId(String loginId); // 아이디 중복 검사 (회원가입시 중복되는 아이디 금지)
    Boolean existsByNickname(String nickname); // 닉네임 중복검사



}

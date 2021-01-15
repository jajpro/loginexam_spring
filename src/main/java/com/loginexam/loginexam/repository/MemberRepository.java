package com.loginexam.loginexam.repository;

import com.loginexam.loginexam.domain.Member;

import java.util.List;
import java.util.Optional;


public interface MemberRepository {

    Member save(Member member);
    //Optional - NULL 반환 시 감싸서 반환
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll(); //모든 회원리스트 반환

}

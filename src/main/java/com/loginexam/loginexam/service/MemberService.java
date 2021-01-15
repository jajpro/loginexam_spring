package com.loginexam.loginexam.service;

import com.loginexam.loginexam.controller.MemberForm;
import com.loginexam.loginexam.domain.Member;
import com.loginexam.loginexam.repository.MemberRepository;
import com.loginexam.loginexam.restapi.DefaultResponse;
import com.loginexam.loginexam.restapi.ResponseMessage;
import com.loginexam.loginexam.restapi.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //@Autowired 생성자가 하나면 Autowired 어노테이션 생략 가능
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입
     * */
    public long join(Member member) {

        validateDuplicateMember(member); //중복회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    /**
     * 로그인
     */
    public DefaultResponse signIn(final MemberForm form) {
        try {

            Optional<Member> member = findByName(form.getName());
            Optional<Member> result = memberRepository.findById(member.get().getId());

            if (result.isEmpty()) {
                return new DefaultResponse(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
            }

            //엔코딩 - parameter1 : rawPassword, parameter2 : encodePassword
            boolean check = passwordEncoder.matches(form.getPassword(), member.get().getPassword());

            //Login Success
            if (check) {
                return new DefaultResponse(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS);
            }
            return new DefaultResponse(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL, "비밀번호 오류.");

        } catch (Exception e) {
            log.error(e.getMessage());
            return new DefaultResponse(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR, "해당 회원이 없습니다.");
        }
    }


    /**
     * 전체회원조회
     * */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 아이디조회
     * */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

}

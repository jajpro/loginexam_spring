package com.loginexam.loginexam.controller;

import com.loginexam.loginexam.domain.Member;
import com.loginexam.loginexam.restapi.DefaultResponse;
import com.loginexam.loginexam.restapi.ResponseMessage;
import com.loginexam.loginexam.restapi.StatusCode;
import com.loginexam.loginexam.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
public class RestController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/members/new")
    public ResponseEntity create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(form.getPassword());
        member.setPassword(encodePassword);
        form.setPassword(encodePassword);
        memberService.join(member);

        return new ResponseEntity(DefaultResponse.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, form), HttpStatus.OK);
    }




    //API 방식
    //ResponseBody 입력 시 HttpMessageConverter에서 객체는 JsonConverter, 문자는 StringConverter 동작
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;

    }
    static class Hello{
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

}

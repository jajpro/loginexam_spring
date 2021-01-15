package com.loginexam.loginexam;

import com.loginexam.loginexam.repository.JpaMemberRepository;
import com.loginexam.loginexam.repository.MemberRepository;
import com.loginexam.loginexam.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;

//Spring Bean 에 등록
@Configuration
public class SpringConfig extends WebSecurityConfigurerAdapter {

    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository(), getPasswordEncoder());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(em);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
//        PasswordEncoder란?
//        Spring Security에서는 비번을 안전하게 저장할 수 있도록 비밀번호의 단방향 암호화를 지원하는 PasswordEncoder 인터페이스와 구현체들을 제공.

     /* PasswordEncoder 인터페이스는 다음과 같다.
    public interface PasswordEncoder {

        // 비밀번호 단방향 암호화
        String encode(CharSequence rawPassword);

            // 암호화되지 않은 비밀번호(raw)와 암호화된 비밀번호(encode)가 일치하는지 비교
        boolean matches(CharSequence rawPassword, String encodedPassword);

        // 기본적으로 false를 return, Custom하게 구현할 경우 이를 기반으로 더 강력한 암호화 구현
        default boolean upgradeEncoding(String encodedPassword) {
            return false;
        }
    }
    */
//        Spring Security 5.3.3에서 공식 지원하는 PasswordEncoder 구현 클래스들은 아래와 같다.
//        BcryptPasswordEncoder : Bcrypt 해시 함수를 사용하여 비밀번호 암호화
//        Argon2PasswordEncoder : Argon2 해시 함수를 사용하여 비밀번호 암호화
//        Pbkdf2PasswordEncoder : Pbkdf2 해시 함수를 사용하여 비밀번호 암호화
//        SCryptPasswordEncoder : SCrypt 해시 함수를  사용하여 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }


    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()      // cors 비활성화
                .csrf().disable()      // csrf 비활성화
                .formLogin().disable() //기본 로그인 페이지 없애기
                .headers().frameOptions().disable();
    }

}

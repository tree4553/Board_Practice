package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()                               // Cross Site Request Forgery 공격에 대한 Spring Security 설정 비활성화
                .formLogin()                                    // 폼 로그인 방식을 기본 인증 방법으로 지정
                .loginPage("/login")                            // 템플릿에 만들어둔 로그인 페이지를 사용하도록 설정
                .loginProcessingUrl("/process_login")           // /process_login URL로 사용자 인증
                .failureUrl("/login?error")  // login 실패시 param에 error를 담아서 /login으로 이동
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied") // 권한 없는 페이지 접근시 이동
                .and()
                .authorizeRequests(authorize -> authorize       // 요청의 접근 권환 확인 설정
                        .antMatchers("/manage/**").hasRole("ADMIN")     // 관리 페이지는 ADMIN 권한만 접근 가능하도록 설정
                        .antMatchers("/**").permitAll()       // 모든 요청에 대한 권한 허용
                );

        return http.build();
    }
}

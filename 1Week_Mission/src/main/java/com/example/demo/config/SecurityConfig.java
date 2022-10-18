package com.example.demo.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/member/user/**").authenticated()
                .antMatchers("/member/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/member/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()  // 이제 서버 실행 후 로그인을 안 해도 페이지 들어가짐
                .and()
                .formLogin()
                .loginPage("/loginForm")  // 로그인이 필요하면 이동하는 페이지
                .loginProcessingUrl("/login") // 로그인 페이지가 호출되면 시큐리티가 로그인 처리해준다. 컨트롤러에서 /login로직 짤 필요가 없다.
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutUrl("/member/logout") // 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
                .deleteCookies("JSESSIONID", "remember-me") // 로그아웃 후 쿠키 삭제
                .logoutSuccessUrl("/"); // 로그아웃 성공 후 이동페이지

        return http.build();
    }

    public WebSecurityCustomizer webSecurityCustomizer() {
        WebSecurityCustomizer web = new WebSecurityCustomizer() {

            @Override
            public void customize(WebSecurity web) {
                web.ignoring().antMatchers("/tistory/css/**", "/tistory/image/**", "/tistory/js/**");
            }

        };

        return web;
    }

}

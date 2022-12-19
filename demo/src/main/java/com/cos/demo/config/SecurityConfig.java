package com.cos.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)  //secured 어노테이션 활성화, preAuthorize 활성ㅎ
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean //해당 메서드의 리턴되는 오브젝트를 IoC로 등록
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .ignoringAntMatchers("/h2-console/**")
                .disable()

                .headers()
                .frameOptions()
                .disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() //인증만 되면 들어갈 수 있는 주소
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()

                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") //  /login 주소가 호출이 되면 시큐리티가 낚아채서 로그인 처리
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm"); // 후처리 필요
    }
}

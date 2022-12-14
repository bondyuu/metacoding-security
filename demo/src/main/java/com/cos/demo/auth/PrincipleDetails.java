package com.cos.demo.auth;

import com.cos.demo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


//시큐리티가 /login을 낚아채서 로그인을 진행하는데
//로그인이 완료되면 시큐리티 session을 만들어준다. (SecuriyContextHolder)
//오브젝트가 정해져있음 -> Authentication 타입 객체
//User 정보 -> UserDetails타입
//Security Session -> Authentication -> UserDetails(PrincipleDetails)
public class PrincipleDetails implements UserDetails, OAuth2User {

    private User user; //콤포지션
    private Map<String, Object> attributes;

    public PrincipleDetails(User user, Map<String, Object> attributes) { //OAuth 로그인
        this.user = user;
        this.attributes = attributes;
    }

    public PrincipleDetails(User user){ //일반로그인
        this.user = user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    //해당 유저의 권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        //휴면 계정 처리
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}

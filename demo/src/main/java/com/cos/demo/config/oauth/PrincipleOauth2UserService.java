package com.cos.demo.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipleOauth2UserService extends DefaultOAuth2UserService {

    // 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //구글 로그인 버튼 -> 구글 로그인 -> 완료 -> code 리턴(Oauth-Client 라이브러리) -> AccessToken요청
        //userRequest 정보 -> loadUser 함수 호출 -> 회원 프로필
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 구글로부터 받은 데이터로 회원가입 처리
        return super.loadUser(userRequest);
    }
}

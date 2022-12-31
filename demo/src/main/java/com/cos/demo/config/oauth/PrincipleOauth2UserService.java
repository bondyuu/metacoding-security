package com.cos.demo.config.oauth;

import com.cos.demo.auth.PrincipleDetails;
import com.cos.demo.model.User;
import com.cos.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipleOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    // 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //구글 로그인 버튼 -> 구글 로그인 -> 완료 -> code 리턴(Oauth-Client 라이브러리) -> AccessToken요청
        //userRequest 정보 -> loadUser 함수 호출 -> 회원 프로필
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId(); //google
        String providerId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String username = provider + "_" + providerId; // google_1234
        String password = bCryptPasswordEncoder.encode("게인데어");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);

        if(userEntity.equals(null)){
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }

        // 구글로부터 받은 데이터로 회원가입 처리
        return new PrincipleDetails(userEntity, oAuth2User.getAttributes());
    }
}

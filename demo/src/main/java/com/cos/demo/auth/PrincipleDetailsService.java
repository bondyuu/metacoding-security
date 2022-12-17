package com.cos.demo.auth;

import com.cos.demo.model.User;
import com.cos.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 login요청이 오면 자동으로 UserDetailsService 타입으로
//IoC되어있는 loadUserByUsername 함수가 실행
@Service
public class PrincipleDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    //리턴되는 PrincipleDetails -> Authentication -> session
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User userEntity = userRepository.findByUsername(username);
        if(userEntity != null){
            return new PrincipleDetails(userEntity);
        }
        return null;
    }
}

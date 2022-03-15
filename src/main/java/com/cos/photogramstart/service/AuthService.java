package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//1. ioc에 등록
//2. @Service트랜잭션 관리를 해준다
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final  BCryptPasswordEncoder bCryptPasswordEncoder;
    @Transactional // insert,update,delete 를 트렌잭션 처리 해준다.
    public User 회원가입(User user){
        User userEntity = userRepository.save(user);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");
        return userEntity;
    }
}

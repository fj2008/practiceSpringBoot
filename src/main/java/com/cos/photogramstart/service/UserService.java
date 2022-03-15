package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    @Transactional
    public User 회원수정(int id, User user){
        //서비스의 책임
        //1. 영속화
        //   - 옵셔널
        //   - 무조건 찾았다 걱정마(무조건성공시에) = get()
        //   - 못찾았으니 익셉션발동시킬게 = orElseThrow()
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> {return new CustomValidationApiException("찾을수 없는 아이디 입니다.");});
        //2. 영속화된 오브젝트를 수정
        userEntity.setName(user.getName());
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);
        userEntity.setBio((user.getBio()));
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
        //더티체킹이 일어나서 업데이트가 완료됨
    }
}

package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data //getter/setter
public class SignupDto {
    @Size(min = 3,max = 20)
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String name;


    //기존에 setter를 사용해서 하나하나 user객체에 넣어줬던 반복작업을
    // 빌더패턴을 통해서 단순화 시켰다.

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }
}

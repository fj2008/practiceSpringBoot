package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
// 기본적으로 java에선 전역변수에 final이 걸려있으면 무조건 생성자나 객체가 만들어질때 초기화를 꼭 해줘야한다.
//해당 어노테이션은 final이걸려있는 모든 객체에 대한 생성자를 만들어준다.
//final 필드를 di할때 사용
@Controller
//1. ioc에 들록하고
//2. 뷰리졸버 작동시켜서 파일리턴
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult){
        //bindingResult 객체는 Valid에 걸린 모든 익셉션 메시지가 담긴다.
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap= new HashMap<>();
            for(FieldError error: bindingResult.getFieldErrors()){
                errorMap.put(error.getField(),error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성검사 실패", errorMap);
        }else{
            User user = signupDto.toEntity();
            authService.회원가입(user);
            return "auth/signin";
        }

    }


}

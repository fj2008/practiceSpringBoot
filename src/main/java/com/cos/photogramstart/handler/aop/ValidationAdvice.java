package com.cos.photogramstart.handler.aop;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component //RestController,Service 모든것들이 Component를 상속해서 만들어져 있다.
@Aspect
public class ValidationAdvice {


    //proceedingJoinPoint 는 여기 주소에 해당되는 함수가 실행 될때 해당 함수의 모든 내부정보에 접근할 수 있는 파라메터입니다.
    // 그리고 호출된 함수보다 먼저 이 함수가 실행되고
    //proceedingJoinPoint => 호출된 함수의 모든 곳(인자값 등등)에 접근할 수 있는 변수
    // proceed()이 호출될때 호출된 함수가 실행된다.
    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg: args){
            if(arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg; //오브젝트타입이기때문에 다운캐스팅

                if(bindingResult.hasErrors()){
                    Map<String, String> errorMap= new HashMap<>();
                    for(FieldError error: bindingResult.getFieldErrors()){
                        errorMap.put(error.getField(),error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성검사 실패", errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }
    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg: args){
            if(arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg; //오브젝트타입이기때문에 다운캐스팅

                if(bindingResult.hasErrors()){
                    Map<String, String> errorMap= new HashMap<>();
                    for(FieldError error: bindingResult.getFieldErrors()){
                        errorMap.put(error.getField(),error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성검사 실패", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();

    }
}

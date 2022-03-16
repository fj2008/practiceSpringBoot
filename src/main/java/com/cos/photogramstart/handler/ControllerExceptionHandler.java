package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomAPIException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
//@ControllerAdvice이 달려있는 클레스에서 해당 프로그램의 모든 익셉션을 낚아챈다.
@ControllerAdvice
public class ControllerExceptionHandler {
    // @ExceptionHandler(RuntimeException.class)을 하면 RuntimeException이 들고있는 모든 익셉션이 호출될때 해당 함수가 낚아체서 호출된다.
    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e){

        if(e.getErrorMap() == null){
            return Script.back(e.getMessage());
        }else{
            return Script.back(e.getErrorMap().toString());
        }
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationException(CustomValidationApiException e){
        //api 에서 데이터 리턴시사용
        return new ResponseEntity<>(new CMResDto<>(-1, e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomAPIException.class)
    public ResponseEntity<?> apiException(CustomAPIException e){
        //api 에서 데이터 리턴시사용
        return new ResponseEntity<>(new CMResDto<>(-1, e.getMessage(),null),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e){

        return Script.back(e.getMessage());
    }
}

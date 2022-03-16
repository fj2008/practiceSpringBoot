package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomException extends  RuntimeException{


    private static final long serialVersionUID = 6682984069327033232L;


    public CustomException(String message){
        super(message);
    }

}

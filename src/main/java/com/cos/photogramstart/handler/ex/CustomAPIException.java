package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomAPIException extends  RuntimeException{


    private static final long serialVersionUID = 6682984069327033232L;

    private String message;

    public CustomAPIException(String message){
        super(message);
    }


}

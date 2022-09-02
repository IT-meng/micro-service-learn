package com.littlezhou.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public Object doException(Exception e){
        System.out.println(e.getCause().getMessage());
        return new Result(Code.SYSTEM_ERR,null,"SystemException");
    }

}

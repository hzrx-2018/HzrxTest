package com.hhf.forum.exception;



import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CommonExceptionHandler {

    @ResponseBody//可以把结果返回给前端，如果是restfulcontroller的话，就不用加这个注解了
    @ExceptionHandler(Throwable.class)
    public Map<String,String> exceptionHandler(Throwable e){
        Map<String,String> result=new HashMap<>();
        result.put("code","ERROR");
        result.put("message",e.getMessage());
        System.out.println("111111111111111111111");
        return result;
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Map<String,String> exceptionHandler(BindException e){
        Map<String,String> result=new HashMap<>();
        result.put("code","ERROR");

        result.put("message",e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        System.out.println("22222222222222222222222222");
        return result;
    }
}

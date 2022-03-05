package com.mrpeng.handler;

import com.mrpeng.exception.IllegalParamException;
import com.mrpeng.exception.NormalException;
import com.mrpeng.pojo.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    @ExceptionHandler(value = {IllegalParamException.class})
    public R exceptionHandler(IllegalParamException ex){
        System.out.println(ex.getMsg());
        return R.error().message(ex.getMsg());
    }

    @ExceptionHandler(value = {NormalException.class})
    public R normalException(NormalException ex){
        System.out.println(ex.getMsg());
        return R.error().message(ex.getMsg());
    }
}

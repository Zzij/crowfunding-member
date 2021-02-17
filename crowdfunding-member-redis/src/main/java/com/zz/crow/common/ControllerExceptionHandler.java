package com.zz.crow.common;

import com.zz.crow.response.ResultEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultEntity<String> handlerException(Exception e){
        return ResultEntity.failed(e.getMessage());
    }
}

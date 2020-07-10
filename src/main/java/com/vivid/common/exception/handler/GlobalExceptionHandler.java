package com.vivid.common.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vivid.common.api.base.Result;
import com.vivid.common.exception.BaseException;



@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    public Result<?> jsonErrorHandler(HttpServletRequest req, BaseException e) throws Exception {
    	System.out.println("GlobalExceptionHandler = " + e.getClass().getSimpleName());
    	e.printStackTrace();
        return Result.error(e.getMessage());
    }
    
    @ExceptionHandler(value = Exception.class)
    public Result<?> normalErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    	e.printStackTrace();
    	System.out.println("GlobalExceptionHandler = " + e.getClass().getSimpleName());
    	if(e instanceof DataIntegrityViolationException) {
    		return Result.error("数据已存在");
    	}
    	return Result.error("系统错误");
    }
}

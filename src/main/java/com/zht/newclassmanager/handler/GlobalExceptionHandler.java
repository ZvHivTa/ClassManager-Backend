package com.zht.newclassmanager.handler;

import com.zht.newclassmanager.exception.BaseException;
import com.zht.newclassmanager.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 * 用于捕获 Controller 层抛出的异常，并统一返回 Result 格式
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获所有未知的 Exception 异常
     */
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception ex) {
        log.error("系统异常信息：{}", ex.getMessage());
        ex.printStackTrace(); // 打印堆栈信息，方便调试
        return Result.error("系统繁忙，请稍后再试");
    }

    /**
     * 捕获 SQL 完整性约束违反异常 (例如：账号重复注册)
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        // 示例：Duplicate entry 'zhangsan' for key 'employee.idx_username'
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + " 已存在";
            return Result.error(msg);
        }
        return Result.error("数据库操作异常");
    }

    /**
     * 捕获我们自定义的业务异常 (BaseException)
     * 假设你有一个自定义异常基类 BaseException
     */
    @ExceptionHandler(BaseException.class)
    public Result<String> exceptionHandler(BaseException ex){
        log.error("业务异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

}
package com.forestalsen.user_center.exception;

import com.forestalsen.user_center.common.BaseResponse;
import com.forestalsen.user_center.common.ErrorCode;
import com.forestalsen.user_center.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class) //只捕获BusinessException类的异常
    public BaseResponse businessException(BusinessException e) {
        log.error("BusinessException"+e.getMessage(), e);
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescripton());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse exception(Exception e) {
        log.error("RunTimeException:", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage());
    }
}

package com.notification.task.controller;

import com.notification.task.exception.ApplicationException;
import com.notification.task.exception.ErrorCode;
import com.notification.task.exception.ExceptionResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "com.notification.task")
public class ControllerAdvisor  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class, RuntimeException.class, ApplicationException.class})
    protected ExceptionResponse handleException(Exception ex) {
        ExceptionResponse error = new ExceptionResponse();
        error.setErrorMessage(ex.getMessage());
        error.setErrorCOde(ErrorCode.APPLICATION_ERROR.getCode());
        return error;
    }
}

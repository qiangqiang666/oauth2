package com.traffic.authentication;

import com.traffic.authentication.enums.ErrorCodeEnum;
import com.traffic.authentication.exception.ApiException;
import com.traffic.authentication.model.other.RestRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName: ControllerExceptionAdvice
 * @Description: Controller统一异常处理
 * @Author: bite
 * @Date: 2019/6/9
 */
@Slf4j
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class ControllerExceptionAdvice {

    @ExceptionHandler(ApiException.class)
    public RestRes handleApiException(ApiException e, HttpServletRequest request) {
        log.error(String.format("【%s】请求异常 code:%s reason:%s IP:%s", request.getRequestURL().toString(), e.getCode(), e.getMessage(), request.getRemoteHost()));
        return RestRes.failed(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestRes handleApiException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error(String.format("【%s】请求异常 code:-1 reason:%s IP:%s", request.getRequestURL().toString(), e.getMessage(),
                request.getRemoteHost()));
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        ObjectError error = errors.get(0);
        return RestRes.failed(-1, error.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RestRes handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.error(String.format("【%s】请求异常 code:%s reason:%s", request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
        return RestRes.failed(-1, "http 400：" + e.getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestRes handleHttpRequestMethodNotSupportedException(HttpServletRequest request) {
        log.error(String.format("【%s】请求异常 code:%s reason:%s", request.getRequestURL().toString(), HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase()));
        return RestRes.failed(-1, "http 405：");
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public RestRes handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        log.error(String.format("【%s】请求异常 code:%s reason:%s", request.getRequestURL().toString(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase()));
        return RestRes.failed(-1, "http 415：" + e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class})
    public RestRes handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
        log.error(String.format("【%s】请求异常 code:%s reason:%s", request.getRequestURL().toString(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()));
        return RestRes.failed(-1, "http 401：" + e.getMessage());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RestRes handleException(Exception e, HttpServletRequest request) {
//        if (e instanceof AccessDeniedException){
//            return RestRes.failed(ErrorCodeEnum.U4);
//        }
        log.error(String.format("【%s】请求异常 code:%s reason:%s", request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()), e);
        return RestRes.failed(ErrorCodeEnum.FL1.code(),ErrorCodeEnum.FL1.msg());
    }
}

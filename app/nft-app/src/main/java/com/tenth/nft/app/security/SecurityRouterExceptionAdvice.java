package com.tenth.nft.app.security;

import com.tpulse.commons.validation.ValidationException;
import com.wallan.router.constants.RouterErrorCode;
import com.wallan.router.endpoint.core.utils.RouterExceptions;
import com.wallan.router.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shijie
 */
@Component
@ControllerAdvice
public class SecurityRouterExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityRouterExceptionAdvice.class);

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity exceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception e){
        if(e instanceof BizException) {
            return bizException(request, response, (BizException) e);
        }else if(e instanceof ValidationException){
            return validationException(request, response, (ValidationException) e);
        }else{
            return exception(request, response, e);
        }
    }

    private ResponseEntity bizException(HttpServletRequest request, HttpServletResponse response, BizException e) {
        String state = e.getState();
        String message = String.format("[security-router]request: %s exception, state: %s, msg: %s", request.getServletPath(), e.getState(), e.getMessage());
        LOGGER.warn(message, e);
        return RouterExceptions.createExceptionResponse(request, response, state, message);
    }

    private ResponseEntity validationException(HttpServletRequest request, HttpServletResponse response, ValidationException e){
        String message = String.format("[security-router]request: %s exception, msg: %s", request.getServletPath(), e.getMessage());
        LOGGER.error(message, e);
        return RouterExceptions.createExceptionResponse(request, response, RouterErrorCode.NODE_EXCEPITON.getCode(), message);
    }

    private ResponseEntity exception(HttpServletRequest request, HttpServletResponse response, Exception e){
        String message = String.format("[security-router]request: %s exception, \r\n %s", request.getServletPath(), e.getMessage());
        LOGGER.error(message, e);
        return RouterExceptions.createExceptionResponse(request, response, RouterErrorCode.NODE_EXCEPITON.getCode(), message);
    }
}

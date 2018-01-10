package com.nekonekod.tagger.taggerserver.web.controller;

import com.nekonekod.tagger.taggerserver.exception.BusiLogicException;
import com.nekonekod.tagger.taggerserver.util.AjaxResultUtil;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Log4j2
class GlobalControllerExceptionHandler {

    /**
     * This method handles the BusiLogicException
     * <br/>All BusiLogicException thown from the business logic will be returned to the front-end as an AJAX message
     *
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.OK)  // 200
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleBusiLogicException(HttpServletRequest request, Exception ex) {
        if (ex instanceof BusiLogicException) {
            BusiLogicException busiLogicException = (BusiLogicException) ex;
            log.warn("Got BusiLogicException when handling[{}]:\n[{}]", request.getRequestURL(), busiLogicException.getAjaxMsg(), ex);
            return AjaxResultUtil.fail(busiLogicException.getAjaxMsg());
        }
        if (ex instanceof HttpMessageNotReadableException) {
            log.error("参数格式错误", ex);
            return AjaxResultUtil.fail("参数格式错误");
        }
        log.error("服务器异常", ex);
        return AjaxResultUtil.fail("服务器异常");
    }
}
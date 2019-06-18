package com.e3.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionResolver implements HandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        e.printStackTrace();
        logger.debug("测试输出的日志。。。。。。。");
        logger.info("系统发生异常了。。。。。。。");
        logger.error("系统发生异常", e);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}

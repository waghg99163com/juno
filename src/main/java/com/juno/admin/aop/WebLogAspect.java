package com.juno.aop;



import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Component
public class WebLogAspect
{
    private Logger logger = Logger.getLogger(getClass());

    @Pointcut("execution(public * com.juno.controller..*.*(..))")
    public void webLog()
    {

    }

    @Before(value = "webLog()")
    public void doBefore(JoinPoint joinpoint) throws Throwable
    {
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录下请求内容
        logger.info("URL : "+request.getRequestURL().toString());
        logger.info("HTTP_METHOD : "+request.getMethod());
        logger.info("IP : "+request.getRemoteAddr());
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements())
        {
            String name = (String)enu.nextElement();
            logger.info("name:{"+name+"},value:{"+request.getParameter(name)+"}");
        }
    }

    @AfterReturning(returning = "ret",pointcut = "webLog()")
    public void doAfterReturn(Object ret) throws Throwable
    {
        //处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }
}

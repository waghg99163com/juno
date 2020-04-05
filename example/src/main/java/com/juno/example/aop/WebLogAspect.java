package com.juno.example.aop;

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
        System.out.println("URL : "+request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : "+request.getMethod());
        System.out.println("IP : "+request.getRemoteAddr());
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements())
        {
            String name = (String)enu.nextElement();
            System.out.println("name:{"+name+"},value:{"+request.getParameter(name)+"}");
        }
    }

    @AfterReturning(returning = "ret",pointcut = "webLog()")
    public void doAfterReturn(Object ret) throws Throwable
    {
        //处理完请求，返回内容
        System.out.println("RESPONSE : " + ret);
    }
}

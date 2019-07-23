package com.juno.controller;

import com.juno.entity.User;
import com.juno.test01.service.UserTest1Service;
import com.juno.test02.service.UserTest2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private UserTest1Service userTest1Service;
    @Autowired
    private UserTest2Service userTest2Service;

    @RequestMapping("/insertTest1")
    @ResponseBody
    public String insertTest1(User user)
    {
        userTest1Service.insertTest(user);
        return "success";
    }

    @RequestMapping("/selectTest1")
    @ResponseBody
    public String selectTest1(Long id)
    {
        User user = userTest1Service.selectTest(id);
        return user.getName();
    }

    @RequestMapping("/insertTest2")
    @ResponseBody
    public String insertTest2(User user)
    {
        userTest2Service.insertTest(user);
        return "success";
    }

    @RequestMapping("/selectTest2")
    @ResponseBody
    public String selectTest2(Long id)
    {
        User user = userTest2Service.selectTest(id);
        return user.getName();
    }
}

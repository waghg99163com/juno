package com.juno.example.test01.service;

import com.juno.example.entity.User;
import com.juno.example.test01.dao.UserMapper1;
import com.juno.example.test02.service.UserTest2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserTest1Service {

    @Autowired(required = false)
    private UserMapper1 userMapper1;
    @Autowired
    private UserTest2Service userTest2Service;

    @Transactional
    public int insertTest(User user)
    {

        int result = userMapper1.insertTest(user.getName(),user.getAge());
        userTest2Service.insertTest(user);
        int i = 1/0;
        return result;
    }

    public User selectTest(Long id)
    {
        return userMapper1.selectTest(id);
    }

    @Async
    public String sendMsg()
    {
        System.out.println("######2######");
        System.out.println("######3######");
        return "success";
    }
}

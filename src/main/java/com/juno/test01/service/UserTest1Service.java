package com.juno.test01.service;

import com.juno.entity.User;
import com.juno.test01.dao.UserMapper1;
import com.juno.test02.dao.UserMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTest1Service {

    @Autowired(required = false)
    private UserMapper1 userMapper1;

    public int insertTest(User user)
    {
        return userMapper1.insertTest(user.getName(),user.getAge());
    }

    public User selectTest(Long id)
    {
        return userMapper1.selectTest(id);
    }
}

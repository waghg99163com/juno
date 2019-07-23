package com.juno.test01.service;

import com.juno.entity.User;
import com.juno.test01.dao.UserMapper1;
import com.juno.test02.dao.UserMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserTest1Service {

    @Autowired(required = false)
    private UserMapper1 userMapper1;

    @Transactional
    public int insertTest(User user)
    {

        int result = userMapper1.insertTest(user.getName(),user.getAge());
        int i = 1/0;
        return result;
    }

    public User selectTest(Long id)
    {
        return userMapper1.selectTest(id);
    }
}

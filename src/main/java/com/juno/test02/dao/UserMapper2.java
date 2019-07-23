package com.juno.test02.dao;

import com.juno.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper2 {
    @Insert("insert into user (NAME, AGE) value (#{name},#{age})")
    Integer insertTest(String name, Long age);

    @Select("select * from user where id = #{id}")
    User selectTest(Long id);
}

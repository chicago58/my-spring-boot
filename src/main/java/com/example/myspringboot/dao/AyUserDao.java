package com.example.myspringboot.dao;

import com.example.myspringboot.model.AyUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 Dao
 */
@Mapper
public interface AyUserDao {

    /**
     * 通过用户名和密码查询用户
     *
     * @param name
     * @param password
     * @return
     */
    AyUser findByNameAndPassword(@Param("name") String name,
                                 @Param("password") String password);
}

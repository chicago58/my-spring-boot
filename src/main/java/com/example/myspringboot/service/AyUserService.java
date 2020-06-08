package com.example.myspringboot.service;

import com.example.myspringboot.model.AyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * 用户服务层接口
 */
public interface AyUserService {

    /**
     * 查询单个记录
     *
     * @param id
     * @return
     */
    AyUser findById(String id);

    /**
     * 查询所有记录
     *
     * @return
     */
    List<AyUser> findAll();

    /**
     * 保存、更新记录
     *
     * @param ayUser
     * @return
     */
    AyUser save(AyUser ayUser);

    /**
     * 删除记录
     *
     * @param id
     */
    void delete(String id);

    /**
     * 分页
     *
     * @param pageable
     * @return
     */
    Page<AyUser> findAll(Pageable pageable);

    /**
     * 根据名字查询
     *
     * @param name
     * @return
     */
    List<AyUser> findByName(String name);

    /**
     * 根据名字模糊查询
     *
     * @param name
     * @return
     */
    List<AyUser> findByNameLike(String name);

    /**
     * 根据主键集合查询
     *
     * @param ids
     * @return
     */
    List<AyUser> findByIdIn(Collection<String> ids);

    /**
     * 根据用户名和密码查询用户
     *
     * @param name
     * @param password
     * @return
     */
    AyUser findByNameAndPassword(String name, String password);
}

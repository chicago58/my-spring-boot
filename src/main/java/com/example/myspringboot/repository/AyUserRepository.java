package com.example.myspringboot.repository;

import com.example.myspringboot.model.AyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * 自定义查询方法
 */
public interface AyUserRepository extends JpaRepository<AyUser, String> {

    /**
     * 通过名字查询
     *
     * @param name
     * @return
     */
    List<AyUser> findByName(String name);

    /**
     * 通过名字模糊查询
     *
     * @param name
     * @return
     */
    List<AyUser> findByNameLike(String name);

    /**
     * 通过主键集合查询
     *
     * @param ids
     * @return
     */
    List<AyUser> findByIdIn(Collection<String> ids);
}

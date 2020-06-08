package com.example.myspringboot.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.example.myspringboot.dao.AyUserDao;
import com.example.myspringboot.model.AyUser;
import com.example.myspringboot.repository.AyUserRepository;
import com.example.myspringboot.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;


/**
 * 用户服务层实现类
 */
//@Transactional // 类开启事务
@Service
public class AyUserServiceImpl implements AyUserService {
    Logger logger = LogManager.getLogger(AyUserServiceImpl.class);

    private static final String ALL_USER = "ALL_USER_LIST";

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private AyUserRepository ayUserRepository;

    @Resource
    private AyUserDao ayUserDao;

    @Override
    public AyUser findById(String id) {
//        return ayUserRepository.findById(id).get();

        // 查询缓存的所有数据
        List<AyUser> allUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
        if (allUserList != null && allUserList.size() > 0) {
            for (AyUser ayUser : allUserList) {
                if (StringUtils.equals(id, ayUser.getId())) {
                    return ayUser;
                }
            }
        }

        // 查询数据库
        AyUser ayUser = ayUserRepository.findById(id).get();
        if (ayUser != null) {
            // 数据更新到缓存
            redisTemplate.opsForList().leftPush(ALL_USER, ayUser);
        }
        return ayUser;
    }

    @Override
    public List<AyUser> findAll() {
        return ayUserRepository.findAll();
    }

    //    @Transactional // 方法开启事务
    @Override
    public AyUser save(AyUser ayUser) {
        AyUser saveUser = ayUserRepository.save(ayUser);

        // 空指针异常
        String error = null;
        error.split("/");
        return saveUser;
    }

    @Override
    public void delete(String id) {
        ayUserRepository.deleteById(id);

        logger.info("userId: " + id + " 用户被删除！");
    }

    @Override
    public Page<AyUser> findAll(Pageable pageable) {
        return ayUserRepository.findAll(pageable);
    }

    @Override
    public List<AyUser> findByName(String name) {
        return ayUserRepository.findByName(name);
    }

    @Override
    public List<AyUser> findByNameLike(String name) {
        return ayUserRepository.findByNameLike(name);
    }

    @Override
    public List<AyUser> findByIdIn(Collection<String> ids) {
        return ayUserRepository.findByIdIn(ids);
    }

    @Override
    public AyUser findByNameAndPassword(String name, String password) {
        return ayUserDao.findByNameAndPassword(name, password);
    }

}

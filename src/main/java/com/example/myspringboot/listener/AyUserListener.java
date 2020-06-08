package com.example.myspringboot.listener;

import com.example.myspringboot.model.AyUser;
import com.example.myspringboot.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * 监听器
 */
@WebListener
public class AyUserListener implements ServletContextListener {
    Logger logger = LogManager.getLogger(AyUserListener.class);

    private static final String ALL_USER = "ALL_USER_LIST";

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private AyUserService ayUserService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 查询数据库所有用户数据
        List<AyUser> allUserList = ayUserService.findAll();
        // 清除缓存的用户数据
        redisTemplate.delete(ALL_USER);
        // 将数据存放到缓存
        redisTemplate.opsForList().leftPushAll(ALL_USER, allUserList);
        // 查询所有用户数据
        List queryUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
//        System.out.println("缓存的用户数：" + queryUserList.size());
        logger.info("缓存的用户数：" + queryUserList.size());

//        System.out.println("ServletContext 上下文初始化");
        logger.info("ServletContext 上下文初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        System.out.println("ServletContext 上下文销毁");
        logger.info("ServletContext 上下文销毁");
    }
}

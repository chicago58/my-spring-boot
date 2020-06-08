package com.example.myspringboot.quartz;

import com.example.myspringboot.model.AyUser;
import com.example.myspringboot.service.AyUserService;
import com.example.myspringboot.service.SendJunkMailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时器类
 */
@Component
@Configuration
@EnableScheduling
public class SendMailQuartz {

    private static final Logger logger = LogManager.getLogger(SendMailQuartz.class);

    @Resource
    private SendJunkMailService sendJunkMailService;

    @Resource
    private AyUserService ayUserService;

    @Scheduled(cron = "*/5 * * * * *")
    public void reportCurrentByCron() {
        List<AyUser> allAyUsers = ayUserService.findAll();
        if (allAyUsers == null || allAyUsers.size() <= 0) {
            return;
        }

        // 发送邮件
        sendJunkMailService.sendJunkMail(allAyUsers);
        logger.info("定时器运行了！！！");
    }
}

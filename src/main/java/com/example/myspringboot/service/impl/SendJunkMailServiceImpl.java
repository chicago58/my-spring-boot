package com.example.myspringboot.service.impl;

import com.example.myspringboot.model.AyUser;
import com.example.myspringboot.service.AyUserService;
import com.example.myspringboot.service.SendJunkMailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * 发送邮件服务
 */
@Service
public class SendJunkMailServiceImpl implements SendJunkMailService {
    public static final Logger logger = LogManager.getLogger(SendJunkMailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendJunkMail(List<AyUser> ayUserList) {

        try {
            if (ayUserList == null || ayUserList.size() <= 0) {
                return Boolean.FALSE;
            }

            for (AyUser ayUser : ayUserList) {
                MimeMessage mimeMessage = this.mailSender.createMimeMessage();
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                // 邮件发送方
                message.setFrom(from);
                // 邮件主题
                message.setSubject("地瓜今日特卖");
                // 邮件接收方
                message.setTo("1162828839@qq.com");
                // 邮件内容
                message.setText(ayUser.getName() + ", 一斤只要9元 ");
                // 发送邮件
                this.mailSender.send(mimeMessage);
            }
        } catch (Exception ex) {
            logger.error("sendJunkMail error and ayUser=%s", ayUserList, ex);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}

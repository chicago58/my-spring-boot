package com.example.myspringboot.service;


import com.example.myspringboot.model.AyUser;

import java.util.List;

/**
 * 发送邮件服务
 */
public interface SendJunkMailService {

    boolean sendJunkMail(List<AyUser> ayUser);
}

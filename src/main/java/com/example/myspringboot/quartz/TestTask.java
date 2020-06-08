package com.example.myspringboot.quartz;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 定时器类
 */
public class TestTask {
    private static final Logger logger = LogManager.getLogger(TestTask.class);

    public void run() {
        logger.info("定时器运行了！！！");
    }
}

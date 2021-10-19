package com.shen;

import com.shen.service.AirportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName Main.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年10月09日 14:02:00
 */
public class SingleThreadMain {
    private static final Logger logger = LogManager.getLogger(SingleThreadMain.class);

    public static void main(String[] args) throws IOException {
        logger.info("=================程序开始运行=================");
        //获取开始时间
        long startTime = System.currentTimeMillis();
        new AirportService().start();
        //获取结束时间
        long endTime = System.currentTimeMillis();
        //输出程序运行时间
        logger.info("=================程序运行结束=================");
        logger.info("程序运行时间：" + (endTime - startTime) + "ms");
    }
}

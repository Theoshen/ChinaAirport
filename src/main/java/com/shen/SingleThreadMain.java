package com.shen;

import com.shen.config.UrlConstant;
import com.shen.entity.Area;
import com.shen.service.AirportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
        Long startTime = System.currentTimeMillis();
        // 单线程版本
        logger.info("=================程序开始运行=================");
//        new AirportService().start();
        new AirportService().test();
        Long endTime = System.currentTimeMillis();
        logger.info("程序运行时间:" + (endTime-startTime) + "ms");
        logger.info("=================程序运行结束=================");
    }
}

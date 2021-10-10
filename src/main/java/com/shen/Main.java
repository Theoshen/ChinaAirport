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
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        logger.info("=================程序开始运行=================");
        new AirportService().start();

        logger.info("=================程序运行结束=================");
    }
}

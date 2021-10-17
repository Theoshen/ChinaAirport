package com.shen;

import com.shen.config.UrlConstant;
import com.shen.entity.Area;
import com.shen.service.AirportCallable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName MultithreadingMain.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年10月17日 23:04:00
 */
public class MultithreadingMain {
    private static final Logger logger = LogManager.getLogger(SingleThreadMain.class);

    public static void main(String[] args) throws IOException {
        Long startTime = System.currentTimeMillis();
        logger.info("=================程序开始运行=================");
        // 多线程版本
        String[] ids = UrlConstant.AREAIDS;
        List<Future<Area>> areaList = new ArrayList<>();
        ThreadPoolExecutor tpc = (ThreadPoolExecutor) Executors.newFixedThreadPool(ids.length);
        for (String id : ids) {
            AirportCallable airport = new AirportCallable(id);
            Future<Area> result = tpc.submit(airport);
            areaList.add(result);
        }
        //创建一个循环来监控执行器的状态
        try {
            while (tpc.getCompletedTaskCount() < areaList.size()) {
                Thread.sleep(50);
            }
            System.out.println("全部线程执行结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tpc.shutdownNow();
        Long endTime = System.currentTimeMillis();
        logger.info("程序运行时间:" + (endTime-startTime) + "ms");
        logger.info("=================程序运行结束=================");
    }
}

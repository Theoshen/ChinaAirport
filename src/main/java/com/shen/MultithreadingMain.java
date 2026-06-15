package com.shen;

import com.shen.config.UrlConstant;
import com.shen.entity.Airport;
import com.shen.entity.Area;
import com.shen.service.AirportCallable;
import com.shen.utils.ExcelUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName MultithreadingMain.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年10月17日 23:04:00
 */
public class MultithreadingMain {
    private static final Logger logger = LogManager.getLogger(MultithreadingMain.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        logger.info("=================程序开始运行=================");
        // 多线程版本
        String[] ids = UrlConstant.AREAIDS;
        List<Future<Area>> futureList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(ids.length);
        try {
            for (String id : ids) {
                AirportCallable airport = new AirportCallable(id);
                Future<Area> result = executorService.submit(airport);
                futureList.add(result);
            }
            // 处理数据（排序）
            for (Future<Area> future : futureList) {
                Area area = future.get();
                areaList.add(area);
            }
        } finally {
            executorService.shutdown();
        }
        System.out.println("全部线程执行结束");

        Collections.sort(areaList);
        List<Airport> airports = new ArrayList<>();
        for (Area area : areaList) {
            airports.addAll(area.getList());
        }

        System.out.println(areaList);
        long endTime = System.currentTimeMillis();
        logger.info("程序运行时间:" + (endTime-startTime) + "ms");
        logger.info("=================程序运行结束=================");

        ExcelUtil.EasyExport(airports);

    }
}

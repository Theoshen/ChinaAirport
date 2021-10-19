package com.shen.service;

import com.alibaba.fastjson.JSONObject;
import com.shen.entity.Airport;
import com.shen.entity.Area;
import com.shen.service.AirportService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName AirportRunnable.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年10月17日 22:13:00
 */
public class AirportCallable implements Callable<Area> {
    private String id;

    public AirportCallable (String id){
        this.id = id;
    }

    @Override
    public Area call() throws Exception {
        JSONObject jsonObject = AirportService.getJson(id);
        // 获取地区名称
        String areaName = jsonObject.get("title").toString();
        // 拿到各地区运输机场列表
        List<Airport> list = AirportService.getAirports(jsonObject);
        // 完善数据
        List<Airport> airports = new ArrayList<>();
        for (Airport airport : list) {
            airport = AirportService.setAirport(airport.getId(),airport);
            airports.add(airport);
        }
        Area area = new Area();
        area.setAreaId(id);
        area.setList(airports);
        area.setAreaName(areaName);
        System.out.println(area);
        return area;
    }


}

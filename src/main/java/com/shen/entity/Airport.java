package com.shen.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName AirPort.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年10月09日 13:56:00
 */

@Data
public class Airport {

    private String id;

    @Excel(name = "地区",  width = 20)
    private String area;

    @Excel(name = "机场名称",  width = 30)
    private String name;

    @Excel(name = "地址",  width = 100)
    private String address;

    @Excel(name = "机场等级",  width = 15)
    private String level;

    @Excel(name = "IATA",  width = 15)
    private String iata;

    @Excel(name = "ICAO",  width = 15)
    private String icao;

    @Excel(name = "机场类型",  width = 15)
    private String type;

    @Excel(name = "经度",  width = 30)
    private String latitude;

    @Excel(name = "纬度",  width = 30)
    private String longitude;

    @Excel(name = "海拔",  width = 15)
    private String altitude;

    @Excel(name = "跑道数量",  width = 15)
    private String runway;
}

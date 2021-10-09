package com.shen.entity;


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

    private String name;

    private String level;

    private String iata;

    private String icao;

    private String address;

    private String type;

    private String latitude;

    private String longitude;

    private String  altitude;

    private String area;

    private String runway;
}

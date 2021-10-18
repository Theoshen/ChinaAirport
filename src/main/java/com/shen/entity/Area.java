package com.shen.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName Area.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年10月09日 14:09:00
 */
@Data
public class Area implements Comparable<Area> {

    private String areaName;

    private String areaId;

    private List<Airport> list;

    @Override
    public int compareTo(Area o) {
        return (Integer.parseInt(this.areaId) - Integer.parseInt(o.areaId));
    }
}

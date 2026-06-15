package com.shen.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.shen.config.UrlConstant;
import com.shen.entity.Airport;
import com.shen.entity.Area;
import com.shen.utils.ExcelUtil;
import com.shen.utils.HtmlParseUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName AirportService.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年10月10日 14:35:00
 */
@Service
public class AirportService {
    private static final Map<Integer, String> AIRPORT_DETAIL_FIELD_MAP;

    static {
        Map<Integer, String> fieldMap = new HashMap<>();
        fieldMap.put(0, "Iata");
        fieldMap.put(1, "Icao");
        fieldMap.put(2, "Level");
        fieldMap.put(3, "Altitude");
        fieldMap.put(4, "Type");
        fieldMap.put(5, "Runway");
        fieldMap.put(7, "Latitude");
        fieldMap.put(8, "Longitude");
        fieldMap.put(10, "Address");
        AIRPORT_DETAIL_FIELD_MAP = Collections.unmodifiableMap(fieldMap);
    }

    /**
     * @Description 境内机场
     * @author chensihua
     * @param jsonObject
     * @createTime 13:05 2021/10/19
     * @return java.util.List<com.shen.entity.Airport>
     * @version 1.0.0
     */
    public static List<Airport> getAirports(JSONObject jsonObject) {
        JSONArray recordMap = jsonObject.getJSONArray("data"); // Directly use getJSONArray
        String title = getAreaTitle(jsonObject);

        List<Airport> airports = new ArrayList<>();
        for (int i = 0; i < recordMap.size(); i++) {
            JSONArray airportInfo = recordMap.getJSONArray(i);

            Airport airPort = new Airport();
            airPort.setArea(title);
            airPort.setName(airportInfo.getString(0)); // Airport Name
            airPort.setId(airportInfo.getString(2)); // ID

            airports.add(airPort);
        }

        return airports;
    }

    private static String getAreaTitle(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        if (title == null) {
            return "";
        }
        int transportIndex = title.indexOf("运");
        if (transportIndex < 0) {
            return title;
        }
        return title.substring(0, transportIndex);
    }

    public static JSONObject getJson(String areaId) {
        // 获取不同地区机场id信息，返回JsonObject
        String url = UrlConstant.BASE_URL + "airmap?" + "a=1&" + "areaid=" + areaId + "&type=1&high=0&special=0";
        String result = HtmlParseUtil.sendGet(url);
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }

    public static Airport setAirport(String id, Airport airport) throws IOException {
        String url = "http://www.chinairport.net/airport/" + id;
        Document document = Jsoup.connect(url).get();
        Elements element = document.select(".airport_can");
        String str = element.text();
        String[] newStr = str.split(" ");
        List<String> list = Arrays.asList(newStr);

        for (int i = 0; i < list.size(); i++) {
            String value = list.get(i);
            if (!value.contains(":"))
                continue;

            String fieldName = AIRPORT_DETAIL_FIELD_MAP.get(i);
            if (fieldName != null) {
                value = value.substring(value.indexOf(":") + 1);
                setField(airport, fieldName, value);
            }
        }
        return airport;
    }

    private static void setField(Airport airport, String fieldName, String value) {
        switch (fieldName) {
            case "Iata":
                airport.setIata(value);
                break;
            case "Icao":
                airport.setIcao(value);
                break;
            case "Level":
                airport.setLevel(value);
                break;
            case "Altitude":
                airport.setAltitude(value);
                break;
            case "Type":
                airport.setType(value);
                break;
            case "Runway":
                airport.setRunway(value);
                break;
            case "Latitude":
                airport.setLatitude(value);
                break;
            case "Longitude":
                airport.setLongitude(value);
                break;
            case "Address":
                airport.setAddress(value);
                break;
            default:
                break;
        }
    }

    public void start() throws IOException {
        String[] areaIds = UrlConstant.AREAIDS;
        List<Area> areaList = new ArrayList<>();
        for (String id : areaIds) {
            JSONObject jsonObject = getJson(id);
            // 获取地区名称
            String areaName = jsonObject.getString("title");
            // 拿到各地区运输机场列表
            List<Airport> list = getAirports(jsonObject);
            // 完善数据
            List<Airport> airports = new ArrayList<>();
            for (Airport airport : list) {
                airport = setAirport(airport.getId(), airport);
                airports.add(airport);
            }
            Area area = new Area();
            area.setAreaId(id);
            area.setList(airports);
            area.setAreaName(areaName);
            areaList.add(area);
        }
        Collections.sort(areaList);
        List<Airport> airports = new ArrayList<>();
        for (Area area : areaList) {
            airports.addAll(area.getList());
        }
        for (Airport airport : airports) {
            System.out.println(airport);
        }
        ExcelUtil.ExportExcel(airports);
    }
}

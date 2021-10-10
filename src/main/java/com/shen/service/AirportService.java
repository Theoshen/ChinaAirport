package com.shen.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shen.config.UrlConstant;
import com.shen.entity.Airport;
import com.shen.entity.Area;
import com.shen.utils.HtmlParseUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName AirportService.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年10月10日 14:35:00
 */
public class AirportService {


    public static List<Airport> getAirports(JSONObject jsonObject) {
        JSONArray recordMap = JSON.parseArray(jsonObject.get("data").toString());
        String title = jsonObject.get("title").toString();
        List<String[]> list = recordMap.toJavaList(String[].class);
        List<Airport> airports = new ArrayList<>();
        for (String[] strings : list) {
            Airport airPort = new Airport();
            for (int i = 0; i < strings.length; i++) {
                airPort.setArea(title);
                if (i == 0) {
                    airPort.setName(strings[i]);
                }
                else if (i == 2) {
                    airPort.setId(strings[i]);
                }
            }
            airports.add(airPort);
        }

        return airports;
    }

    public static JSONObject getJson(String areaId){
        // 获取不同地区机场id信息，返回JsonObject
        String url = UrlConstant.BASE_URL + "airmap?" + "a=1&" + "areaid=" + areaId + "&type=1&high=0&special=0";
        String result = HtmlParseUtil.sendGet(url);
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }

    public static Airport setAirport(String id,Airport airport) throws IOException {
        String url = "https://www.chinairport.net/airport/" + id;
        Document document = Jsoup.connect(url).get();
        Elements element = document.select(".airport_can");
        String str = element.text();
        for (Element element1 : element) {
            str = element1.text();
        }
        String cut = " ";
        String[] newStr = str.split(cut);
        List<String> list = Arrays.asList(newStr);
        List<String> strings = new ArrayList<>();
        for (String s : list) {
            s = s.substring(s.indexOf(":") + 1);
            strings.add(s);
        }
        for (int i = 0; i < strings.size(); i++) {
            switch (i) {
                case 0:
                    airport.setIata(strings.get(i));
                    break;
                case 1:
                    airport.setIcao(strings.get(i));
                    break;
                case 2 :
                    airport.setLevel(strings.get(i));
                    break;
                case 3 :
                    airport.setAltitude(strings.get(i));
                    break;
                case 4:
                    airport.setType(strings.get(i));
                    break;
                case 5 :
                    airport.setRunway(strings.get(i));
                    break;
                case 7 :
                    airport.setLatitude(strings.get(i));
                    break;
                case 8:
                    airport.setLongitude(strings.get(i));
                    break;
                case 10 :
                    airport.setAddress(strings.get(i));
                    break;
                default:
            }
        }
        return airport;
    }


    public void start() throws IOException {
        String[] areaIds = UrlConstant.AREAIDS;
        List<Area> areaList = new ArrayList<>();
        for (String id : areaIds) {
            JSONObject jsonObject = getJson(id);
            // 获取地区名称
            String areaName = jsonObject.get("title").toString();
            // 拿到各地区运输机场列表
            List<Airport> list = getAirports(jsonObject);
            // 完善数据
            List<Airport> airports = new ArrayList<>();
            for (Airport airport : list) {
                airport = setAirport(airport.getId(),airport);
                airports.add(airport);
            }
            Area area = new Area();
            area.setAreaId(id);
            area.setList(airports);
            area.setAreaName(areaName);
            areaList.add(area);
        }
        Integer i1 = areaList.size();

        String json = JSON.toJSONString(areaList);

        System.out.println(areaList);
    }
}

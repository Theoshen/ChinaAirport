package com.shen.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shen.config.UrlConfig;
import com.shen.entity.Airport;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName HtmlParseUtil.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年10月09日 14:07:00
 */
@Component
public class HtmlParseUtil {
    private static final Logger log = LoggerFactory.getLogger(HtmlParseUtil.class);

    public static List<Airport> parse(String areaId) throws IOException {
        String url = UrlConfig.BASE_URL + "airmap?" + "a=1&" + "areaid=" + areaId + "&type=1&high=0&special=0";

        Document document = Jsoup.connect(url).get();

        Element element = document.getElementById("");

        List<Airport> list = new ArrayList<>();
        return list;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static List<Airport> getAirports(String areaId) {
        String url = UrlConfig.BASE_URL + "airmap?" + "a=1&" + "areaid=" + areaId + "&type=1&high=0&special=0";
        String result = sendGet(url);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray recordMap = JSON.parseArray(jsonObject.get("data").toString());
//        List<Map<String,String>> list =
        List<String[]> list = recordMap.toJavaList(String[].class);
        List<Airport> airports = new ArrayList<>();
        for (String[] strings : list) {
            Airport airPort = new Airport();
            for (int i = 0; i < strings.length; i++) {
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



    public static Airport setAirport(String id,Airport airport) throws IOException {
        String url = "https://www.chinairport.net/airport/" + id;
        Document document = Jsoup.connect(url).get();
        Elements element = document.select(".airport_can");
        String str = "";
        for (Element element1 : element) {
            str = element.text();
        }
        String cut = " ";    // 分割串，此处为一个空格

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
                case 1:
                    airport.setIcao(strings.get(i));
                case 2 :
                    airport.setLevel(strings.get(i));
                case 3 :
                    airport.setAltitude(strings.get(i));
                case 4:
                    airport.setType(strings.get(i));
                case 5 :
                    airport.setRunway(strings.get(i));
                case 7 :
                    airport.setLatitude(strings.get(i));
                case 8:
                    airport.setLongitude(strings.get(i));
                case 10 :
                    airport.setAddress(strings.get(i));
            }
        }
        return airport;
    }


    public static void main(String[] args) throws IOException {
        String areaId = "1";
        // todo 1～6 areaid
        List<Airport> list = getAirports(areaId);
        List<Airport> airports = new ArrayList<>();
        for (Airport airport : list) {
            airport = setAirport(airport.getId(),airport);
            airports.add(airport);
        }


        String json = JSON.toJSONString(airports);
        System.out.println(json);
    }
}

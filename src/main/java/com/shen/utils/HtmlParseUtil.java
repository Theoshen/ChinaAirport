package com.shen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

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
    private static final int CONNECT_TIMEOUT_MS = 10000;
    private static final int READ_TIMEOUT_MS = 30000;

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        StringBuilder result = new StringBuilder();
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
            connection.setReadTimeout(READ_TIMEOUT_MS);
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 读取响应内容
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        return result.toString();
    }

}

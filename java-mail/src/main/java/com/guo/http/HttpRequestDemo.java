package com.guo.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * jdk11之前使用的是HttpURLConnection,jdk11之后引入了新的HttpClient,这里我简单写一下前者，后者遇到再学，现在还是jdk8多
 */
public class HttpRequestDemo {
    private void requestDemo() throws IOException {
        URL url = new URL("127.0.0.1:");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);
        conn.setConnectTimeout(5000); // 请求超时5秒
        // 设置HTTP头:
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 11; Windows NT 5.1)");
        // 连接并发送HTTP请求:
        conn.connect();
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("bad response");
        }
        //获取所有的相应Header
        Map<String, List<String>> headers = conn.getHeaderFields();
        for (String s : headers.keySet()) {
            System.out.println(s + ":" + headers.get(s));
        }
        InputStream in = conn.getInputStream();

    }
}

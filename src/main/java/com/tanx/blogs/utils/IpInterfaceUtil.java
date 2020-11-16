package com.tanx.blogs.utils;

import com.alibaba.fastjson.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author tanxiang
 * @version 1.0
 * @date 2020/10/1 下午2:24
 */
public class IpInterfaceUtil {

    public static String getPro(String ip){
        if(ip == null || "127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)){
            return null;
        }
        String message = res(ip);
        if(message!=null){
            return JSONObject.parseObject(message).get("pro").toString();
        }
        return JSONObject.parseObject(content(ip)).get("regionName").toString();
    }

    public static String getCity(String ip){
        if(ip == null || "127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)){
            return null;
        }
        String message = res(ip);
        if(message!=null){
            return JSONObject.parseObject(message).get("city").toString();
        }
    return JSONObject.parseObject(content(ip)).get("city").toString();
    }

    public static String getCountry(String ip){
        if(ip == null || "127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)){
            return null;
        }
      return JSONObject.parseObject(content(ip)).get("country").toString();
    }

    private static String res(String ip){
        String httpsApi = "https://whois.pconline.com.cn/ipJson.jsp?ip="+ip+"&json=true";
        try {
            URL url = new URL(httpsApi);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "GBK"));
            StringBuilder builder = new StringBuilder();
            String data;
            while((data = reader.readLine())!=null){
                builder.append(data);
            }
            return new String(getUtf8BytesFromGbkString(builder.toString()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String content(String ip){
        String api ="http://ip-api.com/json/"+ip+"?lang=zh-CN";
        try{
            URL url = new URL(api);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String data;
            while((data = reader.readLine())!=null){
                builder.append(data);
            }
            return builder.toString();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /** 网上找的gbk转utf-8 */
    private static byte[] getUtf8BytesFromGbkString(String gbkStr) {
        int n = gbkStr.length();
        /* utf-8字节数是gbk的3倍 */
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                /* 非中文 */
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }
}

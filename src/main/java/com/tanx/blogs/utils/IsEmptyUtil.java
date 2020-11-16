package com.tanx.blogs.utils;

import com.tanx.blogs.service.archives.ArchivesService;
import com.tanx.blogs.service.daily.DailyService;
import com.tanx.blogs.service.data.GetData;
import com.tanx.blogs.service.tag.TagService;
import com.tanx.blogs.service.type.TypeService;
import com.tanx.blogs.service.user.UserService;
import com.tanx.blogs.service.visitor.VisitorService;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author tanxiang
 */
public class IsEmptyUtil {
    public static Boolean isStringEmpty(String str) {
        return isStringEmpty(str, "不为空!");
    }

    public static Boolean isStringEmpty(String str1, String str2) {
        return (str1 == null || "".equals(str1) || str2 == null || "".equals(str2));
    }

    public static Boolean isNumber(String str1){
        return !isStringEmpty(str1) && str1.matches("\\d+");
    }

    public static int isUrlRight(String urlData){
        try {
            if(IsEmptyUtil.isStringEmpty(urlData)){
                return 404;
            }else{
                if(urlData.contains("https://")){
                    HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(urlData).openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    return urlConnection.getResponseCode();
                }else if(urlData.contains("http://")){
                    HttpURLConnection urlConnection = (HttpURLConnection) new URL(urlData).openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    return urlConnection.getResponseCode();
                }else{
                    return 500;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 500;
        }
    }

    public static boolean isBoolean(String data){
        return "true".equalsIgnoreCase(data) || "false".equalsIgnoreCase(data);
    }
}

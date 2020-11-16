package com.tanx.blogs.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存工具类
 * @author tanxiang
 * @version 1.0
 * @date 2020/10/25 下午7:00
 */
public class MapCacheUtil {
    private static final Map<String,Object> MAP_CACHE = new HashMap<>(16,0.75f);

    public static void set(String key,Object value){
        if(key == null || "".equals(key)){
            throw new RuntimeException("传入key异常！");
        }

        MAP_CACHE.put(key, value);
    }

    public static Object get(String key){
        return MAP_CACHE.get(key);
    }

    public static void remove(String key){
        MAP_CACHE.remove(key);
    }
}

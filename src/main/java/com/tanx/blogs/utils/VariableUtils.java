package com.tanx.blogs.utils;


import com.alibaba.druid.pool.PreparedStatementPool;
import com.alibaba.druid.support.json.JSONUtils;
import com.tanx.blogs.pojo.model.Visitor;
import org.lionsoul.ip2region.DbSearcher;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author tanxiang
 */
@SuppressWarnings("all")
public class VariableUtils {
    public static final List<Map<String, Object>> LIST = new ArrayList<>(16);
    public static final List<Map<String,Object>> DAILY_LIST = new ArrayList<>(16);
    public static final List<Map<String,Object>> LOG_LIST = new ArrayList<>(16);
    public static final List<Map<String,Object>> FOOTMARK_LIST = new ArrayList<>(16);
    public static final int BTREE = DbSearcher.BTREE_ALGORITHM;
    public static final int BINARY = DbSearcher.BINARY_ALGORITHM;
    public static final int MEMORY = DbSearcher.MEMORY_ALGORITYM;
    public static boolean active;
    public static String loginName;
    public static final String DEV_URL = "F:" + File.separator + "archives";
    public static final String PROD_URL = File.separator + "usr" + File.separator + "local" + File.separator + "blogs" + File.separator + "archives";
    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;
    public static final Integer MINUSONE = -1;
    public static final Integer SHWODPAGE = 5;
    public static final Map<String, String> MAP = new HashMap<>(16, 75.0F);
    public static final String REGEXEMAIL = "^\\w+((-\\w)|(\\.\\w))*@[A-Za-z0-9]+((\\.\\|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    public static final String LINUXPATH = File.separator + "onePeopleBlog" + File.separator + "blogs" + File.separator + "archives";
    public static final String WINDOWSPATH = "C:" + File.separator + "OnePeopleBlogs" + File.separator + "blogs" + File.separator + "archives";
    public static final String LINUXIMAGEPATH = File.separator + "image" + File.separator + "files";
    public static final String WINDOWSIAMGEPATH = "C:" + File.separator + "image" + File.separator + "files";
    public static final String ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    public static final String ACCESSKEYID = "LTAI4FzZHPq86RpS3FqTtbbc";
    public static final Map<String, Visitor> IP_MAP = new HashMap<String,Visitor>();

    static {
        MAP.putIfAbsent("notice", "暂无！");
    }
    public static final String ACCESSKEYSECRET = "mnv5Erp2oUer6t8A3mVVR4prFW0htX"; public static final String BUCKETNAME = "tanxiang"; public static final String OBJECTNAME = "images/";
    public static String minusOne() {
        return JSONUtils.toJSONString(InfoUtil.info(MINUSONE, "\u6570\u636E\u5F02\u5E38\uFF01"));
    }
}

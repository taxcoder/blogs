package com.tanx.blogs.utils;

import java.util.LinkedHashSet;
import java.util.Set;

public class FilterDataUtil
{
    private static final Set<String> set = new LinkedHashSet<>();

    public static Set<String> getSet() {
        return set;
    }

    public static String filterTrimAndFeed(String data) {
        return data.replaceAll("/r", "").replaceAll("/n", "").replaceAll(" ", "");
    }

    public static void addSetSensitive(String data) {
        set.add(data);
    }

    public static String isSensitive(String data) {
        for (String s : set) {
            if (data.contains(s)) {
                return filterTrimAndFeed(changeSensitive(data, s));
            }
        }
        return data;
    }

    public static String changeSensitive(String data, String str) {
        return data.replaceAll(str, "*");
    }

    public static boolean isSensitiveTrue(String data) {
        for (String s : set) {
            if (data.contains(s)) {
                return true;
            }
        }
        return false;
    }
}

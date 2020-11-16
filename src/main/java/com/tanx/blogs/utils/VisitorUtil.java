package com.tanx.blogs.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tanxiang
 */
public class VisitorUtil {
    public static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    public static UserAgent getUserAgent(HttpServletRequest request) {
        return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }
    /**获取浏览器**/
    public static Browser getBrowser(HttpServletRequest request) {
        return getUserAgent(request).getBrowser();
    }

    /**获取浏览器的版本号*/
    public static String getBrowserVersion(HttpServletRequest request) {
        Version browserVersion = getUserAgent(request).getBrowserVersion();

        if(browserVersion!=null){
            return browserVersion.getVersion();
        }

        String header = request.getHeader("User-Agent");
        String trim = getBrowser(request).getName().split(" ")[0].trim();
        System.out.println("VUtil-header:"+header);
        System.out.println("VUtil-trim.contains(header):"+trim.contains(header));
        System.out.println("VUtil-trim:"+trim);
        if(!trim.contains(header)){
            return null;
        }
        System.out.println("VUtil-header.substring(header.indexOf(trim)).split(\" \")[0].split(\"/\")[1]:"+header.substring(header.indexOf(trim)).split(" ")[0].split("/")[1]);
        return header.substring(header.indexOf(trim)).split(" ")[0].split("/")[1];
    }
    /**获取设备的系统*/
    public static String getOperatingSystem(HttpServletRequest request) {
        return getUserAgent(request).getOperatingSystem().getName();
    }
    /**获取设备类型*/
    public static DeviceType getDeviceType(HttpServletRequest request){
        return getUserAgent(request).getOperatingSystem().getDeviceType();
    }

     /**判断是否是PC*/
    public static boolean isComputer(HttpServletRequest request){
        return DeviceType.COMPUTER.equals(getDeviceType(request));
    }
     /**判断是否是手机*/
    public static boolean isMobile(HttpServletRequest request){
        return DeviceType.MOBILE.equals(getDeviceType(request));
    }
     /**判断是否是平板*/
    public static boolean isTablet(HttpServletRequest request){
        return DeviceType.TABLET.equals(getDeviceType(request));
    }
}

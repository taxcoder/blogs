package com.tanx.blogs.pojo.model;

public class Visitor {
    private long visitorId;
    private String visitorIp;
    private String operationSystem;
    private String browser;
    private String browserVersion;
    private String country;
    private String province;
    private String city;

    public Visitor() {}

    public Visitor(String visitorIp, String operationSystem, String browser, String browserVersion) {
        this.visitorIp = visitorIp;
        this.operationSystem = operationSystem;
        this.browser = browser;
        this.browserVersion = browserVersion;
    }

    public Visitor(long visitorId, String visitorIp, String operationSystem, String browser, String browserVersion) {
        this.visitorId = visitorId;
        this.visitorIp = visitorIp;
        this.operationSystem = operationSystem;
        this.browser = browser;
        this.browserVersion = browserVersion;
    }

    public Visitor(long visitorId, String visitorIp, String operationSystem, String browser, String browserVersion, String country, String province, String city) {
        this.visitorId = visitorId;
        this.visitorIp = visitorIp;
        this.operationSystem = operationSystem;
        this.browser = browser;
        this.browserVersion = browserVersion;
        this.country = country;
        this.province = province;
        this.city = city;
    }

    public Visitor(String visitorIp, String operationSystem, String browser, String browserVersion, String country, String province, String city) {
        this.visitorIp = visitorIp;
        this.operationSystem = operationSystem;
        this.browser = browser;
        this.browserVersion = browserVersion;
        this.country = country;
        this.province = province;
        this.city = city;
    }

    public long getVisitorId() {
        return this.visitorId;
    }

    public void setVisitorId(long visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitorIp() {
        return this.visitorIp;
    }

    public void setVisitorIp(String visitorIp) {
        this.visitorIp = visitorIp;
    }

    public String getOperationSystem() {
        return this.operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getBrowser() {
        return this.browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowserVersion() {
        return this.browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Visitor{visitorId=" + this.visitorId + ", visitorIp='" + this.visitorIp + '\'' + ", operationSystem='" + this.operationSystem + '\'' + ", browser='" + this.browser + '\'' + ", browserVersion='" + this.browserVersion + '\'' + ", country='" + this.country + '\'' + ", province='" + this.province + '\'' + ", city='" + this.city + '\'' + '}';
    }
}

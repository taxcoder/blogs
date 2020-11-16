package com.tanx.blogs.pojo.dto;

public class VisitorInfo {
    private String ip;
    private String browser;
    private String browserVersion;
    private String operationSystem;

    public VisitorInfo() {}

    public VisitorInfo(String ip, String browser, String browserVersion, String operationSystem) {
        this.ip = ip;
        this.browser = browser;
        this.browserVersion = browserVersion;
        this.operationSystem = operationSystem;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBrowser() {
        return this.browser;
    }

    public String getOperationSystem() {
        return this.operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
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

    @Override
    public String toString() {
        return "VisitorInfo{ip='" + this.ip + '\'' + ", browser='" + this.browser + '\'' + ", browserVersion='" + this.browserVersion + '\'' + ", operationSystem='" + this.operationSystem + '\'' + '}';
    }
}

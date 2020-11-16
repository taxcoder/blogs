package com.tanx.blogs.pojo.model;

import java.util.Date;

public class User {
    private long userId;
    private String loginName;
    private String username;
    private String password;
    private boolean rank;
    private String sign;
    private String address;
    private String headImg;
    private Date createTime;
    private Date changeTime;
    private String email;
    private boolean active;

    public User() {

    }

    public User(String loginName, String username, String password, String email) {
        this.loginName = loginName;
        this.username = username;
        this.password = password;
        this.email = email;
    }



    public User(String username, String sign, String address, String headImg, String email) {
        this.username = username;
        this.sign = sign;
        this.address = address;
        this.headImg = headImg;
        this.email = email;
    }

    public User(String loginName, String username, String password, Date createTime, Date changeTime, String email) {
        this.loginName = loginName;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
        this.changeTime = changeTime;
        this.email = email;
    }

    public User(long userId, String loginName, String username, String password, boolean rank, String sign, String address, String headImg, Date createTime, Date changeTime, String email, boolean active) {
        this.userId = userId;
        this.loginName = loginName;
        this.username = username;
        this.password = password;
        this.rank = rank;
        this.sign = sign;
        this.address = address;
        this.headImg = headImg;
        this.createTime = createTime;
        this.changeTime = changeTime;
        this.email = email;
        this.active = active;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getRank() {
        return this.rank;
    }

    public void setRank(boolean rank) {
        this.rank = rank;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadImg() {
        return this.headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Date getCreateTime() {
        return new Date();
    }

    public Date getChangeTime() {
        return new Date();
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{userId=" + this.userId + ", loginName='" + this.loginName + '\'' + ", username='" + this.username + '\'' + ", password='" + this.password + '\'' + ", rank=" + this.rank + ", sign='" + this.sign + '\'' + ", address='" + this.address + '\'' + ", headImg='" + this.headImg + '\'' + ", createTime=" + this.createTime + ", changeTime=" + this.changeTime + ", email='" + this.email + '\'' + ", active=" + this.active + '}';
    }
}


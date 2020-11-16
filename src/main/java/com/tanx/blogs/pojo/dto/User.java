package com.tanx.blogs.pojo.dto;


public class User {
    private long id;
    private String loginName;
    private String username;
    private String password;
    private String email;
    private String code;

    public User() {}

    public User(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String loginName, String email) {
        this.loginName = loginName;
        this.email = email;
    }

    public User(String loginName, String username, String password) {
        this.loginName = loginName;
        this.username = username;
        this.password = password;
    }

    public User(String loginName, String username, String password, String email) {
        this.loginName = loginName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(long id, String loginName, String username, String password, String email, String code) {
        this.id = id;
        this.loginName = loginName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.code = code;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "User{id=" + this.id + ", loginName='" + this.loginName + '\'' + ", username='" + this.username + '\'' + ", password='" + this.password + '\'' + ", email='" + this.email + '\'' + ", code='" + this.code + '\'' + '}';
    }
}
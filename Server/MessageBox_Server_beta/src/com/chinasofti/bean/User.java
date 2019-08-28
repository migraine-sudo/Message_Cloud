package com.chinasofti.bean;

public class User {
    private String userid;
    private  String username;
    private  String password;
    private int code;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User() {

    }
    public User(String userid,String username,String password) {
        this.userid=userid;
        this.username=username;
        this.password=password;
    }
}
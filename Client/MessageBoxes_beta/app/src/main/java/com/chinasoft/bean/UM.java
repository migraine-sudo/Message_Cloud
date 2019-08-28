package com.chinasoft.bean;

public class UM {
    private String id;
    private  String username;
    private  String phone;
    private int code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UM() {

    }
    public UM(String id,String username,String phone) {
        this.id=id;
        this.username=username;
        this.phone=phone;
    }
}
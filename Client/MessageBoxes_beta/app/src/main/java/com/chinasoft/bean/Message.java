package com.chinasoft.bean;

public class Message {
    private String Mid;
    private String message;
    private String phone;
    private int code;

    public String getMid() {
        return Mid;
    }

    public void setMid(String mid) {
        Mid = mid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Message() {
    }
    public Message(String mid,String message,String phone) {
        this.Mid=mid;
        this.message=message;
        this.phone=phone;
    }
}

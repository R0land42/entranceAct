package com.example.entranceact;

public class MessageInfo {
    public String msg, name, time;

    public MessageInfo() {
    }

    public MessageInfo(String msg, String name, String time) {
        this.msg = msg;
        this.name = name;
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

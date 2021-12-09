package com.example.entranceact;

public class CurentUser {
    public String curentLog, curentName;

    public CurentUser() {
    }

    public CurentUser(String curentLog, String curentName) {
        this.curentLog = curentLog;
        this.curentName = curentName;
    }

    public String getCurentLog() {
        return curentLog;
    }

    public void setCurentLog(String curentLog) {
        this.curentLog = curentLog;
    }

    public String getCurentName() {
        return curentName;
    }

    public void setCurentName(String curentName) {
        this.curentName = curentName;
    }
}

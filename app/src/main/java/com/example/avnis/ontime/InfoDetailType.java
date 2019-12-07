package com.example.avnis.ontime;

public class InfoDetailType {
    private String date;
    private String time;
    private String status;

    public InfoDetailType(String date,String time,String status) {
        this.time=time;
        this.date=date;
        this.status=status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

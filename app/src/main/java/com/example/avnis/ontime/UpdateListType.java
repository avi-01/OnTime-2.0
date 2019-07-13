package com.example.avnis.ontime;

/**
 * Created by avnis on 7/7/2019.
 */
public class UpdateListType {
    private String time;
    private String name;
    private String att;
    private String mes;
    private String per;
    private String status;
    public UpdateListType(String time,String name,String att,String mes,String per,String status) {
        this.time=time;
        this.name=name;
        this.att=att;
        this.mes=mes;
        this.per=per;
        this.status=status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}


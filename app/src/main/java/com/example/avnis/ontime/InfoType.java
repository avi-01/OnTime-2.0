package com.example.avnis.ontime;

/**
 * Created by avnis on 7/7/2019.
 */
public class InfoType {
    private String name;
    private String att;
    private String mes;
    private String per;
    public InfoType(String name, String att, String mes, String per) {
        this.name=name;
        this.att=att;
        this.mes=mes;
        this.per=per;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }
}


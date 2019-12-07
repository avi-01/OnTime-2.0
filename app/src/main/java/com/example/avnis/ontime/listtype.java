package com.example.avnis.ontime;

/**
 * Created by avnis on 7/6/2019.
 */
public class listtype {
    private String time;
    private String name;
    private String dur;

    public listtype(String time,String name,String dur) {
        this.time=time;
        this.name=name;
        this.dur=dur;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDur() {
        return dur;
    }

    public void setDur(String dur) {
        this.dur = dur;
    }
}

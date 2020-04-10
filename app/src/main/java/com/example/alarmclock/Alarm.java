package com.example.alarmclock;

public class Alarm {
    private int id;
    private String hour;
    private String min;
    private int enable;

    public Alarm(int id, String hour, String min, int enable) {
        this.id = id;
        this.hour = hour;
        this.min = min;
        this.enable = enable;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}

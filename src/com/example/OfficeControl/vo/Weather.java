package com.example.OfficeControl.vo;

import android.graphics.Bitmap;

/**
 * Created by john on 5/26/2016.
 */
public class Weather {

    private String currTemp;
    private String date;
    private String day;
    private Bitmap pic1;
    private Bitmap pic2;
    private String condition;
    private String wind;
    private String temp;

    public String getCurrTemp() {
        return currTemp;
    }

    public void setCurrTemp(String currTemp) {
        this.currTemp = currTemp;
    }


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }


    public Bitmap getPic2() {
        return pic2;
    }

    public void setPic2(Bitmap pic2) {
        this.pic2 = pic2;
    }

    public Bitmap getPic1() {
        return pic1;
    }

    public void setPic1(Bitmap pic1) {
        this.pic1 = pic1;
    }
}

package com.example.myweatherforecast.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherBean {
    @SerializedName("status")
    private String status;
    @SerializedName("count")
    private String count;
    @SerializedName("info")
    private String info;
    @SerializedName("lives")
    private List<DayWeatherBean> dayWeathers;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public List<DayWeatherBean> getDayWeathers() {
        return dayWeathers;
    }
    public void setDayWeathers(List<DayWeatherBean> dayWeathers) {
        this.dayWeathers = dayWeathers;
    }
    @Override
    public String toString() {
        return "WeatherBean{" +
                "status='" + status + '\'' +
                ", count='" + count + '\'' +
                ", info='" + info + '\'' +
                ", dayWeathers=" + dayWeathers +
                '}';
    }
}


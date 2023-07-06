package com.example.myweatherforecast.bean;

import com.google.gson.annotations.SerializedName;

public class DayWeatherBean {
    @SerializedName("province")
    private String province;

    @SerializedName("city")
    private String city;

    @SerializedName("adcode")
    private String adcode;

    @SerializedName("weather")
    private String weather;

    @SerializedName("temperature")
    private String temperature;

    @SerializedName("winddirection")
    private String winddirection;

    @SerializedName("windpower")
    private String windpower;

    @SerializedName("humidity")
    private String humidity;

    @SerializedName("reporttime")
    private String reporttime;

    @SerializedName("temperature_float")
    private String temperature_float;

    @SerializedName("humidity_float")
    private String humidity_float;


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWinddirection() {
        return winddirection;
    }

    public void setWinddirection(String winddirection) {
        this.winddirection = winddirection;
    }

    public String getWindpower() {
        return windpower;
    }

    public void setWindpower(String windpower) {
        this.windpower = windpower;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public String getTemperature_float() {
        return temperature_float;
    }

    public void setTemperature_float(String temperature_float) {
        this.temperature_float = temperature_float;
    }

    public String getHumidity_float() {
        return humidity_float;
    }

    public void setHumidity_float(String humidity_float) {
        this.humidity_float = humidity_float;
    }

    @Override
    public String toString() {
        return "DayWeatherBean{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", adcode='" + adcode + '\'' +
                ", weather='" + weather + '\'' +
                ", temperature='" + temperature + '\'' +
                ", winddirection='" + winddirection + '\'' +
                ", windpower='" + windpower + '\'' +
                ", humidity='" + humidity + '\'' +
                ", reporttime='" + reporttime + '\'' +
                ", temperature_float='" + temperature_float + '\'' +
                ", humidity_float='" + humidity_float + '\'' +
                '}';
    }
}


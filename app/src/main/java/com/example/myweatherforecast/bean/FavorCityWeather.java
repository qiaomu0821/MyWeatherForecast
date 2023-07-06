package com.example.myweatherforecast.bean;

public class FavorCityWeather {
    private String city;
    private String cityAdcode;
    private String tem;
    private String weather;
    private String humidity;
    public String getCityAdcode() {
        return cityAdcode;
    }
    public void setCityAdcode(String cityAdcode) {
        this.cityAdcode = cityAdcode;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getTem() {
        return tem;
    }
    public void setTem(String tem) {
        this.tem = tem;
    }
    public String getWeather() {
        return weather;
    }
    public void setWeather(String weather) {
        this.weather = weather;
    }
    public String getHumidity() {
        return humidity;
    }
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
    @Override
    public String toString() {
        return "FavorCityWeather{" +
                "city='" + city + '\'' +
                ", cityAdcode='" + cityAdcode + '\'' +
                ", tem='" + tem + '\'' +
                ", weather='" + weather + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}


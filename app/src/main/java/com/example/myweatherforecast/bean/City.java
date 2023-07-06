package com.example.myweatherforecast.bean;

public class City {
    private String name;
    private String adcode;

    public City(String name, String adcode) {
        this.name = name;
        this.adcode = adcode;
    }

    public City(String name) {
        this.name = name;
    }

    public City() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", adcode='" + adcode + '\'' +
                '}';
    }
}


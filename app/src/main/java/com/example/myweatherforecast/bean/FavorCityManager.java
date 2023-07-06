package com.example.myweatherforecast.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FavorCityManager {
    private SharedPreferences sharedPreferences;
    private Gson gson;
    public FavorCityManager(Context context) {
        sharedPreferences = context.getSharedPreferences("favorCity", Context.MODE_PRIVATE);
        gson = new Gson();
    }
    //存缓存结果
    public void setQueryResult(FavorCityWeather favorCityWeather){
        List<FavorCityWeather> result = new ArrayList<>();
        result = getQueryResult();
        for (FavorCityWeather f:result) {
            if (TextUtils.equals(favorCityWeather.getCity(),f.getCity())){
                return;
            }
        }
        result.add(favorCityWeather);
        saveQueryResult(result);
    }
    //读缓存结果
    public List<FavorCityWeather> getQueryResult() {
        String json = sharedPreferences.getString("favorCity","");
        List<FavorCityWeather> list = new ArrayList<>();
        if (!json.equals("")){
            list = gson.fromJson(json, new TypeToken<List<FavorCityWeather>>() {
            }.getType());
        }
        return list;
    }
    private void saveQueryResult(List<FavorCityWeather> result) {
        String json = gson.toJson(result);
        SharedPreferences.Editor editor = sharedPreferences.edit().putString("favorCity", json);
        editor.apply();
    }
    public void deleteQueryResult(int i) {
        List<FavorCityWeather> queryResult = getQueryResult();
        queryResult.remove(i);
        saveQueryResult(queryResult);
    }
}


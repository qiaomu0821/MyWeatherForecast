package com.example.myweatherforecast.bean;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class QueryManager {
    private SharedPreferences sharedPreferences;
    private Gson gson;
    public QueryManager(Context context) {
        sharedPreferences = context.getSharedPreferences("QueryManager", Context.MODE_PRIVATE);
        gson = new Gson();
    }
    //存缓存结果
    public void setQueryResult(WeatherBean queryResult){
        List<WeatherBean> result = new ArrayList<>();
        result = getQueryResult();
        result.add(queryResult);
        if (result.size() > 3){
            result.remove(0);
        }
        saveQueryResult(result);
    }
    //读缓存结果
    public List<WeatherBean> getQueryResult() {
        String json = sharedPreferences.getString("queryResult","");
        List<WeatherBean> list = new ArrayList<>();
        if (!json.equals("")){
            list = gson.fromJson(json, new TypeToken<List<WeatherBean>>() {
            }.getType());
        }
        return list;
    }
    private void saveQueryResult(List<WeatherBean> result) {
        String json = gson.toJson(result);
        SharedPreferences.Editor editor = sharedPreferences.edit().putString("queryResult", json);
        editor.apply();
    }

}


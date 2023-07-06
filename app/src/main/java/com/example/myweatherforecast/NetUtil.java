package com.example.myweatherforecast;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtil {
    public static String doGet(String urlStr){
        String result = "";

        HttpURLConnection connection = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        // 连接网络
        try {
            URL urL = new URL(urlStr);
            connection = (HttpURLConnection) urL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            // 从连接中读取数据(二进制)
            InputStream inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            // 二进制流送入缓冲区
            bufferedReader = new BufferedReader(inputStreamReader);

            // 从缓存区中一行行读取字符串
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
    public static String getWeatherOfCity(String cityAdcode){
        // 拼接出获取天气数据的URL
//        String weatherUrl = URL_WEATHER_WITH_FUTURE + "&city=" + city;
        String weatherUrl = "https://restapi.amap.com/v3/weather/weatherInfo?city=" + cityAdcode + "&key=d0e9943b4c520f170a3c4cbad548294f&extensions=base";
        String weatherResult = doGet(weatherUrl);
        Log.d("gao","----返回的值----"+weatherResult);
        return weatherResult;
    }
}


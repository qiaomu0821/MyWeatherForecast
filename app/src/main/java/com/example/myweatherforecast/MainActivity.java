package com.example.myweatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweatherforecast.adapter.MyAdapter;
import com.example.myweatherforecast.bean.City;
import com.example.myweatherforecast.bean.DayWeatherBean;
import com.example.myweatherforecast.bean.QueryManager;
import com.example.myweatherforecast.bean.WeatherBean;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView tvCity,tvWeather, tvTem, tvWin, tvHumidity,tvReporttime;
    //    private ImageView ivWeather;
    private EditText etSearch;
    private ImageView ivSearch,ivRefresh,ivFavor;
    private QueryManager queryManager;
    private ExpandableListView elvCity;
    private MyAdapter myadapter;
    private List<String> cityGroup;
    private Map<String,List<City>> cityChild;
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String weather = (String) msg.obj;
                if (TextUtils.isEmpty(weather) || !weather.contains("province")) {
                    Toast.makeText(MainActivity.this, "天气数据为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(weather, WeatherBean.class);
                updateUiOfWeather(weatherBean);
                queryManager.setQueryResult(weatherBean);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queryManager = new QueryManager(MainActivity.this);
        //初始化
        initAdapter();
        initView();
        initEvent();
        List<WeatherBean> queryResult = queryManager.getQueryResult();
        if (queryResult != null){
            WeatherBean weatherBean = queryResult.get(queryResult.size() - 1);
            String adcode = weatherBean.getDayWeathers().get(0).getAdcode();
            getWeatherOfCity(adcode);
        }
    }

    private void initAdapter() {
        cityGroup = new ArrayList<>();
        cityChild = new HashMap<>();
        try {
            InputStream inputStream = getAssets().open("city.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String province = "";
            String province_number = "";
            List<List<City>> cityList = new ArrayList<>();
            int index = -1;
            while ((line = reader.readLine()) != null) {
                String[] str = line.split("\t");
                String city = str[0];
                String number = str[1];
                String cityCode = str[2];
                if(cityCode.equals("0")){
                    if(index!=-1)
                        cityChild.put(cityGroup.get(index),cityList.get(index));
                    cityGroup.add(city);
                    cityList.add(new ArrayList<>());
                    ++index;
                }
                else{
                    City citySP = new City(city, number);
                    cityList.get(index).add(citySP);
                }
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        tvCity = findViewById(R.id.frag_tv_city);
        tvTem = findViewById(R.id.frag_tv_currenttemp);
        tvWeather = findViewById(R.id.frag_tv_condition);
        tvWin = findViewById(R.id.frag_tv_wind);
        tvHumidity = findViewById(R.id.frag_tv_humidity);
        tvReporttime = findViewById(R.id.frag_tv_date);
        etSearch = findViewById(R.id.main_et_searchtext);
        ivSearch = findViewById(R.id.main_iv_search);
        elvCity = findViewById(R.id.main_elv_city);
    }
    private void getWeatherOfCity(String selectedCity) {
        // 开启子线程，请求网络
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 请求网络
                String weatherOfCity = NetUtil.getWeatherOfCity(selectedCity);
                // 使用handler将数据传递给主线程
                Message message = Message.obtain();
                message.what = 0;
                message.obj = weatherOfCity;
                mHandler.sendMessage(message);
            }
        }).start();
    }
    private void initEvent() {
        myadapter = new MyAdapter(MainActivity.this,cityGroup,cityChild);
        elvCity.setAdapter(myadapter);
        //省、市两级行政单位的浏览界面的点击事件
        elvCity.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String s = cityGroup.get(i);
                List<City> cityList = cityChild.get(s);
                City city = cityList.get(i1);
                getWeatherOfCity(city.getAdcode());
                return false;
            }
        });
        //搜索的点击事件
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityAdcode = etSearch.getText().toString();
                boolean query_flag = true;
                List<WeatherBean> queryResult = queryManager.getQueryResult();
                if (queryResult != null){
                    for (WeatherBean weatherBean:queryResult) {
                        String adcode = weatherBean.getDayWeathers().get(0).getAdcode();
                        //有记录
                        if (TextUtils.equals(adcode,cityAdcode)){
                            query_flag = false;
                            updateUiOfWeather(weatherBean);
                            break;
                        }
                    }
                    //没有记录,访问api
                }
                if (query_flag) {
                    getWeatherOfCity(cityAdcode);
                }
            }
        });
    }
    //更新UI
    private void updateUiOfWeather(WeatherBean weatherBean) {
        if (weatherBean == null){
            return;
        }
        List<DayWeatherBean> dayWeathers = weatherBean.getDayWeathers();
        DayWeatherBean todayWeather = dayWeathers.get(0);
        if (todayWeather == null){
            return;
        }
        tvCity.setText(todayWeather.getProvince() + todayWeather.getCity());
        tvTem.setText(todayWeather.getTemperature() + "°C");
        tvWeather.setText(todayWeather.getWeather());
        tvWin.setText(todayWeather.getWinddirection() + "风" + todayWeather.getWindpower() + "级");
        tvHumidity.setText(todayWeather.getHumidity() + "%");
        tvReporttime.setText(todayWeather.getReporttime());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_refresh) {
            List<WeatherBean> queryResult = queryManager.getQueryResult();
            if (queryResult == null){
                return false;
            }
            WeatherBean weatherBean = queryResult.get(queryResult.size() - 1);
            String adcode = weatherBean.getDayWeathers().get(0).getAdcode();
            getWeatherOfCity(adcode);
            Toast.makeText(MainActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
        }
        else if(itemId == R.id.menu_favor){
            Intent intent = new Intent(MainActivity.this,FavorActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
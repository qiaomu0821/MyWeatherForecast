package com.example.myweatherforecast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myweatherforecast.adapter.ListViewAdapter;
import com.example.myweatherforecast.bean.DayWeatherBean;
import com.example.myweatherforecast.bean.FavorCityManager;
import com.example.myweatherforecast.bean.FavorCityWeather;
import com.example.myweatherforecast.bean.WeatherBean;
import com.google.gson.Gson;

import java.util.List;

public class FavorActivity extends AppCompatActivity {
    private ImageView ivAdd;
    private EditText etSearch;
    private ListView lvFavor;
    private ListViewAdapter listViewAdapter;
    private List<FavorCityWeather> favorCityWeathers;
    private FavorCityWeather favorCityWeather;
    private FavorCityManager favorCityManger;
    private boolean flag = false;
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String weather = (String) msg.obj;
                if (TextUtils.isEmpty(weather) || !weather.contains("province")) {
                    Toast.makeText(FavorActivity.this, "天气数据为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(weather, WeatherBean.class);
                List<DayWeatherBean> dayWeathers = weatherBean.getDayWeathers();
                favorCityWeather.setCity(dayWeathers.get(0).getCity());
                favorCityWeather.setTem(dayWeathers.get(0).getTemperature() + "°C");
                favorCityWeather.setWeather(dayWeathers.get(0).getWeather());
                favorCityWeather.setHumidity(dayWeathers.get(0).getHumidity() + "%");
                favorCityWeather.setCityAdcode(dayWeathers.get(0).getAdcode());
                if (flag){
                    updateUiOfWeather(favorCityWeather);
                    Toast.makeText(FavorActivity.this,"添加城市成功",Toast.LENGTH_SHORT).show();
                    favorCityManger.setQueryResult(favorCityWeather);
                    flag = false;
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);
        initView();
        initData();
        initEvent();
    }
    private void initEvent() {
        //添加的点击事件
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityString = etSearch.getText().toString();
                flag = true;
                getWeatherOfCity(cityString);
            }
        });

        lvFavor.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(FavorActivity.this,"你点击了第" + i + "个",Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder normalDialog = new AlertDialog.Builder(FavorActivity.this);
                normalDialog.setTitle("注意");
                normalDialog.setMessage("是否选择删除");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                favorCityWeathers.remove(i);
                                lvFavor.setAdapter(listViewAdapter);
                                favorCityManger.deleteQueryResult(i);
                                listViewAdapter.notifyDataSetChanged();
                            }
                        });
                normalDialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                // 显示
                normalDialog.show();
                return true;
            }
        });
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

    private void updateUiOfWeather(FavorCityWeather favorCityWeather) {
        //不能收藏重复城市
        for (FavorCityWeather f:favorCityWeathers) {
            if (TextUtils.equals(favorCityWeather.getCity(),f.getCity())){
                Toast.makeText(FavorActivity.this,"updateUiOfWeather该城市已存在",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        favorCityWeathers.add(favorCityWeather);
        lvFavor.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();
    }
    private void initData() {
        //初始化favorCityWeathers
        favorCityWeathers = favorCityManger.getQueryResult();
        //更新weather信息
        for (FavorCityWeather f:favorCityWeathers) {
            getWeatherOfCity(f.getCityAdcode());
        }
        //初始化adapter
        listViewAdapter = new ListViewAdapter(FavorActivity.this,favorCityWeathers);
        lvFavor.setAdapter(listViewAdapter);
    }
    private void initView() {
        ivAdd = findViewById(R.id.favor_iv_add);
        etSearch = findViewById(R.id.favor_et_searchtext);
        lvFavor = findViewById(R.id.favor_elv_city);
        favorCityWeather = new FavorCityWeather();
        favorCityManger = new FavorCityManager(FavorActivity.this);
    }
}

package com.example.myweatherforecast.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myweatherforecast.R;
import com.example.myweatherforecast.bean.FavorCityWeather;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private List<FavorCityWeather> favorCityWeathers;

    public ListViewAdapter(Context context, List<FavorCityWeather> favorCityWeathers) {
        this.context = context;
        this.favorCityWeathers = favorCityWeathers;
    }

    @Override
    public int getCount() {
        return favorCityWeathers.size();
    }

    @Override
    public Object getItem(int i) {
        return favorCityWeathers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = View.inflate(context, R.layout.item_favor,null);
            viewHolder = new ViewHolder();
            viewHolder.tvCityFavor = view.findViewById(R.id.item_favor_tv_city);
            viewHolder.tvTemFavor = view.findViewById(R.id.item_favor_tv_temp);
            viewHolder.tvWeatherFavor = view.findViewById(R.id.item_favor_tv_condition);
            viewHolder.tvHumidityFavor = view.findViewById(R.id.item_favor_humidity);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvCityFavor.setText(favorCityWeathers.get(i).getCity());
        viewHolder.tvTemFavor.setText(favorCityWeathers.get(i).getTem());
        viewHolder.tvWeatherFavor.setText(favorCityWeathers.get(i).getWeather());
        viewHolder.tvHumidityFavor.setText(favorCityWeathers.get(i).getHumidity());
        return view;
    }
    static class ViewHolder{
        TextView tvCityFavor;
        TextView tvTemFavor;
        TextView tvWeatherFavor;
        TextView tvHumidityFavor;
    }
}


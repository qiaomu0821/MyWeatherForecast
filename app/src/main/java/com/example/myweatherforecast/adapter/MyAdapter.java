package com.example.myweatherforecast.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.myweatherforecast.R;
import com.example.myweatherforecast.bean.City;
import com.example.myweatherforecast.bean.FavorCityManager;

import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseExpandableListAdapter {
    private Context context;

    private List<String> cityGroup;
    private Map<String,List<City>> cityChild;

    private FavorCityManager favorCityManger;

    public MyAdapter(Context context, List<String> cityGroup,Map<String,List<City>> cityChild) {
        this.context = context;
        this.cityGroup = cityGroup;
        this.cityChild = cityChild;
    }

    @Override
    //获取分组个数
    public int getGroupCount() {
        return cityGroup.size();
    }

    @Override
    //分组中子选项个数为1
    public int getChildrenCount(int i) {
        String groupName = cityGroup.get(i);
        if (cityChild.containsKey(groupName)){
            return cityChild.get(groupName).size();
        }
        return 0;
    }

    @Override
    //获取指定分组数据
    public Object getGroup(int i) {
        return cityGroup.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        String groupName = cityGroup.get(i);
        if (cityChild.containsKey(groupName)){
            return cityChild.get(groupName);
        }
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        View view;
        GroupHolder groupHolder;
        if (convertView == null){
            //父布局
            view = View.inflate(context, R.layout.item_exlist_city,null);
            groupHolder = new GroupHolder();
            groupHolder.tvCityGroup = view.findViewById(R.id.tv_cityGroup);
            view.setTag(groupHolder);
        } else {
            view = convertView;
            groupHolder = (GroupHolder) view.getTag();
        }
        groupHolder.tvCityGroup.setText(cityGroup.get(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        View view;
        ChildHolder childHolder;
        if (convertView == null){
            //父布局
            view = View.inflate(context, R.layout.item_exlist_area,null);
            childHolder = new ChildHolder();
            childHolder.tvCityChild = view.findViewById(R.id.tv_city_child);
            view.setTag(childHolder);
        } else {
            view = convertView;
            childHolder = (ChildHolder) view.getTag();
        }

        String groupKey = cityGroup.get(i);
        List<City> child = cityChild.get(groupKey);

        childHolder.tvCityChild.setText(child.get(i1).getName());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    static class GroupHolder {
        TextView tvCityGroup;
    }

    static class ChildHolder {
        TextView tvCityChild;
    }
}


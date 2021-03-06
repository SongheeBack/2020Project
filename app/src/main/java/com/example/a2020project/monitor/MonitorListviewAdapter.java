package com.example.a2020project.monitor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a2020project.R;

import java.util.ArrayList;

public class MonitorListviewAdapter extends BaseAdapter {
    private ArrayList<MonitorListview> monitorListviewlist = new ArrayList<MonitorListview>();
    TextView deviceNameView;
    TextView deviceDataView;
    int pos;
    public MonitorListviewAdapter(){

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount(){
        return monitorListviewlist.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        pos = position;
        final Context context = parent.getContext();

        // Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.monitor_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        deviceNameView = (TextView) convertView.findViewById(R.id.monitorDeviceName);
        deviceDataView = (TextView) convertView.findViewById(R.id.monitorDataP);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        MonitorListview monitorListview = monitorListviewlist.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        deviceNameView.setText(monitorListview.getDeviceName());
        deviceDataView.setText(monitorListview.getDeviceData());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }


    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return monitorListviewlist.get(position);
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(String name, String data){
        MonitorListview listviewItem = new MonitorListview();

        //String cName = listviewItem.getDeviceName();
         listviewItem.setDeviceName(name);
         listviewItem.setDeviceData(data);

        monitorListviewlist.add(listviewItem);
    }

    public void setdata(int i, String data){

        monitorListviewlist.get(i).setDeviceData(data);
    }



    public void clear(){
        monitorListviewlist.clear();
    }
}

package com.example.a2020project.ErrorData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a2020project.R;

import java.util.ArrayList;

public class ErrorListviewAdapter extends BaseAdapter {

    //Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ErrorListview> listViewItemList = new ArrayList<ErrorListview>();

    // ListViewAdapter의 생성자
    public ErrorListviewAdapter(){}

    // Adapter에 사용되는 데이터의 개수를 리턴 : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴 : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "Listview_item" Layout을 inflate하여 convertView 참조 획득.
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.error_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)로부터 위젯에 대한 참조 획득
        TextView LogData = (TextView) convertView.findViewById(R.id.textView1);
        TextView LogDataInform = (TextView) convertView.findViewById(R.id.textView2);

        // Data set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ErrorListview listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        LogData.setText(listViewItem.geterrData());
        LogDataInform.setText(listViewItem.geterrDataInform());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴 : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능
    public void addItem(String data, String inform){
        ErrorListview item = new ErrorListview();

        item.seterrData(data);
        item.seterrDataInform(inform);

        listViewItemList.add(item);
    }

    public void clear(){
        listViewItemList.clear();
    }
}

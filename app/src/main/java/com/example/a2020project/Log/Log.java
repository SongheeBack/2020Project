package com.example.a2020project.Log;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a2020project.R;

public class Log extends Fragment {


    public Log() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log, null) ;
        // Inflate the layout for this fragment
        ListView listview1;
        ListView listview2;
        ListView listview3;
        LogListviewAdapter adapter1;
        LogListviewAdapter adapter2;
        LogListviewAdapter adapter3;

        // Adapter 생성
        adapter1 = new LogListviewAdapter();
        adapter2 = new LogListviewAdapter();
        adapter3 = new LogListviewAdapter();

        // 리스트뷰 참조 및 Adapter 닫기
        listview1 = view.findViewById(R.id.listView1);
        listview2 = view.findViewById(R.id.listView2);
        listview3 = view.findViewById(R.id.listView3);


        listview1.setAdapter(adapter1);
        listview2.setAdapter(adapter2);
        listview3.setAdapter(adapter3);

        // 첫 번째 아이템 추가
        adapter1.addItem("Box", "Account Box black 36dp");
        adapter2.addItem("Box", "Account Box black 36dp");
        adapter3.addItem("Box", "Account Box black 36dp");
        // 두 번째 아이템 추가
        adapter1.addItem("Circle", "Account Circle black 36dp");
        adapter2.addItem("Circle", "Account Circle black 36dp");
        adapter3.addItem("Circle", "Account Circle black 36dp");
        // 세 번째 아이템 추가
        adapter1.addItem("Ind", "Account Ind black 36dp");
        adapter2.addItem("Ind", "Account Ind black 36dp");
        adapter3.addItem("Ind", "Account Ind black 36dp");


        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // get item
                LogListview item = (LogListview) parent.getItemAtPosition(position);

                String titleStr = item.getTitleStr();
                String descStr = item.getDescStr();
            }
        });


        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // get item
                LogListview item = (LogListview) parent.getItemAtPosition(position);

                String titleStr = item.getTitleStr();
                String descStr = item.getDescStr();
            }
        });

       /* listview3.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                String titleStr = item.getTitleStr();
                String descStr = item.getDescStr();
            }
        });*/
        return view;
        //return inflater.inflate(R.layout.fragment_log, container, false);
    }

}

package com.example.a2020project.monitor;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.a2020project.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Monitor extends Fragment {


    public Monitor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monitor, null) ;
        ListView listview ;
        MonitorListviewAdapter mAdapter;

        // Adapter 생성
        mAdapter = new MonitorListviewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = view.findViewById(R.id.monitorListview);
        listview.setAdapter(mAdapter);

        // 디비에서 데이터 가져와서
        // mAdapter.addItem 이용해 데이터 추가
        // 동적
        mAdapter.addItem("디바이스1", "20");
        mAdapter.addItem("디바이스2", "40");

        return view;
        //return inflater.inflate(R.layout.fragment_monitor, container, false);
    }

}

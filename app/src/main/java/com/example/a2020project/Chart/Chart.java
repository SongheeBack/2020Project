package com.example.a2020project.Chart;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2020project.R;
//MPAndroidChart import
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chart extends Fragment {


    public Chart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chart, null) ;

        // 꺾은 선 그래프
        LineChart lineChart;

        ArrayList<Entry> entry_chart = new ArrayList<>();

        lineChart = view.findViewById(R.id.chart);//layout의 id
        LineData chartData = new LineData();

        entry_chart.add(new Entry(2, 5));
        entry_chart.add(new Entry(3, 6));
        entry_chart.add(new Entry(4, 8));
        entry_chart.add(new Entry(1, 9));

    /* 만약 (2, 3) add하고 (2, 5)한다고해서
    기존 (2, 3)이 사라지는게 아니라 x가 2인곳에 y가 3, 5의 점이 찍힘 */

        LineDataSet lineDataSet = new LineDataSet(entry_chart, "꺽은선1");
        chartData.addDataSet(lineDataSet);

        lineChart.setData(chartData);

        lineChart.invalidate();

        return view;
        //return inflater.inflate(R.layout.fragment_chart, container, false);
    }

}

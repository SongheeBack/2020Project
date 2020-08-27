package com.example.a2020project.Chart;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2020project.MainActivity;
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

        // DatePicker Dialog
        final int DIALOG_DATE = 1;
        final int DIALOG_TIME = 2;
        TextView startDate = view.findViewById(R.id.startD);
        TextView endDate = view.findViewById(R.id.endD);

        //  조회
        Button checkB;

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

        // YYMMDD 텍스트뷰 클릭 시 이벤트 -> DatePicker Dialog
       /* startDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_DATE); // 날짜 설정 다이얼로그 띄우기
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_DATE);
            }
        });*/


        return view;
        //return inflater.inflate(R.layout.fragment_chart, container, false);
    }

   /* @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case 1 :
                DatePickerDialog dpd = new DatePickerDialog(Chart.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Toast.makeText(getActivity().getApplicationContext(), year+"년 "+(monthOfYear+1)+"월 "+dayOfMonth+"일 을 선택했습니다", Toast.LENGTH_SHORT).show();
                    }
                }, 2015, 6, 21); // 기본값 연월일(사용자가 날짜설정 후 다이얼로그 빠져나올때 호출할 리스너 등록)
                return dpd;

            case DIALOG_TIME :
                TimePickerDialog tpd =
                        new TimePickerDialog(MainActivity.this,
                                new OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view,
                                                          int hourOfDay, int minute) {
                                        Toast.makeText(getApplicationContext(),
                                                hourOfDay +"시 " + minute+"분 을 선택했습니다",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }, // 값설정시 호출될 리스너 등록
                                4,19, false); // 기본값 시분 등록
                // true : 24 시간(0~23) 표시
                // false : 오전/오후 항목이 생김
                return tpd;
        }
        return onCreateDialog(id);
    }*/
}

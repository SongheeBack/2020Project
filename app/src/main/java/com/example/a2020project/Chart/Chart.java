package com.example.a2020project.Chart;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a2020project.DBConnect;
import com.example.a2020project.MainActivity;
import com.example.a2020project.R;
//MPAndroidChart import
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chart extends Fragment {

    Context mContext;

    ArrayList<String> device_ID = new ArrayList<>();
    HashMap<String, String> dName = new HashMap<>();
    ArrayList<String> deviceName = new ArrayList<>();
    ArrayList<String> unitArr = new ArrayList<>();

    Spinner spinner_type;
    TextView tv_dataType;
    ArrayAdapter<String> stAdapter;

    LineChart sChart;
    ArrayList<Entry> entry_chart = new ArrayList<>();
    //LineData chartData = new LineData();

    int id_pos;
    String nameCh;
    String typeCh;
    String startD;
    String startT;
    String endD;
    String endT;


    public Chart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_chart, null);

        mContext = MainActivity.mContext;

        device_ID.clear();
        dName.clear();
        deviceName.clear();
        entry_chart.clear();
        id_pos = 0;
        nameCh = null;
        typeCh = null;
        startD = null;
        startT= null;
        endD = null;
        endT = null;

        device_ID = ((MainActivity)getActivity()).getDevice_ID();
        dName = ((MainActivity)getActivity()).getDevice_Name();

        unitArr.add("데이터 타입");

        for(int i = 0; i<device_ID.size(); i++){
            String name;
            name = dName.get(device_ID.get(i));
            deviceName.add(name);
        }

        Spinner spinner_device = view.findViewById(R.id.spinner_device);
        spinner_type = view.findViewById(R.id.spinner_type);

        final TextView tv_deviceName = view.findViewById(R.id.tv_deviceName);
        tv_dataType = view.findViewById(R.id.tv_dataType);

        ArrayAdapter<String> sdAdapter = new ArrayAdapter<String>(MainActivity.mContext, R.layout.support_simple_spinner_dropdown_item, deviceName);
        spinner_device.setAdapter(sdAdapter);


        spinner_device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                tv_deviceName.setText("선택된 기기: " + deviceName.get(position));
                id_pos = position;
                nameCh = deviceName.get(position);

                stAdapter = new ArrayAdapter<String>(MainActivity.mContext, R.layout.support_simple_spinner_dropdown_item, unitArr);
                spinner_type.setAdapter(stAdapter);

                setTypeSpinner(nameCh);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                tv_dataType.setText("조회할 데이터: "+ unitArr.get(position));
                typeCh = unitArr.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });



        // 선택한 날짜와 시간을 저장하기 위한 변수 선언
        final StringBuilder chart_StartDate = new StringBuilder();
        final StringBuilder chart_EndDate = new StringBuilder();
        final StringBuilder chart_StartTime = new StringBuilder();
        final StringBuilder chart_EndTime = new StringBuilder();

        // TextView에 대한 이벤트 처리를 위해 TextView의 id를 가져오기 위한 변수들 선언
        final TextView[] tv = new TextView[4];
        tv[0] = view.findViewById(R.id.startDate);
        tv[1] = view.findViewById(R.id.endDate);
        tv[2] = view.findViewById(R.id.startTime);
        tv[3] = view.findViewById(R.id.endTime);

        // Calendar를 선언한 후 TextView에 오늘 날짜를 표시
        Calendar cal = Calendar.getInstance();
        tv[0].setText(cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE));
        tv[1].setText(cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE));


        // 이하 TextView에 대한 이벤트 처리 함수
        tv[0].setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                new DatePickerDialog(getActivity(), mDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
            }

            DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker dview, int year, int month, int dayOfMonth) {
                    TextView tv = (TextView)view.findViewById(R.id.startDate);
                    tv.setText(String.format("%d-%d-%d", year, month+1, dayOfMonth));
                    chart_StartDate.setLength(0);
                    chart_StartDate.append(Integer.toString(year)).append(Integer.toString(month+1)).append(Integer.toString(dayOfMonth));
                }
            };
        });

        tv[1].setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                new DatePickerDialog(getActivity(), mDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
            }

            DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker dview, int year, int month, int dayOfMonth) {
                    TextView tv = (TextView)view.findViewById(R.id.endDate);
                    tv.setText(String.format("%d-%d-%d", year, month+1, dayOfMonth));
                    chart_EndDate.setLength(0);
                    chart_EndDate.append(Integer.toString(year)).append(Integer.toString(month+1)).append(Integer.toString(dayOfMonth));
                }
            };
        });

        tv[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tv[2].setText(selectedHour + ":" + selectedMinute + ":00");
                        chart_StartTime.append(Integer.toString(selectedHour)).append(Integer.toString(selectedMinute));
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        tv[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tv[3].setText(selectedHour + ":" + selectedMinute + ":00");
                        chart_EndTime.append(Integer.toString(selectedHour)).append(Integer.toString(selectedMinute));
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        Button chart_btn = view.findViewById(R.id.chart_button);

        chart_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                entry_chart.clear();

                startD = tv[0].getText().toString();
                endD = tv[1].getText().toString();
                startT = tv[2].getText().toString();
                endT = tv[3].getText().toString();

                if(startD != null & endD != null & startT != null & endT != null){

                    String sDate = startD + " " + startT;
                    String eDate = endD + " " + endT;

                    getLogData(sDate, eDate, nameCh, typeCh);

                }
            }
        });

        // 꺾은 선 그래프

        sChart = view.findViewById(R.id.chart);//layout의 id

        /*entry_chart.add(new Entry(2, 5));
        entry_chart.add(new Entry(3, 6));
        entry_chart.add(new Entry(4, 8));
        entry_chart.add(new Entry(1, 9));*/

    /* 만약 (2, 3) add하고 (2, 5)한다고해서
    기존 (2, 3)이 사라지는게 아니라 x가 2인곳에 y가 3, 5의 점이 찍힘 */


        return view;
        //return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    public void setTypeSpinner(String name){

        String id = null;

        for(String key : dName.keySet()){
            if(name.equals(dName.get(key))){
                id = key;
            }
        }
        unitArr.clear();

        stAdapter = new ArrayAdapter<String>(MainActivity.mContext, R.layout.support_simple_spinner_dropdown_item, unitArr);
        spinner_type.setAdapter(stAdapter);
        //stAdapter.notifyDataSetChanged();

        unitArr = ((MainActivity)getActivity()).getDataType_Sp(id);
        stAdapter.notifyDataSetChanged();

        if(stAdapter.getCount()!=0 && stAdapter!=null){
            stAdapter.clear();
        }

        for(int i = 0; i<unitArr.size(); i++){
            stAdapter.add(unitArr.get(i));
        }

    }

    public void getLogData(String sD, String eD, final String nameCh, final String typeCh){

        final String id = ((MainActivity)getActivity()).findIdByName(nameCh);
        String idx = ((MainActivity)getActivity()).findIdxByUnit(id, typeCh);
        //android.util.Log.d("name, type: ", nameCh + ", " + typeCh);

        if(idx != null){
            DBConnect.GetData dbLog1 = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse(){
                @Override
                public void processFinish(String result) {
                    //Log.d("dbChart1되나...: ", result);

                    if (result.equals("[]")) {
                        Toast.makeText(mContext, "해당 기간에 조회 가능한 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String date = null;

                        try {
                            JSONArray restArr = new JSONArray(result);
                            for(int i=0;i<restArr.length();i++){

                                JSONArray item = restArr.getJSONArray(i);
                                date = item.getString(0);
                                String value = item.getString(1);
                                //float vf = Float.parseFloat(value);
                                //Log.d("value: ", value + ", " + vf);

                                //String valueUnit = value + " " + typeCh;

                                entry_chart.add(new Entry(i, Float.parseFloat(value)));
                                LineDataSet lineDataSet = new LineDataSet(entry_chart, nameCh);
                                LineData lineData = new LineData(lineDataSet);
                                sChart.setData(lineData);

                                /*ArrayList<LineDataSet> dataSets = new ArrayList<>();
                                dataSets.add(lineDataSet);*/

                                /*ScatterDataSet scatterDataSet = new ScatterDataSet(entry_chart, );
                                ScatterData scatterData = new ScatterData(scatterDataSet);
                                sChart.setData(scatterData);*/
                                sChart.invalidate();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).execute("select DISTINCT date_format(log_date,'%y-%m-%d %H:%i:%s') as log_date, log_value from log " +
                    "where device_ID = " + '"' + id + '"' + " and log_index = " + '"' + idx + '"' +
                    " and log_date between "+ '"' + sD + '"' + " and" + '"' + eD + '"' + " order by log_date desc", "2");

        }

    }

}

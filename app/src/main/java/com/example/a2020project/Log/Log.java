package com.example.a2020project.Log;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a2020project.DBConnects.DBConnect;
import com.example.a2020project.MainActivity;
import com.example.a2020project.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Log extends Fragment {

    Context mContext;
    static final int ACT_SET_BIRTH = 1;
    ArrayList<String> device_ID = new ArrayList<>();
    HashMap<String, String> dName = new HashMap<>();
    ArrayList<String> deviceName = new ArrayList<>();
    ArrayList<String> unitArr = new ArrayList<>();

    Spinner spinner_type;
    TextView tv_dataType;
    ArrayAdapter<String> stAdapter;

    int id_pos;
    String nameCh;
    String typeCh;

    String startD;
    String startT;
    String endD;
    String endT;

    ListView listview1;
    LogListviewAdapter adapter1;

    public Log() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log, null) ;

        mContext = MainActivity.mContext;

        device_ID.clear();
        dName.clear();
        deviceName.clear();

        id_pos = 0;
        nameCh = null;
        typeCh = null;
        startD = null;
        startT= null;
        endD = null;
        endT = null;

        device_ID = ((MainActivity)getActivity()).getDevice_ID();
        //android.util.Log.d("로그~~id:: ", String.valueOf(device_ID));
        dName = ((MainActivity)getActivity()).getDevice_Name();
        //android.util.Log.d("로그~~name:: ", String.valueOf(dName));

        unitArr.add("데이터 타입");

        for(int i = 0; i<device_ID.size(); i++){
            String name;
            name = dName.get(device_ID.get(i));
            //android.util.Log.d("deviceID i:: ", String.valueOf(i));
            deviceName.add(name);
            android.util.Log.d("deviceName:: ", String.valueOf(deviceName));
        }

        Spinner spinner_device = view.findViewById(R.id.spinner_device);
        spinner_type = view.findViewById(R.id.spinner_data);

        final TextView tv_deviceName = view.findViewById(R.id.tv_deviceName);
        tv_dataType = view.findViewById(R.id.tv_dataType);

        ArrayAdapter<String> sdAdapter = new ArrayAdapter<String>(MainActivity.mContext, R.layout.support_simple_spinner_dropdown_item, deviceName);
        spinner_device.setAdapter(sdAdapter);


        spinner_device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                tv_deviceName.setText("선택된 기기: " + deviceName.get(position).toString());
                id_pos = position;
                nameCh = deviceName.get(position).toString();

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

        final StringBuilder startDate = new StringBuilder();
        final StringBuilder endDate = new StringBuilder();
        final StringBuilder startTime = new StringBuilder();
        final StringBuilder endTime = new StringBuilder();

        final TextView[] tv = new TextView[4];
        tv[0] = (TextView)view.findViewById(R.id.editTextDate1);
        tv[1] = view.findViewById(R.id.editTextDate2);
        tv[2] = view.findViewById(R.id.editTextTime1);
        tv[3] = view.findViewById(R.id.editTextTime2);

        Calendar cal = Calendar.getInstance();
        tv[0].setText(cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE));
        tv[1].setText(cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE));

        tv[0].setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                new DatePickerDialog(getActivity(), mDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
            }

            DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker dview, int year, int month, int dayOfMonth) {
                    //TextView tv = (TextView)view.findViewById(R.id.editTextDate1);
                    tv[0].setText(String.format("%d-%d-%d", year, month+1, dayOfMonth));
                    startDate.setLength(0);
                    startDate.append(Integer.toString(year)).append(Integer.toString(month+1)).append(Integer.toString(dayOfMonth));
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
                    //TextView tv = (TextView)view.findViewById(R.id.editTextDate2);
                    tv[1].setText(String.format("%d-%d-%d", year, month+1, dayOfMonth));
                    endDate.setLength(0);
                    endDate.append(Integer.toString(year)).append(Integer.toString(month+1)).append(Integer.toString(dayOfMonth));
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
                        startTime.append(Integer.toString(selectedHour)).append(Integer.toString(selectedMinute));
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
                        endTime.append(Integer.toString(selectedHour)).append(Integer.toString(selectedMinute));
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        listview1 = view.findViewById(R.id.listView1);
        adapter1 = new LogListviewAdapter();
        //listview1.setAdapter(adapter1);

        Button log_btn = view.findViewById(R.id.log_button);
        log_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                adapter1.clear();

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

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        unitArr = ((MainActivity)getActivity()).getDataType_Sp(id);
        stAdapter.notifyDataSetChanged();

        if(stAdapter.getCount()!=0 && stAdapter!=null){
            stAdapter.clear();
        }

        for(int i = 0; i<unitArr.size(); i++){
            stAdapter.add(unitArr.get(i));
        }

    }

    public void getLogData(String sD, String eD, String nameCh, final String typeCh){

        String id = ((MainActivity)getActivity()).findIdByName(nameCh);
        String idx = ((MainActivity)getActivity()).findIdxByUnit(id, typeCh);
        //android.util.Log.d("name, type: ", nameCh + ", " + typeCh);

        if(idx != null){
            DBConnect.GetData dbLog1 = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse(){
                @Override
                public void processFinish(String result) {
                    //android.util.Log.d("dbLog1되나...: ", result);

                    if (result.equals("[]")) {
                        Toast.makeText(mContext, "해당 기간에 조회 가능한 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            JSONArray restArr = new JSONArray(result);
                            for(int i=0;i<restArr.length();i++){

                                JSONArray item = restArr.getJSONArray(i);
                                String date = item.getString(0);
                                String value = item.getString(1);

                                Float cData = Float.parseFloat(value);
                                String data_2 = String.valueOf(Math.round(cData*100)/100.0);

                                String valueUnit = data_2 + " " + typeCh;

                                adapter1.addItem(valueUnit, date);
                                listview1.setAdapter(adapter1);

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
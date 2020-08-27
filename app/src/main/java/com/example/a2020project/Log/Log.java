package com.example.a2020project.Log;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.a2020project.R;

import java.util.Calendar;

public class Log extends Fragment {

    static final int ACT_SET_BIRTH = 1;

    public Log() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_log, null) ;
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
                    TextView tv = (TextView)view.findViewById(R.id.editTextDate1);
                    tv.setText(String.format("%d-%d-%d", year, month+1, dayOfMonth));
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
                    TextView tv = (TextView)view.findViewById(R.id.editTextDate2);
                    tv.setText(String.format("%d-%d-%d", year, month+1, dayOfMonth));
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
                        tv[2].setText(selectedHour + "시 " + selectedMinute + "분");
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
                        tv[3].setText(selectedHour + "시 " + selectedMinute + "분");
                        endTime.append(Integer.toString(selectedHour)).append(Integer.toString(selectedMinute));
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

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
        }); */
        return view;
    }
}
package com.example.a2020project.monitor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.a2020project.DBConnect;
import com.example.a2020project.MainActivity;
import com.example.a2020project.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Monitor extends Fragment {

    ListView listview;
    MonitorListviewAdapter mAdapter;

    String value; // 최종 데이터
    int cnt_check1;
    //int cnt_check2; // log_index size
    HashMap<String, String> dName = new HashMap<>();

    public Monitor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monitor, null) ;

        // Adapter 생성
        mAdapter = new MonitorListviewAdapter();

        //id_check = null;
        value = null;
        cnt_check1 = 0;
        dName.clear();

        // 리스트뷰 참조 및 Adapter달기
        listview = view.findViewById(R.id.monitorListview);

        ArrayList<String> device_ID = new ArrayList<>();
        device_ID = ((MainActivity)getActivity()).getDevice_ID();

        for(int i = 0; i < device_ID.size(); i++){
            //int i_check = i;
            String deviceId = null;
            deviceId = device_ID.get(i);

            String index = null;
            index = ((MainActivity)getActivity()).getLog_Index(deviceId);
            //Log.d("Monitor!!!!!!!!!:: ", deviceId + " / "+index);

            ArrayList<String>log_Index = indexString(index);
            //Log.d("Monitor index String: ", String.valueOf(log_Index));

            for(int j = 0; j < log_Index.size(); j++){
                final String logindex = log_Index.get(j);

                final String finalDeviceId = deviceId;
                DBConnect.GetData dbMonitor1 = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse(){
                    @Override
                    public void processFinish(String result) {
                        //Log.d("dbMonitor1되나...: ",  finalDeviceId + " / " + logindex + " / "+result);

                        if(result.equals("[]")){
                            showResult(finalDeviceId, "0", "조회할 수 있는 데이터가 없습니다.");
                        }
                        else{
                            getRecentData(finalDeviceId, logindex, result);
                        }

                    }
                }).execute("SELECT DISTINCT log_date from log WHERE log_index = "+ '"' + logindex + '"' +" and device_id = " + '"' + deviceId + '"' +" ORDER BY log_date DESC LIMIT 1", "1");
            }

        }

        return view;
        //return inflater.inflate(R.layout.fragment_monitor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

    }


    public ArrayList<String> indexString(String in){

        ArrayList<String> log_Index = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(in);
            for(int i=0;i<jsonArray.length();i++){
                JSONArray item = jsonArray.getJSONArray(i);
                String st = item.getString(0);
                //Log.d("indexString:: ", st);
                log_Index.add(st);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return log_Index;
    }

    public void getRecentData(final String id, String idx, String date){

        final String dID = id;
        //logID = id;
        final String Idx = idx;
        //final String key = id + idx;
        try {
            final JSONArray recentDate = new JSONArray(date);
            for(int i = 0; i< recentDate.length(); i++){
                JSONArray item = recentDate.getJSONArray(i);
                final String rdate = item.getString(0);
                //Log.d("rdate::: ", rdate);
                DBConnect.GetData dbMonitor2 = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse(){
                    @Override
                    public void processFinish(String result) {
                        //Log.d("dbMonitor2되나...: ", result);

                        String Ldata;
                        try {
                            JSONArray restArr = new JSONArray(result);
                            for(int i=0;i<restArr.length();i++){

                                JSONArray item = restArr.getJSONArray(i);
                                Ldata = item.getString(0);

                                //Log.d("Ldata: ", String.valueOf(Ldata));

                                showResult(dID, Idx, Ldata);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).execute("SELECT DISTINCT log_value FROM log WHERE device_ID = " + '"' + dID + '"' + " and log_index = " + '"' + idx + '"' + " and log_date = " + "'"+rdate+"'", "1");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showResult(String id, String idx, String data){

        //Log.d("sdf::: ", id_check);
        dName = ((MainActivity)getActivity()).getDevice_Name();
        String unit = ((MainActivity)getActivity()).getData_Unit(id, idx);
        String name = dName.get(id);

        int cnt_check2 = ((MainActivity)getActivity()).getLogIndex_Cnt(id);
        //Log.d("cnt_ch2: ", String.valueOf(cnt_check2));
        String addUnit = data + unit;
        //Log.d("name + addUnit : ", name + ", " + addUnit);


        if(cnt_check1 == 0){
            value = addUnit;
            cnt_check1++;
        }
        else {
            value = value + "\n" + addUnit;
            cnt_check1++;
        }

        if (cnt_check1 == cnt_check2) {

            mAdapter.addItem(name, value);

            //Log.d("value, cnt: ", value + ", " + "1 = " + cnt_check1 + ", 2 = " + cnt_check2);
            cnt_check1 = 0;
            value = null;
        }
        listview.setAdapter(mAdapter);

    }

}

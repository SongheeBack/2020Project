package com.example.a2020project.monitor;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2020project.DBConnect;
import com.example.a2020project.MainActivity;
import com.example.a2020project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Monitor extends Fragment {

    String mJsonString;
    ListView listview;
    MonitorListviewAdapter mAdapter;

    public Monitor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_monitor, null) ;

        //return view;
        return inflater.inflate(R.layout.fragment_monitor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        // Adapter 생성
        mAdapter = new MonitorListviewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = view.findViewById(R.id.monitorListview);

        try{

            DBConnect DBcon = new DBConnect();
           // DBConnect.GetData db = DBcon.new GetData();
            //mJsonString = db.execute("select device_level from device", "1").get();
            showResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showResult(){
        try {

            JSONArray jsonArray = new JSONArray(mJsonString);

            for(int i=0;i<jsonArray.length();i++){

                JSONArray item = jsonArray.getJSONArray(i);

                String id = item.getString(0);
                String data = item.getString(1);

                mAdapter.addItem(data, id);

                /*HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_NAME, name);

                mArrayList.add(hashMap);*/
            }

            listview.setAdapter(mAdapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }

}

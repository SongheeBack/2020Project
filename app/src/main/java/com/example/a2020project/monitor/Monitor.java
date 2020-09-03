package com.example.a2020project.monitor;



import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.a2020project.DBConnect;
import com.example.a2020project.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URISyntaxException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Monitor extends Fragment {

    String mJsonString;
    String user_level;
    //ArrayList<String> device_ID;
    String device_ID;
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

        Intent intent = getActivity().getIntent();
        user_level = intent.getStringExtra("user_level");
        Log.d("레벨: ", user_level);


        try{

            //DBConnect DBcon = new DBConnect();
            // DBConnect.GetData db = DBcon.new GetData();
            // mJsonString = db.execute("select device_level from device", "1").get();

            DBConnect.GetData dbcon_Monitor1 = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse(){
                @Override
                public void processFinish(String result) {
                    //device_ID.add(result);
                    device_ID = result;
                    Log.d("dbcon_Monitor1 되나...: ", device_ID);
                }
            }).execute("select device_ID from device where device_level =" + user_level, "1");

            /*DBConnect.GetData dbcon_Monitor2 = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse(){
                @Override
                public void processFinish(String result) {
                    device_ID = result;
                    Log.d("dbcon_Monitor1 되나...: ", device_ID);
                }
            }).execute("SELECT DISTINCT log_date FROM log WHERE device_ID = '"+ device_ID +"' order by log_date desc", "1");*/


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

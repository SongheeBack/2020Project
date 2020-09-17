package com.example.a2020project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.a2020project.ErrorData.ErrorData;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static Context mContext;
    ArrayList<HashMap<String, String>> logIndex = new ArrayList<>();
    public HashMap<String, String> dName = new HashMap<>();
    public HashMap<String,HashMap<String,String>> idIdxUnit = new HashMap<>();

    int tabP;
    TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        Intent intent = this.getIntent();
        //String id = intent.getStringExtra("device_ID");

        logIndex.clear();
        dName.clear();
        idIdxUnit.clear();


        logIndex = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("logIndexArray");
        dName = (HashMap<String, String>) getIntent().getSerializableExtra("deviceName");
        idIdxUnit = (HashMap<String,HashMap<String,String>>) getIntent().getSerializableExtra("idIdxUnit");


        //Tablayout
        TabLayout tabs = (TabLayout) findViewById(R.id.tab_layout);
        tabs.addTab(tabs.newTab().setText("Monitor"));
        tabs.addTab(tabs.newTab().setText("Log"));
        tabs.addTab(tabs.newTab().setText("Chart"));
        tabs.setTabGravity(tabs.GRAVITY_FILL);


        //Adapter
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabAdapter = new TabAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(tabAdapter);

        // 탭 선택 이벤트
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabP = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver), new IntentFilter("MyData"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("이상 데이터 발생").setMessage("데이터를 확인하시겠습니까?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.goToError:
                errorDataActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void errorDataActivity(){
        Intent intent = new Intent(getApplicationContext(), ErrorData.class);
        //intent.putExtra("device_ID", device_ID_string);
        intent.putExtra("logIndexArray", logIndex);
        intent.putExtra("deviceName", dName);
        intent.putExtra("idIdxUnit", idIdxUnit);
        startActivity(intent);
    }

    public ArrayList<String> getDevice_ID() {
        //Log.d("메인 getDevice_ID ::", "1");

        HashMap<String, String> str = new HashMap<>();
        str = logIndex.get(0);

        ArrayList<String> device_ID = new ArrayList<>();
        //og.d("뭐지....", String.valueOf(str));

        for (String key : str.keySet()){
            device_ID.add(key);
            //Log.d("hashMap Key : ", key);

            /*Set set = str.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) iterator.next();

                String key = (String) entry.getKey();
                device_ID.add(key);
                /*String value = (String) entry.getValue();
                log_Index.add(value);
                Log.d("hashMap Key : ", key);
                //Log.d("hashMap Value : ", String.valueOf(value));
            }*/
        }
        //Log.d("리스트~~~~~~~id: ", device_ID.toString());

        return device_ID;
    }

    public HashMap<String, String> getDevice_Name(){
        //Log.d("메인 getDevice_Name ::", "1");

        return dName;
    }

    public String findIdByName(String name){

        String id;

        for(String key : dName.keySet()){
            if(name.equals(dName.get(key))){
                id = key;
                return id;
            }
        }

        return null;
    }

    public HashMap<String, String> getUnitIdxMap(String id, String unit){
        HashMap<String, String> str = new HashMap<>();
        HashMap<String, String> resMap = new HashMap<>();

        str = idIdxUnit.get(id);

        for (String index : str.keySet()) {
            if (unit.equals(str.get(index))) {
                resMap.put(unit, index);
            }
        }

        return resMap;
    }

    public String getData_Unit(String id, String idx){
        //Log.d("메인 getData_Unit :: ", id + ", " + idx);

        HashMap<String,String> str = new HashMap<>();
        String unit = null;

        str = idIdxUnit.get(id);
        //Log.d("메인 str: ", id + ", " + idx + ", " + String.valueOf(str));
        unit = str.get(idx);

        return unit;
    }

    public String findIdxByUnit(String id, String type){

        String idx;

        HashMap<String, String> str = idIdxUnit.get(id);
        for(String key : str.keySet()){
            if (type.equals(str.get(key))){
                idx = key;
                return idx;
            }
        }

        return null;
    }

    public ArrayList<String> getDataType_Sp(String id){
        //Log.d("메인 getDataType_Sp :: ", id);
        HashMap<String, String> str = new HashMap<>();
        ArrayList<String> arr = new ArrayList<>();

        str = idIdxUnit.get(id);

        String unit;
        for(String key : str.keySet()){
            unit = str.get(key);
            arr.add(unit);
        }

        return arr;
    }

    public String getLog_Index(String id){

        //Log.d("메인 getLog_Index ::", id);

        HashMap<String, String> str = new HashMap<>();

        str = logIndex.get(0);
        String values = str.get(id);

        return values;
    }

    public int getLogIndex_Cnt(String id){

        int logCnt = 0;

        HashMap<String,String> str = new HashMap<>();
        str = idIdxUnit.get(id);

        logCnt = str.size();

        return logCnt;
    }

}
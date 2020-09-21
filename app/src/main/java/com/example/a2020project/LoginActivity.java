package com.example.a2020project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2020project.DBConnects.DBConnect;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class LoginActivity extends AppCompatActivity {

    EditText userId;
    EditText userPw;
    ImageView loginBtn;
    String ID, PW;
    String user_level;
    String user_exist;
    String device_ID_string;
    String ERROR = "result error";
    String NOT_EXIST_INDEX = "로그 인덱스 없음";

    int cnt;
    int cnt_2;
    int ii;


    DBConnect DBcon = new DBConnect();

    ArrayList<String> deviceID = new ArrayList<>();
    ArrayList<HashMap> cArrayList = new ArrayList<>();
    HashMap<String, String> hashMap_idNumOfData = new HashMap<>();
    HashMap<String,String> hashMap_idIdx = new HashMap<>();
    HashMap<String,HashMap<String,String>> hashMap_idIdxUnit = new HashMap<>();
    HashMap<String,String> dName = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        deviceID.clear();
        hashMap_idIdx.clear();
        hashMap_idIdxUnit.clear();
        dName.clear();
        hashMap_idNumOfData.clear();

        ii = 0;

        userId = findViewById(R.id.loginId);
        userPw = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID = userId.getText().toString();
                PW = userPw.getText().toString();

                // 아이디 비번이 공백이 아닐 때
                if(!ID.equals("") && !PW.equals("")){

                    try{

                        DBConnect.GetData dbcon = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse(){
                            @Override
                            public void processFinish(String result) {
                                Log.d("dbcon되나..: ", result);
                                user_exist = result;
                                if (user_exist.equals("["+"["+'"'+"1"+'"'+"]"+"]")){
                                    getUserData();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "아이디, 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).execute("SELECT EXISTS (SELECT * FROM user WHERE user_ID = " + '"' + ID + '"' + "and user_PW = " +  '"' + PW + '"' + ") as success", "1");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //Log.d("아이디 비번 확인: ", ID + " / "+ PW);
                }

                else{
                    Toast.makeText(getApplicationContext(), "아이디, 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void runActivity(){

        //Log.d("runAct:: ", "되나?");
        //Log.d("HashMap:: ", String.valueOf(hashMap_idIdxUnit));

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //intent.putExtra("device_ID", device_ID_string);
        intent.putExtra("logIndexArray", cArrayList);
        intent.putExtra("deviceName", dName);
        intent.putExtra("idIdxUnit", hashMap_idIdxUnit);
        startActivity(intent);
        finish();

        //Toast.makeText(getApplicationContext(), "사용자: " + ID + "Level: " + user_level, Toast.LENGTH_SHORT).show();
    }

    private void getUserData(){
        DBConnect.GetData dbcon2 = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse(){
            @Override
            public void processFinish(String result) {

                //Log.d("dbcon2되나...: ", result);
                user_level = result;

                String level = user_level;
                String intLevel = level.replaceAll("[^0-9]", "");
                //Log.d("숫자만 나오나? ", intLevel);

                getDeviceData(intLevel);
            }
        }).execute("select user_level FROM user WHERE user_ID = " + '"' + ID + '"' + "and user_PW = " +  '"' + PW + '"', "1");
    }

    private void getDeviceData(String intLevel){
        DBConnect.GetData dbcon3 = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse(){
            @Override
            public void processFinish(String result) {
                Log.d("dbcon3 되나...: ", result);
                //device_ID_string = result;
                String id;
                String name;
                String numOfData;

                try {
                    JSONArray restArr = new JSONArray(result);
                    for(int i=0;i<restArr.length();i++) {

                        int j = i;
                        JSONArray item = restArr.getJSONArray(i);
                        id = item.getString(0);
                        name = item.getString(1);
                        numOfData = item.getString(2);

                        deviceID.add(id);
                        dName.put(id, name);
                        hashMap_idNumOfData.put(id, numOfData);

                        if(j==restArr.length()-1){
                            getIndex();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("select device_ID, device_name, device_num_data from device where device_level <=" + '"' + intLevel + '"', "3");
    }

    /*public void getDName(final String id, final int length){

        DBConnect.GetData getDName = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse(){
            @Override
            public void processFinish(String result) {
                //Log.d("Login/getDName 되나...: ", id+"/"+result);

                String name;
                try {
                    JSONArray restArr = new JSONArray(result);
                    for(int i=0;i<restArr.length();i++){

                        JSONArray item = restArr.getJSONArray(i);
                        name = item.getString(0);
                        dName.put(id, name);

                        if(cnt == length){
                            comArrayList(hashMap_idIdx);
                            //Log.d("length_cnt: ", String.valueOf(cnt));
                        }
                        else { cnt++; } }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("SELECT device_name FROM device WHERE device_ID = " + '"' + id + '"', "1");
    }*/

    public void getIndex(){

        final int length = deviceID.size();
        Log.d("deviceID: ", String.valueOf(deviceID));
        Log.d("length: ", String.valueOf(length));
        for(ii = 0; ii< length; ii++){
            //JSONArray item = device_ID.getJSONArray(ii);
            final String id = deviceID.get(ii);
            //Log.d("id값이 뭐지:: ", id);

            cnt = 1;
            DBConnect.GetData getIndex = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse(){
                @Override
                public void processFinish(String result) {
                    Log.d("Login/getIndex 되나...: ", id+"/"+result);

                    Log.d("length_cnt: ", String.valueOf(cnt));
                    String res = result;
                    if(res.equals(ERROR)){
                            Log.w("Log Index Error: ", NOT_EXIST_INDEX);
                        }
                    else if (res.equals("[]")) {
                        res = "[" + "[" + "0" + "]" + "]";
                        hashMap_idIdx.put(id, res);
                        //cnt = 1;
                        if (cnt == length) {
                            comArrayList(hashMap_idIdx);
                            Log.d("length_cnt: ", String.valueOf(cnt));
                        } else {
                                cnt++;
                            }
                    }
                    else{
                        //Log.d("res: ", res);
                        hashMap_idIdx.put(id, res);
                        //Log.d("hashMap Id Name: ", String.valueOf(hashMap_idIdx));
                        //cnt = 1;
                        if (cnt == length) {
                            comArrayList(hashMap_idIdx);
                            Log.d("length_cnt: ", String.valueOf(cnt));
                        } else {
                                cnt++;
                            }
                    }
                }
            }).execute("SELECT DISTINCT data_idx FROM datainfo WHERE device_ID = " + '"' + id + '"', "1");
        }
    }

    public void comArrayList(final HashMap<String, String> hashMap) {
        cArrayList.clear();
        cArrayList.add(hashMap);
        //Log.d("cArrayList::: ", String.valueOf(cArrayList));

        final HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> str = cArrayList.get(0);
        final int length = str.keySet().size();

        ArrayList<String> keyset = new ArrayList<>();
        keyset.clear();
        cnt_2 = 1;

        Iterator<String> keys = str.keySet().iterator();
        while(keys.hasNext()){
            final String key = keys.next();
            keyset.add(key);
        }

        if(keyset.size() > 0){
           // Log.d("keySetArr: ", String.valueOf(keyset));

            for(int i = 0; i<keyset.size(); i++){

                //Log.d("HashMap:: ", String.valueOf(hashMap_idIdxUnit));
                final String key = keyset.get(i);
                String Idx = str.get(keyset.get(i));

                try {
                    JSONArray idxArr = new JSONArray(Idx);
                    //Log.d("idxArr length: ", String.valueOf(idxArr.length()));

                    for(int j = 0; j<idxArr.length(); j++){
                        final int z = j;
                        final int idxArrL = idxArr.length();
                        //Log.d("idxArrL: ", String.valueOf(idxArrL));
                        JSONArray item = idxArr.getJSONArray(j);
                        //Log.d("item: ", String.valueOf(item));
                        final String idx = item.getString(0);
                        //Log.d("idx: ", String.valueOf(idx));

                        DBConnect.GetData dbGetDataUnit = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse() {
                            @Override
                            public void processFinish(String result) {

                                Log.d("dbGetDataUnit되나...: ", result);

                                String res = null;
                                try {
                                    JSONArray resArr = new JSONArray(result);
                                    JSONArray item = resArr.getJSONArray(0);
                                    if (item.equals("[]")){
                                        res = "(null)";
                                    }
                                    res = item.getString(0);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                map.put(idx, res);
                                //Log.d("map:: ", String.valueOf(map));

                                if(z == idxArrL-1) {
                                    HashMap<String, String> map2 = (HashMap<String, String>) map.clone();
                                    hashMap_idIdxUnit.put(key, map2);
                                    map.clear();

                                    if(cnt_2 == length){
                                        runActivity();
                                    }
                                    else {
                                        cnt_2++;
                                    }
                                }
                            }
                        }).execute("select data_unit from datainfo where device_ID = " + '"' + key + '"' + " and data_idx = " + '"' + idx + '"', "1");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}



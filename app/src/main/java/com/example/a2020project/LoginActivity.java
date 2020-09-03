package com.example.a2020project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    EditText userId;
    EditText userPw;
    Button loginBtn;
    String ID, PW;
    String user_level;
    String user_exist;
    String resultString;

    DBConnect DBcon = new DBConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        userId = findViewById(R.id.loginId);
        userPw = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginButton);

//        ID = userId.getText().toString();
//        PW = userPw.getText().toString();
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
                                user_exist = result;
                                Log.d("dbcon되나..: ", user_exist);
                                if (user_exist.equals("["+"["+'"'+"1"+'"'+"]"+"]")){

                                    DBConnect.GetData dbcon2 = (DBConnect.GetData) new DBConnect.GetData(new DBConnect.GetData.AsyncResponse(){
                                        @Override
                                        public void processFinish(String result) {
                                            user_level = result;
                                            Log.d("dbcon2되나...: ", user_level);
                                            runActivity();
                                        }
                                    }).execute("select user_level FROM user WHERE user_ID = " + '"' + ID + '"' + "and user_PW = " +  '"' + PW + '"', "1");
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "아이디, 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).execute("SELECT EXISTS (SELECT * FROM user WHERE user_ID = " + '"' + ID + '"' + "and user_PW = " +  '"' + PW + '"' + ") as success", "1");

                        //DBConnect DBcon = new DBConnect();
                        //DBConnect.GetData db = new DBConnect.GetData();

                        //get으로 되는지 확인해야함
                        /*db.execute("SELECT EXISTS (SELECT * FROM user WHERE user_ID = " + '"' + ID + '"' + "and user_PW = " +  '"' + PW + '"' + ") as success", "1");
                        //user_exist = DBcon.getResult();
                        Log.d("진행1", user_exist);

                        while (db.getStatus().equals(Fin)){
                            user_exist = DBcon.getResult();
                            Log.d("진행2", user_exist);
                            if (user_exist.equals("["+"["+'"'+"1"+'"'+"]"+"]")){
                                db.execute("select user_level FROM user WHERE user_ID = " + '"' + ID + '"' + "and user_PW = " +  '"' + PW + '"', "1");
                                user_level = DBcon.getResult();
                                Log.d("레벨11: ", user_level);
                                runActivity();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "아이디, 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }*/
                        /*else{
                            Log.d("111111111", "으니ㅏ으린으리ㅏ느");
                        }*/

                        //showResult();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d("아이디 비번 확인: ", ID + " / "+ PW);
                }

                else{
                    Toast.makeText(getApplicationContext(), "아이디, 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void runActivity(){
        String level = user_level;
        String intLevel = level.replaceAll("[^0-9]", "");
        Log.d("숫자만 나오나? ", intLevel);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("user_level", intLevel);
        startActivity(intent);
        finish();

        Toast.makeText(getApplicationContext(), "사용자: " + ID + "Level: " + level, Toast.LENGTH_SHORT).show();
    }

}

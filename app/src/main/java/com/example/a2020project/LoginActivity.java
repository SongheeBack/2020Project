package com.example.a2020project;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userId = findViewById(R.id.loginId);
        userPw = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginButton);

        ID = userId.getText().toString();
        PW = userPw.getText().toString();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ID != "" && PW != ""){

                }
                else{
                    Toast.makeText(getApplicationContext(), "아이디, 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

                //Toast.makeText(getApplicationContext(),"로그인 기능 임시 구현 무조건 성공", Toast.LENGTH_SHORT);

                //intent.putExtra("login", "로그인 기능 임시 구현 무조건 성공");*/

            }
        });

    }

}

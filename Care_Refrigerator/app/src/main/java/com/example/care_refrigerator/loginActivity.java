package com.example.care_refrigerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.button);
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText_loginid = findViewById(R.id.editText_loginid);
                EditText editText_password = findViewById(R.id.editText_password);
                final String loginid = editText_loginid.getText().toString();
                String password = editText_password.getText().toString();
                if (isEmptyOrWhiteSpace(loginid)) {
                    editText_loginid.setError("로그인 아이디를 입력하세요.");
                    Toast.makeText(loginActivity.this, "로그인 아이디 미입력", Toast.LENGTH_SHORT).show();
                }
                else if(isEmptyOrWhiteSpace(password)) {
                    editText_password.setError("비밀번호를 입력하세요.");
                    Toast.makeText(loginActivity.this, "비밀번호 미입력", Toast.LENGTH_SHORT).show();
                }
                else {
                    String msg = "회원가입 성공: " + loginid;
                    Toast.makeText(loginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                //회원 가입 데이터를 서버에 전송하는 코드를 구현해야 함. Firebase이용할 예정.


         }
        };
        button.setOnClickListener(listener);
    }
    static boolean isEmptyOrWhiteSpace(String s){
        if(s==null)return true;
        return s.trim().length()==0;
    }
}

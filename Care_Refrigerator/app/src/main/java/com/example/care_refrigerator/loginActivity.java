package com.example.care_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    private static final String TAG = "SingUp";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        Button button = findViewById(R.id.button);
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText_password2 = findViewById(R.id.editText_password2);
                EditText editText_loginid = findViewById(R.id.editText_loginid);
                EditText editText_password = findViewById(R.id.editText_password);

                final String loginid = editText_loginid.getText().toString();
                String password = editText_password.getText().toString();
                String password2 = editText_password2.getText().toString();
                if (isEmptyOrWhiteSpace(loginid)) {
                    editText_loginid.setError("로그인 아이디를 입력하세요.");
                    Toast.makeText(loginActivity.this, "로그인 아이디 미입력", Toast.LENGTH_SHORT).show();
                } else if (isEmptyOrWhiteSpace(password)) {
                    editText_password.setError("비밀번호를 입력하세요.");
                    Toast.makeText(loginActivity.this, "비밀번호 미입력", Toast.LENGTH_SHORT).show();
                } else if (isEmptyOrWhiteSpace(password2)) {
                    editText_password2.setError("비밀번호를 한번 더 입력하세요.");
                    Toast.makeText(loginActivity.this, "비밀번호 확인 미입력", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(password2)) {
                    editText_password2.setError("비밀번호와 같지 않습니다.");
                    Toast.makeText(loginActivity.this, "비밀번호 불일치", Toast.LENGTH_SHORT).show();
                } else {
                    String msg = "회원가입 성공: " + loginid;
                    Toast.makeText(loginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    createacount();
                }
            }
            //회원 가입 데이터를 서버에 전송하는 코드를 구현해야 함. Firebase이용할 예정.
            public void createacount(){
                EditText editText_loginid = findViewById(R.id.editText_loginid);
                EditText editText_password = findViewById(R.id.editText_password);
                final String id = editText_loginid.getText().toString();
                String password = editText_password.getText().toString();
                mAuth.createUserWithEmailAndPassword(id,password).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"SingInwithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(),loginonActivity.class);
                        }else {
                            Log.w(TAG,"SingwithEmail:failure",task.getException());

                        }
                    }
                });
            }
        };
        button.setOnClickListener(listener);
    }
    static boolean isEmptyOrWhiteSpace(String s){
        if(s==null)return true;
        return s.trim().length()==0;
    }
}

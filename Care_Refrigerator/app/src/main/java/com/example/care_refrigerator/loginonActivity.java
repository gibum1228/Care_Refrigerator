package com.example.care_refrigerator;

import android.content.Intent;
import android.net.Uri;
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

public class loginonActivity extends AppCompatActivity {
    private static final String TAG = "SingUp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginon);
        Button loginbutton2 = findViewById(R.id.loginBtn2);
        final Button loginbutton3 = findViewById(R.id.loginBtn3);

        loginbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI();
            }
        });
        loginbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),loginActivity.class);
                startActivity(intent);
            }
        });
    }
        private void updateUI() { // 로그인이 유효하면 넘어가는 기능
            final EditText editText_id = findViewById(R.id.editText_id);
            final EditText editText2_password = findViewById(R.id.editText2_password);
            final String id = editText_id.getText().toString();
            final String password = editText2_password.getText().toString();
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(id, password)
                    .addOnCompleteListener(loginonActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(loginonActivity.this,"패스워드나 이메일이 틀립니다.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        public void getCurrentUser (FirebaseUser user){
            user = FirebaseAuth.getInstance().getCurrentUser();

            if (user != null) {
                // Name, email address, and profile photo Url
                String name = user.getDisplayName();
                String email = user.getEmail();
                Uri photoUrl = user.getPhotoUrl();

                // Check if user's email is verified
                boolean emailVerified = user.isEmailVerified();

                // The user's ID, unique to the Firebase project. Do NOT use this value to
                // authenticate with your backend server, if you have one. Use
                // FirebaseUser.getIdToken() instead.
                String uid = user.getUid();
            }
        }
    static boolean isEmptyOrWhiteSpace(String s){
        if(s==null)return true;
        return s.trim().length()==0;
    }
}

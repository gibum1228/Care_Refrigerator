package com.example.care_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class OptionActivity extends AppCompatActivity {

    Button homeBtn;
    Button loginBtn;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        homeBtn = (Button)findViewById(R.id.homeBtn);
        loginBtn = findViewById(R.id.loginBtn);
        Button darkBtnOn = findViewById(R.id.drakBtnOn);
        Button darkBtnOff = findViewById(R.id.drakBtnOff);
        Button reset = findViewById(R.id.reset);

        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),loginonActivity.class);
                startActivity(intent);
            }
        });
        darkBtnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
        darkBtnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userid = user.getUid();
                mDatabase.getReference().child(userid).setValue(null);
            }
        });
    }
}
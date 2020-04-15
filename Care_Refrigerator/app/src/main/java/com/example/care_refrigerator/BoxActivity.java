package com.example.care_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BoxActivity extends AppCompatActivity {

    Button homeBtn;
    ListView listView;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);

        Intent intent = getIntent();

        String sss = intent.getStringExtra("category");
        Toast.makeText(this, sss, Toast.LENGTH_SHORT).show();

        ObjectKind objKind = (ObjectKind)intent.getSerializableExtra("class");

        // 생성
        homeBtn = (Button)findViewById(R.id.homeBtn);
        listView = (ListView)findViewById(R.id.listView);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        // 스크롤뷰 안에 리스트뷰 스크롤
        listView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent evnet){
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }
}
package com.example.care_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button optionBtn;
    Button boardBtn;
    Button boxBtn;
    Button pushBtn;
    TextView dateText;

    // 액티비티 전환
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardBtn = (Button)findViewById(R.id.boardBtn);
        pushBtn = (Button)findViewById(R.id.pushBtn);
        boxBtn = (Button)findViewById(R.id.boxBtn);
        optionBtn = (Button)findViewById(R.id.optionBtn);
        dateText = (TextView)findViewById(R.id.textView4);

        optionBtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivity(intent);
           }
        });

        boardBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });

        boxBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), BoxActivity.class);
                startActivity(intent);
            }
        });

        pushBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), PushActivity.class);
                startActivity(intent);
            }
        });

        dateText.setText(getTime);

    }

    // 시간 입력받기
    static long nowTime = System.currentTimeMillis();
    Date nowDate = new Date(nowTime);

    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String getTime = simpleDate.format(nowDate);
}

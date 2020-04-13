package com.example.care_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    Button boardBtn = (Button)findViewById(R.id.boardBtn);
    Button boxBtn = (Button)findViewById(R.id.boxBtn);
    Button pushBtn = (Button)findViewById(R.id.pushBtn);
    Button optionBtn = (Button)findViewById(R.id.optionBtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 액티비티 전환
        optionBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, OptionActivity.class);
                startActivity(intent);
            }
        });
        pushBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, PushActivity.class);
                startActivity(intent);
            }
        });
        boxBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, BoxActivity.class);
                startActivity(intent);
            }
        });
    }


}

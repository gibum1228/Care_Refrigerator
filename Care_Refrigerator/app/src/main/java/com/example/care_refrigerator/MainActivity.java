package com.example.care_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    Button optionBtn;
    Button boardBtn;
    Button boxBtn;
    Button pushBtn;

    // 액티비티 전환
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        boardBtn = (Button)findViewById(R.id.boardBtn);
        pushBtn = (Button)findViewById(R.id.pushBtn);
        boxBtn = (Button)findViewById(R.id.boxBtn);
        optionBtn = (Button)findViewById(R.id.optionBtn);

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

        myRef.setValue("Hello, World!");
    }

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("test");



}


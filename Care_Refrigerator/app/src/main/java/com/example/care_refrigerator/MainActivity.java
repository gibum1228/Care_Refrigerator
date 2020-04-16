package com.example.care_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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
        Button save = findViewById(R.id.save);
        final EditText editText_content = findViewById(R.id.editText_content);

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText_content.getText().toString();
                //저장 클릭시 값 db에 저장 구현
            }
        });

        myRef.setValue("Hello, World!");
    }

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("test");



}


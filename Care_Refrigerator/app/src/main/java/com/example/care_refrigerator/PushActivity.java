package com.example.care_refrigerator;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PushActivity extends AppCompatActivity {

    // 생성
    Button homeBtn;
    Button dateInputBtn;
    EditText objectName;
    Spinner spinner;
    GregorianCalendar today = new GregorianCalendar();
    private DatePickerDialog.OnDateSetListener callbackMethod;

    String s = "";
    String spinS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        // 초기화
        homeBtn = (Button) findViewById(R.id.homeBtn);
        dateInputBtn = (Button)findViewById(R.id.dateInputBtn);
        spinner = (Spinner) findViewById(R.id.categoryBox);
        objectName = (EditText)findViewById(R.id.objectName);

        // 액티비티 전환
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // 스피너(콤보 박스)
        String[] items = {"", "육류", "채소류", "유제품", "냉동식품", "기타"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_text_coustom, items);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        // 유통 기한 달력 선택 메소드
        this.InitializeView();
    }

    // 날짜 정보 전달
    private void InitializeView() {
        callbackMethod = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                monthOfYear++;

                s = year + "년 " + monthOfYear + "월 " + dayOfMonth + "일";
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        };
    }

    // 유통기한 버튼 클릭시 달력 보이기
    public void OnClickHandler(View view){
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, today.get(today.YEAR), today.get(today.MONTH), today.get(today.DAY_OF_MONTH));

        dialog.show();
    }

    // Push버튼 눌러서 BoxActivity에 물품 정보 전송
    public void OnClickPush(View view){
        spinS = (String)spinner.getSelectedItem();
        ObjectKind objKind = new ObjectKind(spinS, objectName.getText().toString(), s);

        if(objKind.getCategory().compareTo("") == 0 || objKind.getName().compareTo("") == 0 || objKind.getDate().compareTo("") == 0){
            if(objKind.getCategory().compareTo("") == 0){
                Toast.makeText(this, "분류를 선택해주세요.", Toast.LENGTH_SHORT).show();
            }else if(objKind.getName().compareTo("") == 0 ){
                Toast.makeText(this, "제품명을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "유통기한을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Intent intent = new Intent(PushActivity.this, BoxActivity.class);
            intent.putExtra("class", objKind);

            startActivity(intent);
        }
    }
}

class ObjectKind implements Serializable { // 물품 정보 클래스

    // 필드
    private String category = "";
    private String name = "";
    private String date = "";

    // 생성자
    public ObjectKind(){}
    public ObjectKind(String c, String n, String d){
        this.category = c;
        this.name = n;
        this.date = d;
    }

    // get
    public String getCategory() {
        return this.category;
    }
    public String getName(){
        return this.name;
    }
    public String getDate(){
        return this.date;
    }

    // print
    @Override
    public String toString(){
        return "카테고리: " + this.category +" \n제품명: " + this.name +" \n유통기한: " + this.date;
        // return String.format("카테고리: %s" + " \n제품명: %s" + " \n유통기한: %s", this.category, this.name, this.date);
    }
}
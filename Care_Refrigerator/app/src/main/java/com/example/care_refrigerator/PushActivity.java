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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class PushActivity extends AppCompatActivity {

    private DatabaseReference mPostReference;

    // 생성
    Button homeBtn;
    Button dateEndBtn;
    EditText nameEdit;
    Spinner spinner;
    GregorianCalendar today = new GregorianCalendar(); // 현재
    private DatePickerDialog.OnDateSetListener callbackMethod;

    String s = ""; // 날짜
    String spinS = ""; // 분류

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        // 초기화
        homeBtn = (Button) findViewById(R.id.homeBtn);
        dateEndBtn = (Button)findViewById(R.id.dateEndBtn);
        spinner = (Spinner) findViewById(R.id.categoryBox);
        nameEdit = (EditText)findViewById(R.id.nameEdit);

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

    // Push버튼 눌러서 DB에 데이터 저장
    public void OnClickPush(View view){
        spinS = (String)spinner.getSelectedItem();

        if(spinS.compareTo("") == 0 || s.compareTo("") == 0 || nameEdit.getText().toString().compareTo("") == 0){
            if(spinS.compareTo("") == 0){
                Toast.makeText(this, "분류를 선택해주세요.", Toast.LENGTH_SHORT).show();
            }else if(s.compareTo("") == 0 ){
                Toast.makeText(this, "유통기한을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "제품명을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        }else{

        }

        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        ObjectData objData = new ObjectData(spinS, nameEdit.getText().toString(), s, 1L);
        postValues = objData.toMap();
        childUpdates.put("uesr2/objectData/", postValues);
        mPostReference.updateChildren(childUpdates);
    }

}
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
import java.util.GregorianCalendar;

public class PushActivity extends AppCompatActivity {

    // 생성
    Button homeBtn;
    Button dateInputBtn;
    EditText objectName;
    Spinner spinner;
    GregorianCalendar today = new GregorianCalendar();
    private TextView dateText;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    ArrayAdapter<String> adapter;
    String s = "";

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
        String[] items = {"육류", "채소류", "유제품", "냉동식품", "기타"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_text_coustom, items);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        // 유통 기한 입력 메소드
        this.InitializeView();
        this.InitializeListener();
    }

    private void InitializeListener() {
        dateText = (TextView)findViewById(R.id.dateText);
    }

    // 날짜 정보 전달
    private void InitializeView() {
        callbackMethod = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                monthOfYear = monthOfYear + 1;
                dateText.setText(year + "년 " + monthOfYear + "월 " + dayOfMonth + "일" );
                s = year + "년 " + monthOfYear + "월 " + dayOfMonth + "일";
            }
        };
    }

    // 버튼 클릭시 이벤트 생성
    public void OnClickHandler(View view){
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, today.get(today.YEAR), today.get(today.MONTH), today.get(today.DAY_OF_MONTH));

        dialog.show();
    }

    public void OnClickPush(View view){
        String spinS = (String)spinner.getSelectedItem();

        ObjectKind objKind = new ObjectKind(spinS, objectName.getText().toString(), s);

        Toast.makeText(this, objKind.toString(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, BoxActivity.class);
        intent.putExtra("category", "분류");
        intent.putExtra("class", objKind);

        // startActivity(intent);
        // dateText.setText(objKind.toString());
    }

}

class ObjectKind implements Serializable { // 물품 정보 클래스

    // 필드
    private String category = "";
    private String name = "";
    private String date = "";

    // 생성자
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
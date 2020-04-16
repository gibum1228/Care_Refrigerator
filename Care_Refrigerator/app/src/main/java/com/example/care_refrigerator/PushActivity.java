package com.example.care_refrigerator;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    Button homeBtn;
    Button dateEndBtn;
    Button pushDBBtn;
    EditText nameEdit;
    Spinner spinner;

    private DatabaseReference mPostReference;
    GregorianCalendar today = new GregorianCalendar(); // 현재
    private DatePickerDialog.OnDateSetListener callbackMethod;
    String s = ""; // 날짜
    String spinToS = ""; // 분류

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        // 초기화
        homeBtn = (Button) findViewById(R.id.homeBtn);
        dateEndBtn = (Button)findViewById(R.id.dateEndBtn);
        pushDBBtn = (Button)findViewById(R.id.pushDBBtn);
        spinner = (Spinner) findViewById(R.id.categoryBox);
        nameEdit = (EditText)findViewById(R.id.nameEdit);

        // 스피너(콤보 박스)
        String[] items = {"", "육류", "채소류", "유제품", "냉동식품", "기타"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_text_coustom, items);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

    }

    // 날짜 정보 전달
    private void calenderView() {
        callbackMethod = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                monthOfYear++;

                s = year + "년 " + monthOfYear + "월 " + dayOfMonth + "일";
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        };
    }

    // 실시간 데이터베이스에 정보 전달
    public void pushFirebaseDB(){
        String userEmail = "";
        mPostReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            String userName = user.getDisplayName();
            userEmail = user.getEmail();
            Uri userPhotoUrl = user.getPhotoUrl();

            boolean emailVerified = user.isEmailVerified();

            String uid = user.getUid();
        }

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        ObjectData objData = new ObjectData(spinToS, nameEdit.getText().toString(), s, 1L);
        postValues = objData.toMap();
        childUpdates.put("User/" + userEmail +"/objectData/", postValues);
        mPostReference.updateChildren(childUpdates);
    }

    // 클릭시 발생 이벤트
    public void OnClick(View view){
        switch(view.getId()){
            case R.id.pushDBBtn:
                spinToS = (String)spinner.getSelectedItem();

                // 빈칸 있을 시
                if(spinToS.compareTo("") == 0 || s.compareTo("") == 0 || nameEdit.getText().toString().compareTo("") == 0){
                    if(spinToS.compareTo("") == 0){
                        Toast.makeText(this, "분류를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }else if(s.compareTo("") == 0 ){
                        Toast.makeText(this, "유통기한을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "제품명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    pushFirebaseDB();

                    // 초기화
                    nameEdit.setText("");
                    spinner.setSelection(0);
                    s = "";
                }
                break;
            case R.id.dateEndBtn:
                // 달력 보이기
                DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, today.get(today.YEAR), today.get(today.MONTH), today.get(today.DAY_OF_MONTH));
                dialog.show();

                // 유통 기한 입력 받기
                this.calenderView();

                break;
            case R.id.homeBtn:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;

        }
    }
}

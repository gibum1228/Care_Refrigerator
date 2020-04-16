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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.care_refrigerator.BoxActivity.arrayAdapter;

public class PushActivity extends AppCompatActivity {

    public static ArrayList<String> pastCount = new ArrayList<String>();

    FirebaseAuth mAut;
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
    public static int count;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        try{
            count = arrayAdapter.getCount() + 1;
        }catch (NullPointerException e){
            count = 1;
        }

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
//        String userEmail = "";

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if(user != null){
//            String userName = user.getDisplayName();
//            userEmail = user.getEmail();
//            Uri userPhotoUrl = user.getPhotoUrl();
//
//            boolean emailVerified = user.isEmailVerified();
//
//            String uid = user.getUid();
//        }

        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        ObjectData objData = new ObjectData(ID ,spinToS, nameEdit.getText().toString(), s, 1L);
        postValues = objData.toMap();
        childUpdates.put("admin/" + ID, postValues);
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
                    if(pastCount.isEmpty()){
                        ID = String.valueOf(count);
                    }else{
                        ID = pastCount.get(0).toString();
                        pastCount.remove(0);
                    }
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
                callbackMethod = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        monthOfYear++;

                        s = year + "년 " + monthOfYear + "월 " + dayOfMonth + "일";
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }
                };
                break;
            case R.id.homeBtn:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;

        }
    }
}

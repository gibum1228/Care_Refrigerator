package com.example.care_refrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BoxActivity extends AppCompatActivity {

    Button homeBtn;
    ListView listView;
    ScrollView scrollView;
    Spinner sortSpin;

    ArrayList<String> itemList;
    ArrayAdapter<String> adapter;
    static ObjectKind item = new ObjectKind();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);

        // PushActivity에서 정보 전달 받기
        Intent intent = getIntent();
        // 정보 전달 받았으면 아이템 추가
        if(intent.hasExtra("class")){

            item = (ObjectKind)intent.getSerializableExtra("class");

            Toast.makeText(this, "데이터 전송 받음", Toast.LENGTH_SHORT).show();
        }

        // 생성
        homeBtn = (Button)findViewById(R.id.homeBtn);
        listView = (ListView)findViewById(R.id.listView);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        sortSpin = (Spinner)findViewById(R.id.sortSpin);

        // 버튼 작동
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

        // 스피너 정렬 기준
        String[] sortSpinner = {"-", "분류별", "짧은 순", "가나다"};
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(this, R.layout.spinner_text_coustom, sortSpinner);
        sortSpin.setAdapter(spinAdapter);
        sortSpin.setSelection(0);

        // 리스트뷰 (Sample Item)
        itemList = new ArrayList<String>();
        itemList.add("123124123");
        itemList.add("1111111111");
        itemList.add("555544433");
        itemList.add("555544433");
        itemList.add("555544433");
        itemList.add("555544433");
        itemList.add("555544433");
        itemList.add("555544433");
        itemList.add("555544433");
        itemList.add("555544433");
        itemList.add("555544433");
        itemList.add("555544433");
        itemList.add("555544433");
        itemList.add("555544433");

        // 어댑터 생성 및 설정
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // 여러 항목을 선택할 수 있는 설정

        itemList.add(item.getName());
        adapter.notifyDataSetChanged();
    }

    // ADD, DEL 버튼 클릭 시 실행되는 메소드
    public void OnClickAD(View view){
        EditText ed = (EditText)findViewById(R.id.pushEdit);

        switch(view.getId()){
            case R.id.addBtn: // ADD
                String text = ed.getText().toString(); // EditText에 있는 문자열 값 가져오기

                if(!text.isEmpty()){ // EditText가 비어있지 않으면
                    itemList.add(text);
                    ed.setText("");

                    adapter.notifyDataSetChanged(); // 리스트 목록 갱신
                }
                break;
            case R.id.delBtn: // DEL
                // 싱글 모드일 때 삭제 버튼
//                int pos = listView.getCheckedItemPosition(); // 현재 선택된 항목의 위치값 얻기
//
//                if(pos != ListView.INVALID_POSITION){ // 선택된 항목이 있으면
//                    itemList.remove(pos);
//
//                    listView.clearChoices(); // 리스트뷰 선택 해제
//                    adapter.notifyDataSetChanged(); // 리스트 목록 갱신
//                }
//                break;
                SparseBooleanArray sbArray = listView.getCheckedItemPositions(); // 선택된 아이템의 위치를 알려주는 배열
                // ex) {0=true, 3=true, 4=false, 6=true}

                if(sbArray.size() != 0){
                    for(int i = listView.getCount() - 1; i >= 0; i--){ // 목록의 역순으로 순회하면서 항목 제거
                        if(sbArray.get(i)){
                            itemList.remove(i);
                        }
                    }
                    listView.clearChoices();
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
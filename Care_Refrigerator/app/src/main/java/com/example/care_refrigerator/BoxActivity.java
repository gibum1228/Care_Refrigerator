package com.example.care_refrigerator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.care_refrigerator.PushActivity.pastCount;

public class BoxActivity extends AppCompatActivity {

    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;
    private ChildEventListener mChild;

    Button homeBtn;
    ListView listView;
    ScrollView scrollView;
    Spinner sortSpin;
    String ID;


    public static ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);

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
        String[] sortSpinner = {"", "분류별", "짧은 순", "가나다", "수량"};
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(this, R.layout.spinner_text_coustom, sortSpinner);
        sortSpin.setAdapter(spinAdapter);
        sortSpin.setSelection(0);

        // 어댑터 생성 및 설정
        initDatabase();
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemLongClickListener(longClickListener);

        getFirebaseDB();
    }

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            String[] nowData = arrayData.get(position).split(" ");
            ID = nowData[0];
            pastCount.add(ID);
            AlertDialog.Builder dialog = new AlertDialog.Builder(BoxActivity.this);
            dialog.setTitle("데이터 삭제")
                    .setMessage("해당 데이터를 삭제하시겠습니까?")
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDatabase.getReference().child("admin").child(ID).setValue(null);
                            getFirebaseDB();
                            Toast.makeText(BoxActivity.this, "데이터 삭제 완료", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(BoxActivity.this, "데이터 삭제 취소", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create()
                    .show();
            return false;
        }
    };

    public void getFirebaseDB(){
        // 실시간 업데이트
        mReference = mDatabase.getReference("admin"); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayAdapter.clear();
                arrayData.clear();

                for (DataSnapshot fireData : dataSnapshot.getChildren()) {
                    ObjectData get = fireData.getValue(ObjectData.class);
                    String[] info = {get.id, get.category, get.name, String.valueOf(get.count), get.dateEnd};
                    String msg = info[0] + " " + info[1] + " " + info[2] + " " + info[3] + " " + info[4];
                    arrayData.add(msg);
//                    arrayAdapter.add(msg); // 데이터 저장

                }
                arrayAdapter.addAll(arrayData);
                arrayAdapter.notifyDataSetChanged();
//                listView.setSelection(arrayAdapter.getCount() - 1); // 활성화 시 리스트뷰가 마지막 데이터 기준
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initDatabase() {

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("log");
        mReference.child("log").setValue("check");

        mChild = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReference.removeEventListener(mChild);
    }

}
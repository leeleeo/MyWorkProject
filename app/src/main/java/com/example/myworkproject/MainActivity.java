package com.example.myworkproject;
import androidx.appcompat.app.AppCompatActivity;
import android.R.layout;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.layout.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button main_Start;
    Button main_End;
    Button main_Edit;
    ListView main_ScrollList;
    TextView main_State;
    static String state;

    public String StartTime;
    public String EndTime;

    Button tap1;
    Button tap2;
    Button tap3;
    Button tap4;

    private void bindViews() {
        main_Start = findViewById(R.id.main_Start);
        main_End = findViewById(R.id.main_End);
        main_Edit = findViewById(R.id.main_Edit);
        main_ScrollList = findViewById(R.id.main_ScrollList);
        main_State = findViewById(R.id.main_State);
        tap1 = findViewById(R.id.tap1);
        tap2 = findViewById(R.id.tap2);
        tap3 = findViewById(R.id.tap3);
        tap4 = findViewById(R.id.tap4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.close();
        bindViews();

        main_Start.setOnClickListener(this);
        main_End.setOnClickListener(this);
        main_Edit.setOnClickListener(this);
        tap1.setOnClickListener(this);
        tap2.setOnClickListener(this);
        tap3.setOnClickListener(this);
        tap4.setOnClickListener(this);
    }

    private String getTime(){
        Calendar cal = Calendar.getInstance();
        int y=0, m=0, d=0, h=0, mi=0;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH) +1;
        d = cal.get(Calendar.DAY_OF_MONTH);
        h = cal.get(Calendar.HOUR);
        mi = cal.get(Calendar.MINUTE);
        String date = y+"/"+m+"/"+d+" "+h+":"+mi+":";
        return date;
    }

    @Override
    public void onClick(View v) {
        if(v==main_Start) { //출근 데이터 갱신
            state ="근무 중";
            main_State.setText(getTime()+"\n"+"\t"+state);
            StartTime = getTime();
            insert_goTime(StartTime);
        }else if(v==main_End){ //퇴근 데이터 갱신
            state = "출근 전";
            main_State.setText(getTime()+"\n"+"\t"+state);
            EndTime = getTime();
            insert_offTime(EndTime);
        }else if(v==main_Edit){
            Intent intent = new Intent(this, MainEditActivity.class);
            startActivity(intent);
        }else if(v==tap1){
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        }else if(v==tap2){
            Intent intent2 = new Intent(this, MainEditActivity.class);
            startActivity(intent2);
        }else if(v==tap3){
            Intent intent3 = new Intent(this, MainEditActivity.class);
            startActivity(intent3);
        }else if(v==tap4){
            Intent intent4 = new Intent(this, DepartmentActivity.class);
            startActivity(intent4);
        }
    }

    public void insert_goTime(String time) {
        DBHelper databaseHelper = new DBHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("INSERT INTO cworktbl (wGoTime) VALUES ('"+time+"');");
        Toast.makeText(getApplicationContext(), "출근 성공", Toast.LENGTH_SHORT).show();
        db.close();
    }
    public void insert_offTime(String time) {
        DBHelper databaseHelper = new DBHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("INSERT INTO cworktbl (wOffTime) VALUES ('"+time+"');");
        Toast.makeText(getApplicationContext(), "퇴근 성공", Toast.LENGTH_SHORT).show();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchWorkList();
    }

    private DBHelper dbHelper;

    private void fetchWorkList() {
        if (dbHelper == null) {
            dbHelper = new DBHelper(this);
        }
        List<WorkList> workList = dbHelper.getWorkList();
        ArrayAdapter<WorkList> Adapter = new ArrayAdapter<WorkList>(this , activity_list_item, workList);
        main_ScrollList.setAdapter(Adapter);
    }
}
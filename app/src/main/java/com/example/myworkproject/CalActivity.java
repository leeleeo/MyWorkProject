package com.example.myworkproject;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.myworkproject.databinding.ActivityCalBinding;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.layout.activity_list_item;

public class CalActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCalBinding binding;

    TextView textView;
    CalendarView calendarView;

    ListView listView;

    String dbname = "commute.db";

    String tablename = "cscheduletbl";
    String x;

    String sql;

    SQLiteDatabase db;
    DBHelper helper;

    Cursor cursor;
    Calendar cal = Calendar.getInstance();

    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH)+1;
    int day = cal.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        binding = ActivityCalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalActivity.this, CalEditActivity.class);
                startActivity(intent);
            }
        });

        Button btn_tap1 = (Button) findViewById(R.id.tap1);
        Button btn_tap2 = (Button) findViewById(R.id.tap2);
        Button btn_tap3 = (Button) findViewById(R.id.tap3);
        Button btn_tap4 = (Button) findViewById(R.id.tap4);
        btn_tap1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btn_tap2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                startActivity(intent);
            }
        });
        btn_tap3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CalActivity.class);
                startActivity(intent);
            }
        });

        btn_tap4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DepartmentActivity.class);
                startActivity(intent);
            }
        });

        textView = findViewById(R.id.textView);
        calendarView = findViewById(R.id.calendarView);

        textView.setText(year+"년 "+month+"월 "+day+"일");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                textView.setText(String.format("%d년 %d월 %d일", year,month+1,dayOfMonth));
                textView.setVisibility(View.VISIBLE);

                if(month<9){
                    String x = year+"-"+0+(month+1)+"-"+dayOfMonth;

                    helper = new DBHelper(CalActivity.this);
                    db = helper.getWritableDatabase();
                    sql = "select sText,sStartTime, sEndTime from "+ tablename+" where sStartDate='"+x+"' order by sStartTime ";
                    cursor = db.rawQuery(sql, null);   // select 사용시 사용(sql문, where조건 줬을 때 넣는 값)

                    CalAdapter adapter = new CalAdapter();

                    while (cursor.moveToNext()){
                        adapter.addItemToList(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                    }


                    listView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                }else{
                    String x = year+"-"+(month+1)+"-"+dayOfMonth;

                    helper = new DBHelper(CalActivity.this);
                    db = helper.getWritableDatabase();
                    sql = "select sText,sStartTime, sEndTime from "+ tablename+" where sStartDate='"+x+"' order by sStartTime ";
                    cursor = db.rawQuery(sql, null);   // select 사용시 사용(sql문, where조건 줬을 때 넣는 값)

                    CalAdapter adapter = new CalAdapter();

                    while (cursor.moveToNext()){
                        adapter.addItemToList(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                    }


                    listView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                }


            }
        });

        listView = findViewById(R.id.cal_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Intent intent = new Intent(CalActivity.this, CalEditActivity.class);
                startActivity(intent);
                getData();
            }
        });

        x=year+"-"+0+month+"-"+day;

        selectDB();

    }

    public void selectDB(){

        try {
            helper = new DBHelper(this);
            db = helper.getWritableDatabase();
            sql = "select sText,sStartTime, sEndTime from "+ tablename+" where sStartDate='"+x+"' order by sStartTime ";
            cursor = db.rawQuery(sql, null);   // select 사용시 사용(sql문, where조건 줬을 때 넣는 값)

            CalAdapter adapter = new CalAdapter();

            while (cursor.moveToNext()){
                adapter.addItemToList(cursor.getString(0),cursor.getString(1),cursor.getString(2));
            }


            listView.setAdapter(adapter);

            adapter.notifyDataSetChanged();


        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    public void getData(){
        db = helper.getWritableDatabase();

        String sql= ("select sStartDate, sEndDate, sText,sStartTime, sEndTime from cscheduletbl where sNum=?");

        db.execSQL(sql);
    }
}
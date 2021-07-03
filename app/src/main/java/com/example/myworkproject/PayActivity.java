package com.example.myworkproject;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class PayActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DBHelper helper;

    Calendar cal = Calendar.getInstance();

    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH)+1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);


        TextView textView = findViewById(R.id.textView1);
        textView.setText(year+"."+month);

        Button b1 = findViewById(R.id.btn1);
        Button b2 = findViewById(R.id.btn2);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                calInput(-1);
                textView.setText(year+"."+month);
            }
        });

        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                calInput(1);
                textView.setText(year+"."+month);
            }
        });

        TextView txt1 = findViewById(R.id.textView5);
        /*db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select SUM(pMonthTime*8720) from cpaytbl ", null);
        txt1.setText((CharSequence) cursor);*/

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

    }
    public void calInput(int gap) {
        month += gap;
        if(month <= 0) {
            month = 12;
            year--;
        } else if(month >= 13) {
            month = 1;
            year++;
        }
    }

}
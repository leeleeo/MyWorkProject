package com.example.myworkproject;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import static android.R.layout.activity_list_item;

public class DepartmentActivity extends AppCompatActivity implements View.OnClickListener{
    ListView de_ListView;
    TextView de_DeName;

    DBHelper helper;
    SQLiteDatabase db;

    Button tap1;
    Button tap2;
    Button tap3;
    Button tap4;

    private void bindViews() {
        de_ListView = findViewById(R.id.de_ListView);
        de_DeName = findViewById(R.id.de_DeName);
        tap1 = findViewById(R.id.tap1);
        tap2 = findViewById(R.id.tap2);
        tap3 = findViewById(R.id.tap3);
        tap4 = findViewById(R.id.tap4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        bindViews();
        setDeName();

        tap1.setOnClickListener(this);
        tap2.setOnClickListener(this);
        tap3.setOnClickListener(this);
        tap4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==tap1){
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

    public void setDeName(){
        String name = "";
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor;
        cursor= db.rawQuery("SELECT department FROM cmembertbl",null);

        while (cursor.moveToNext()) {
            name = cursor.getString(0);
        }
        de_DeName.setText(name);
        cursor.close();
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
        List<MemberList> MemberList = dbHelper.getMemberList();
        ArrayAdapter<MemberList> Adapter = new ArrayAdapter<MemberList>(this , activity_list_item, MemberList);
        de_ListView.setAdapter(Adapter);
    }
}
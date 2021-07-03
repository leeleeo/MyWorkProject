package com.example.myworkproject;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class
DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context,"commute.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cmembertbl = "CREATE TABLE IF NOT EXISTS cmembertbl ("+
                "userid   TEXT NOT NULL PRIMARY KEY,"+
                "password   TEXT NOT NULL,"+
                "name   TEXT NOT NULL,"+
                "company   TEXT NOT NULL,"+
                "department   TEXT NOT NULL,"+
                "position   TEXT NOT NULL,"+
                "usertel   TEXT NOT NULL,"+
                "email   TEXT NOT NULL)";
        String cworktbl = "CREATE TABLE IF NOT EXISTS cworktbl ("+
                "wDate   TEXT NOT NULL PRIMARY KEY,"+
                "wMID   TEXT NOT NULL,"+
                "wGoTime   TEXT,"+
                "wOffTime   TEXT,"+
                "wDutyHours   TEXT,"+
                "wExtraHours   TEXT,"+
                "wChange INTERGER NOT NULL,"+ //수정 여부(수정=1, 미수정=0)
                "wChangeDate   TEXT,"+ //수정날짜
                "wChangeCause   TEXT,"+ //수정이유
                "wNewGoTime   TEXT,"+ //수정출근시간
                "wNewOffTime   TEXT,"+ //수정퇴근시간
                "FOREIGN KEY(wMID) REFERENCES cmembertbl(mID))";

        String cpaytbl = "CREATE TABLE IF NOT EXISTS cpaytbl ("+
                "pCode   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "pMID   TEXT NOT NULL,"+
                "pDate   TEXT NOT NULL,"+
                "pMonthTime   TEXT NOT NULL,"+
                "pMonthExtraHours   TEXT,"+
                "pAntiPay   INTEGER NOT NULL,"+
                "pExtraPay   INTEGER,"+
                "pMonthPay   INTEGER NOT NULL,"+
                "FOREIGN KEY(pMID) REFERENCES cmembertbl(mID))";

        String cscheduletbl = "CREATE TABLE IF NOT EXISTS cscheduletbl ("+
                "sNum   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "sMID   TEXT NOT NULL,"+
                "sStartDate   TEXT NOT NULL,"+
                "sEndDate   TEXT NOT NULL,"+
                "sStartTime   TEXT NOT NULL,"+
                "sEndTime   TEXT NOT NULL,"+
                "sText   TEXT NOT NULL,"+
                "FOREIGN KEY(sMID) REFERENCES cmembertbl(mID))";

        db.execSQL(cmembertbl);
        db.execSQL(cworktbl);
        db.execSQL(cpaytbl);
        db.execSQL(cscheduletbl);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
    }

    public boolean insertData(String userid, String password, String name, String company, String department, String position, String usertel, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid", userid);
        contentValues.put("password",password);
        contentValues.put("name", name);
        contentValues.put("company", company);
        contentValues.put("department", department);
        contentValues.put("position", position);
        contentValues.put("usertel", usertel);
        contentValues.put("email",email);
        long result = db.insert("cmembertbl", null, contentValues);

        if(result== -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkuserid(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from cmembertbl where userid = ?", new String[] {userid});
        if(cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkuseridPassword(String userid, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from cmembertbl where userid = ? and password = ?", new String[] {userid,password});

        if(cursor.getCount()>0) {
            return true;
        }
        else {
            return
                    false;
        }
    }
    // work SELECT문
    public ArrayList<WorkList> getWorkList(){
        ArrayList<WorkList> _WorkList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT wGoTime, wOffTime FROM cworktbl", null);
        if(cursor.getCount()!=0){
            //조회온 데이터가 있을때 내부 수행
            while(cursor.moveToNext()){
                String goTime = cursor.getString(cursor.getColumnIndex("wGoTime"));
                String offTime = cursor.getString(cursor.getColumnIndex("wOffTime"));

                WorkList _list = new WorkList();
                _list.setwGoTime(goTime);
                _list.setwOffTime(offTime);
                _WorkList.add(_list);
            }
        }
        cursor.close();
        return _WorkList;
    }
    // member SELECT문
    public ArrayList<MemberList> getMemberList(){
        ArrayList<MemberList> _MemberList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT position, name, usertel, email FROM cmembertbl", null);
        if(cursor.getCount()!=0){
            //조회온 데이터가 있을때 내부 수행
            while(cursor.moveToNext()){
                String Position = cursor.getString(cursor.getColumnIndex("position"));
                String Name = cursor.getString(cursor.getColumnIndex("name"));
                String tel = cursor.getString(cursor.getColumnIndex("usertel"));
                String meil = cursor.getString(cursor.getColumnIndex("email"));

                MemberList _list = new MemberList();
                _list.setposition(Position);
                _list.setname(Name);
                _list.setusertel(tel);
                _list.setemail(meil);
                _MemberList.add(_list);
            }
        }
        cursor.close();
        return _MemberList;
    }
}


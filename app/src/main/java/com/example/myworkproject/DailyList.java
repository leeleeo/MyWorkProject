package com.example.myworkproject;
public class DailyList {

    public String sStartDate;
    public String sEndDate;
    public String sStartTime;
    public String sEndTime;
    public String sText;

    public String getsStartDate() {
        return sStartDate;
    }
    public String getsEndDate() {
        return sEndDate;
    }
    public String getsStartTime() {
        return sStartTime;
    }
    public String getsEndTime() {
        return sEndTime;
    }
    public String getsText() {
        return sText;
    }
    public void setsStartDate(String sStartDate) {
        this.sStartDate = sStartDate;
    }
    public void setsEndDate(String sEndDate) {
        this.sEndDate = sEndDate;
    }
    public void setsStartTime(String sStartTime) {
        this.sStartTime = sStartTime;
    }
    public void setsEndTime(String sEndTime) {
        this.sEndTime = sEndTime;
    }
    public void setsText(String sText) {
        this.sText = sText;
    }
}
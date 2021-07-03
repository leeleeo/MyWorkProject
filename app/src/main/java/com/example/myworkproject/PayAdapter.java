package com.example.myworkproject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PayAdapter extends BaseAdapter {

    ArrayList<PayList> list = new ArrayList<PayList>();

    public PayAdapter(){

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pay_list, parent, false);
        }

        TextView payText = (TextView) convertView.findViewById(R.id.payTextView);

        PayList clist = list.get(position);

        payText.setText("날짜"+"추가시간"+"추가급여");

        return convertView;
    }

    public void addItemToPay(String text, String startTime, String endTime){
        PayList clist = new PayList();

        /*clist.setsText(text);
        clist.setsStartTime(startTime);
        clist.setsEndTime(endTime);*/

        list.add(clist);

    }

}
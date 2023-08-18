package com.globalitians.inquiry.activities.SmsAndNotification.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.SmsAndNotification.Models.CategoryWiseResponse;

import java.util.ArrayList;

public class SmsCategoryAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<CategoryWiseResponse.Message> mAlcategorywise;

    public SmsCategoryAdapter(Activity activity, ArrayList<CategoryWiseResponse.Message> mAlcategorywise) {
        this.activity = activity;
        this.mAlcategorywise= mAlcategorywise;
    }

    @Override
    public int getCount() {
        return mAlcategorywise.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rootview = inflater.inflate(R.layout.my_sms,null,true);
        TextView tv_name = rootview.findViewById(R.id.my_tv_special);
        tv_name.setText(""+mAlcategorywise.get(i).getMessage());
        return rootview;
    }
}

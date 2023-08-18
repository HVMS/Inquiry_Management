package com.globalitians.inquiry.activities.MyTempWork.Activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.globalitians.inquiry.R;import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;

public class MyTempActivity extends AppCompatActivity{

    // layout extra stuff
    private TextView startdate;
    private TextView enddate;
    private TextView show;

    private DatePickerDialog mDatePickerDiaoginquirydate = null;
    private DatePickerDialog mDatePickerDialogJoinedDate = null;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(MyTempActivity.this);
        setContentView(R.layout.activity_my_temp);
        /*findViews();
        initdate();*/
    }

    /*private void initdate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener inquirydatelistner;
        inquirydatelistner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if ((month + 1) < 10 && dayOfMonth < 10) {
                    startdate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if ((month + 1) < 10) {
                    startdate.setText("" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if (dayOfMonth < 10) {
                    startdate.setText("0" + dayOfMonth + "" + (month + 1) + "-" + year);
                } else {
                    startdate.setText("" + dayOfMonth + "" + (month + 1) + "-" + year);
                }
            }
        };

        DatePickerDialog.OnDateSetListener joinedDateListener;
        joinedDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if ((month + 1) < 10 && dayOfMonth < 10) {
                    enddate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if ((month + 1) < 10) {
                    enddate.setText("" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                } else if (dayOfMonth < 10) {
                    enddate.setText("0" + dayOfMonth + "" + (month + 1) + "-" + year);
                } else {
                    enddate.setText("" + dayOfMonth + "" + (month + 1) + "-" + year);
                }
            }
        };

        //initializing date filter date picker dialog
        mDatePickerDiaoginquirydate = new DatePickerDialog(
                MyTempActivity.this, R.style.DialogTheme,inquirydatelistner, year, month, day);
        mDatePickerDiaoginquirydate.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);

        //initializing date filter date picker dialog
        mDatePickerDialogJoinedDate = new DatePickerDialog(
                MyTempActivity.this,R.style.DialogTheme, joinedDateListener, year, month, day);
        mDatePickerDialogJoinedDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private static long getDateDiff(SimpleDateFormat simpleDateFormat, String strInquirydate, String strUpcomingdate) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(strUpcomingdate).getTime()
                    - simpleDateFormat.parse(strInquirydate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void findViews() {
         startdate = findViewById(R.id.startdate);
         enddate = findViewById(R.id.enddate);
         show = findViewById(R.id.show);
         listners();
    }

    private void listners() {
        startdate.setOnClickListener(this);
        enddate.setOnClickListener(this);
        show.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startdate:
                mDatePickerDiaoginquirydate.show();
                break;
            case R.id.enddate:
                mDatePickerDialogJoinedDate.show();
                break;
            case R.id.show:
                int datedifference = (int)(getDateDiff(new SimpleDateFormat("dd-MM-yyyy"), startdate.getText().toString(), enddate.getText().toString()));
                Toast.makeText(this, ""+datedifference, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}

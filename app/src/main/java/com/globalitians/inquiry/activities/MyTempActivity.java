package com.globalitians.inquiry.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.globalitians.inquiry.R;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;

public class MyTempActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linone;
    private LinearLayout lintwo;

    private RelativeLayout relone;
    private RelativeLayout reltwo;

    private Button btnone;
    private Button btntwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(MyTempActivity.this);
        setContentView(R.layout.activity_my_temp);

        findViews();
    }

    private void findViews() {

        relone = findViewById(R.id.rel_calender_one);
        reltwo = findViewById(R.id.rel_calender_two);

        btnone = findViewById(R.id.btn1);
        btntwo = findViewById(R.id.btn2);

        listners();
    }

    private void listners() {
        btnone.setOnClickListener(this);
        btntwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn1:
                Toast.makeText(this, "One is Clicked", Toast.LENGTH_SHORT).show();
                showFirst();
                break;
            case R.id.btn2:
                showSecond();
                Toast.makeText(this, "Second is Clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Simply get out from here", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceAsColor")
    private void showSecond() {
        relone.setVisibility(View.GONE);
        reltwo.setVisibility(View.VISIBLE);
        btnone.setBackgroundResource(R.drawable.extra_bk_two);
        btntwo.setBackgroundResource(R.drawable.extra_bk_one);
        btnone.setTextColor(R.color.colorWhite);
        btntwo.setTextColor(R.color.colorBlackAlpha);
    }

    @SuppressLint("ResourceAsColor")
    private void showFirst() {
        relone.setVisibility(View.VISIBLE);
        reltwo.setVisibility(View.GONE);
        btnone.setBackgroundResource(R.drawable.extra_bk_one);
        btntwo.setBackgroundResource(R.drawable.extra_bk_two);
        btnone.setTextColor(R.color.colorBlackAlpha);
        btntwo.setTextColor(R.color.colorWhite);
    }
}

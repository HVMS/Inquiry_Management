package com.globalitians.inquiry.activities.MyTempWork.Activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.globalitians.inquiry.R;

public class CalenderActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        findViews();
    }

    private void findViews() {
        fab = findViewById(R.id.calendar_fab);

        clicklistners();
    }

    private void clicklistners() {
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.calendar_fab:
//                callBottomSheetDialog();
                break;
        }
    }

    /*private void callBottomSheetDialog() {
        TempDialogBox tempDialogBox = new TempDialogBox();
        tempDialogBox.show(getSupportFragmentManager(),"add tag");
    }*/
}

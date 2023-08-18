package com.globalitians.inquiry.activities.others;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.globalitians.inquiry.R;
import com.globalitians.inquiry.activities.Dashboard.Activities.DashboardActivity;
import com.globalitians.inquiry.activities.Utility.CommonUtil;

import java.util.Random;

import static com.globalitians.inquiry.activities.Utility.CommonUtil.setFullScreenMode;
import static com.globalitians.inquiry.activities.Utility.Constants.KEY_IS_LOGGED_IN;
import static com.globalitians.inquiry.activities.Utility.Constants.LOGIN_PREFRENCES;

public class LauncherActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout=null;
    private SharedPreferences mSharedpreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode(LauncherActivity.this);
        CommonUtil.playSoundForAttendance("" + "Welcome", LauncherActivity.this);

        setContentView(R.layout.activity_launcher);

        initialize();
        findViews();
        setRandomeSplashScreen();

        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mSharedpreferences.getString(KEY_IS_LOGGED_IN,"false").equals("true")){
                    startActivity(new Intent(LauncherActivity.this, DashboardActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(LauncherActivity.this,LoginActivity.class));
                    finish();
                }
            }
        },3000);

    }

    private void initialize() {
        mSharedpreferences = getSharedPreferences(LOGIN_PREFRENCES, Context.MODE_PRIVATE);
        mEditor = mSharedpreferences.edit();
    }

    private void setRandomeSplashScreen() {
        try {
            final int min = 1;
            final int max = 4;
            final int random = new Random().nextInt((max - min) + 1) + min;

            switch (random) {
                case 1:
                    relativeLayout.setBackgroundResource(R.mipmap.ic_splash1);
                    break;
                case 2:
                    relativeLayout.setBackgroundResource(R.mipmap.ic_splash2);
                    break;
                case 3:
                    relativeLayout.setBackgroundResource(R.mipmap.ic_splash3);
                    break;
                case 4:
                    relativeLayout.setBackgroundResource(R.mipmap.ic_splash4);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findViews() {
        relativeLayout = (RelativeLayout)findViewById(R.id.rel_launcher);
    }
}

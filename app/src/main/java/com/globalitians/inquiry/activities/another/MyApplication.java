package com.globalitians.inquiry.activities.another;

import android.app.Application;

import com.globalitians.inquiry.activities.Utility.TypefaceUtil;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF_REGULAR", "fonts/abhayalibre_regular.ttf");
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF_BOLD", "fonts/abhayalibre_bold.ttf");
    }
}
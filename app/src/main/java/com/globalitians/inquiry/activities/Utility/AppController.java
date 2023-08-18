package com.globalitians.inquiry.activities.Utility;

import android.app.Application;
import android.content.Context;

public class AppController extends Application {
    private static AppController mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public static Context getInstance() {
        return mInstance;
    }
}

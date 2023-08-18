package com.globalitians.inquiry.activities.Utility;

import android.Manifest;

public class Permissions {

    public static final String[] STORAGE_PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static final String[] STORAGE_PERMISSIONS2 = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    public static final String[] LOCATION_PERMISSIONS = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
}

/*
 * Copyright (c) 2020 Konekthing.
 */

package com.ridwan.destinasi_jakut.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper {
    public static final String TAG = "Permission";
    Activity activity;
    public static final int PERMISSION_MEDIA = 2, PERMISSION_CAMERA = 3, PERMISSION_MIC = 4;

    public PermissionHelper(Activity activity){
        this.activity = activity;
    }

    public boolean isStoragePermissionGrantedMedia() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_MEDIA);
                return false;
            }
        } else {
            Log.v(TAG, "Permission is granted");
            return false;
        }
    }

    public boolean permissionCamera() {
        int camera = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (camera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
            return false;
        } else {
            return true;
        }
    }

    public boolean isMic(){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_MIC);
            return false;
        }else {
            return true;
        }
    }
}

package com.example.restoapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class DataHelper {
    public static final String IS_LOGIN = "IS_LOGIN";
    Context context;

    public DataHelper(Context context) {
        this.context = context;
    }

    public SharedPreferences getPref() {
        SharedPreferences prefs = context.getSharedPreferences("JuraganDimsum", Context.MODE_PRIVATE);
        return prefs;
    }
}

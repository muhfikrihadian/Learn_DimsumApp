package com.muhfikrih.dimsumapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class DataHelper {
    String key = "RajaDimsum";
    Context context;

    public DataHelper(Context context) {
        this.context = context;
    }

    public SharedPreferences getPrefs() {
        SharedPreferences prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return prefs;
    }

    public Boolean isLoggedIn() {
        String username = getPrefs().getString("Username", "");
        if (username.equalsIgnoreCase("")) {
            return false;
        } else {
            return true;
        }
    }

    public String getRole() {
        String role = getPrefs().getString("Role", "");
        return role;
    }

    public String getUsername() {
        String username = getPrefs().getString("Username", "");
        return username;
    }

    public void deletePref() {
        context.getSharedPreferences(key, 0).edit().clear().commit();
    }
}

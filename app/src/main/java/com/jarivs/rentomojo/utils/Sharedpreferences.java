package com.jarivs.rentomojo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import java.util.HashMap;


public class Sharedpreferences {

    Context context;
    private SharedPreferences pref; //added private
    public static Editor editor;
    private int PRIVATE_MODE = 0;
    private static Sharedpreferences userData = null;

    // Shared Preferences file name

    private static final String PREF_NAME = "com.jarivs.rentomojo";

    public static final String TAG_USER_LOGGED_IN = "userloggedinstatus";

    private String TAG_DATA_SAVED_IN_FIREBASE = "dataSavedInFirebase";




    public Sharedpreferences(Context c) {
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static Sharedpreferences getUserDataObj(Context c) {
        if (userData == null) {
            userData = new Sharedpreferences(c);
        }
        return userData;
    }
    public void clearAll(Context c) {
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        pref.edit().clear().commit();
    }


    /*
        *  Loggedin username
        **/
    public Boolean getIsUserLoggedIn() {
        return pref.getBoolean(TAG_USER_LOGGED_IN, false);
    }

    public void setIsUserLoggedIn(Boolean status) {
        try {
            editor.putBoolean(TAG_USER_LOGGED_IN, status);
            editor.commit();
        } catch (Exception e) {
        }
    }



    public String getDataSavedStatus() {
        return pref.getString(TAG_DATA_SAVED_IN_FIREBASE, "");
    }

    public void setDataSavedStatus(String dataSavedStatus) {
        try {
            editor.putString(TAG_DATA_SAVED_IN_FIREBASE, dataSavedStatus);
            editor.commit();
        } catch (Exception e) {
        }
    }


}


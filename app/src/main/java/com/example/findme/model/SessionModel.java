package com.example.findme.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SessionModel {
    public static final String SESSIOM_LIA_APP       = "sessionLostInAmikom";
    public static final String SESSION_USER_ID       = "sessionUserId";
    public static final String SESSION_USER_NAME     = "sessionUserName";
    public static final String SESSION_EMAIL         = "sessionEmail";
    public static final String SESSION_SUDAH_LOGIN   = "sessionSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SessionModel(Context context){
        sp = context.getSharedPreferences(SESSIOM_LIA_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getUserId(){

        return sp.getString(SESSION_USER_ID, "");
    }

    public String getUserName(){

        return sp.getString(SESSION_USER_NAME, "");
    }

    public String getEmail(){

        return sp.getString(SESSION_EMAIL, "");
    }

    public Boolean getSudahLogin(){

        return sp.getBoolean(SESSION_SUDAH_LOGIN, false);
    }
}


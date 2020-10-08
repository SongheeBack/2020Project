package com.example.a2020project.ForLogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static final String PREF_USER_ID = "userID";
    static final String PREF_USER_PW = "userPW";
    static final String AUTO_LOGIN_ENABLED = "Auto_Login_Enabled";
    //private static SharedPreferences.Editor editor;


    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUserInform(Context ctx, String userID, String userPW, boolean b) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_ID, userID);
        editor.putString(PREF_USER_PW, userPW);
        editor.putBoolean(AUTO_LOGIN_ENABLED, b);
        editor.commit();
    }

    public static Boolean getBoolean(Context ctx, String message){
        return getSharedPreferences(ctx).getBoolean(message, false);
    }


    // 저장된 정보 가져오기
    public static String getUserID(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
    }

    public static String getUserPW(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_PW, "");
    }

    // 로그인 정보 저장 X
    public static void clearUserInform(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }

}

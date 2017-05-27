package com.islington.nepalbloodbankdonor.Helper;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class GlobalState extends Application {

    SharedPreferences checkLogin;
    SharedPreferences.Editor checkLoginEditor;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editSharedPreferences;
    SharedPreferences validUserInfo;
    SharedPreferences.Editor validUserInfoEditor;


    public static GlobalState singleton;

    public static final String PREFS_IS_LOGGED_IN = "is logged in"; // to check if user is logged in


    public static final String PREFS_CHECK_LOGIN = "check login";
    public static final String MyPref="MyPreferences";
    public static  final String usernamekey="username";
    public static final String PREFS_VALID_USER_INFO = "valid user info"; // to store the user information like name and phone number


    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        super.onCreate();

        checkLogin = this.getSharedPreferences(PREFS_CHECK_LOGIN, 0);
        checkLoginEditor = checkLogin.edit();


        validUserInfo = this.getSharedPreferences(PREFS_VALID_USER_INFO, 0);
        validUserInfoEditor = validUserInfo.edit();

        sharedPreferences=this.getSharedPreferences(MyPref,Context.MODE_PRIVATE);
        editSharedPreferences =sharedPreferences.edit();

        singleton = this;
    }

    /**
    /**
     * @return MySIngleton instance
     */
    public GlobalState getInstance() {
        return singleton;
    }




    //For Check Login
    public String getPrefsCheckLogin()

    {

        return checkLogin.getString(PREFS_CHECK_LOGIN,"");
    }

    public void setPrefsCheckLogin(String value, int resultCode){
        if(resultCode==1){
            checkLoginEditor.putString(PREFS_CHECK_LOGIN,value).commit();
        }else {
            checkLoginEditor.remove(PREFS_CHECK_LOGIN).commit();
        }
    }
    public String getUserNameKey(){

        return sharedPreferences.getString(usernamekey,"");


    }
    public String getPREFS_VALID_USER_INFO(){
        return validUserInfo.getString(PREFS_VALID_USER_INFO,"");
    }

    public void setPrefsValidUserInfo(String validUser,int resultCode){
        if (resultCode == 1) {
            validUserInfoEditor.putString(PREFS_VALID_USER_INFO, validUser).commit();
        } else {
            validUserInfoEditor.remove(PREFS_VALID_USER_INFO).commit();
        }
    }

}

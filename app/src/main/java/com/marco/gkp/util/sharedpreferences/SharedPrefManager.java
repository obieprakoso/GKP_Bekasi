package com.marco.gkp.util.sharedpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.marco.gkp.activity.HomeActivity;
import com.marco.gkp.activity.LoginActivity;

import java.util.HashMap;


public class SharedPrefManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String NO_REGIS = "NO_REGIS";
    public static final String ALAMAT = "ALAMAT";

    public SharedPrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String name, String no_regis, String alamat){

        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(NO_REGIS, no_regis);
        editor.putString(ALAMAT, alamat);
        editor.apply();

    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){

        if (this.isLoggin()) {
            Intent i = new Intent(context, HomeActivity.class);
            //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);
            ((LoginActivity) context).finish();

        }
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(NO_REGIS, sharedPreferences.getString(NO_REGIS, null));
        user.put(ALAMAT, sharedPreferences.getString(ALAMAT, null));

        return user;
    }

    public void logout(){

        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
        ((HomeActivity) context).finish();

    }
}

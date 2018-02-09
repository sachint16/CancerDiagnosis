package com.example.pawan_pc.cancerdiaganosis;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;
import java.util.Map;

/**
 * Created by Pawan-PC on 12-10-2017.
 */
public class PrefrenceManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "College_project";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefrenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setLocation(String lat,String longi){
        editor.putString("latitude",lat);
        editor.putString("longitude",longi);
        editor.commit();
    }
    public String[] getLocation(){
        String[] data=new String[]{pref.getString("latitude","28.3909655"),pref.getString("longitude","77.3817044")};
        return data;
    }
    public void setLoginState(boolean state){
        editor.putBoolean("s",state);
        editor.commit();
    }
    public boolean getLoginState(){
        return pref.getBoolean("s",false);
    }
    public void setEmail(String email){
        editor.putString("e",email);
        editor.commit();
    }
    public String getEmail(){
        return pref.getString("e",null);
    }
}

package com.qiito.umepal.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.qiito.umepal.Constants.Constants;
import com.qiito.umepal.holder.UserBaseHolder;

/**
 * Created by aswathy on 12/1/16.
 */
public class SharedPreference {

    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "UMEPAL";


    // Selected Table (make variable public to access from outside)
    public static final String USER_ID = "userId";
    public static final String TOKEN="token";
    public static final String ROLE ="role";
    public static final String PROVIDER_ID = "providerId";


    // Constructor
    public SharedPreference(Context context){
        this._context = context;
        Log.e("activity>>>",""+_context);
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public SharedPreference() {

    }

    public void save(int userid,String token) {

        editor.putInt(USER_ID, userid);
        editor.putString(TOKEN,token);
        editor.commit();
    }
    public void saveUserdetails(UserBaseHolder userBaseHolder) {

        Gson gson = new Gson();
        String json = gson.toJson(userBaseHolder);
        editor.putString("userBaseHolder", json);
        editor.commit();
    }
    public UserBaseHolder getdetails(){
        Gson gson = new Gson();
        String json = pref.getString("userBaseHolder", "");
        UserBaseHolder obj = gson.fromJson(json, UserBaseHolder.class);
        return obj;
    }
    public int getUserId() {
        int text;

        text = pref.getInt(USER_ID,0);
        return text;
    }
    public String getToken(){
        String token=pref.getString(TOKEN, null);
        return token;
    }


    public void Role (String role){

        editor.putString(ROLE, role);

        editor.commit();
    }

    public String getRole() {
        String currentRole = pref.getString(ROLE,null);
        return currentRole;
    }

    public void removeData(){

        editor.clear().commit();

    }

    public void saveProviderId(int providerId){
        editor.putInt(PROVIDER_ID, providerId);
        editor.commit();
    }

    public int getProviderId(){
        int text;

        text = pref.getInt(PROVIDER_ID,0);
        return text;
    }

}

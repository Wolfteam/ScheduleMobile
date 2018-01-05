package com.wolfteam20.schedulemobile.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext;

import javax.inject.Inject;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public class PreferencesHelper implements PreferencesHelperContract {

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_FULLNAME= "PREF_KEY_FULLNAME";
    private static final String PREF_KEY_USERNAME = "PREF_KEY_USERNAME";
//    private static final String PREF_KEY_EMAIL = "PREF_KEY_EMAIL";
//    private static final String PREF_KEY_THEME = "PREF_KEY_THEME";

    private Context mContext;
    private SharedPreferences mPrefs;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context, String preferencesName) {
        mContext = context;
        //Este metodo crea las preferencias con el nombre que le pases aunque no existan
        mPrefs = mContext.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
    }

    @Override
    public void storeAccessToken(String token) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, token).apply();
    }

    @Override
    public String getToken() {
        String token = "";
        if (mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null) != null)
            token = mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
        return token;
    }

    @Override
    public String getUsername() {
        return mPrefs.getString(PREF_KEY_USERNAME, null);
    }

    @Override
    public String getFullname() {
        return mPrefs.getString(PREF_KEY_FULLNAME, null);
    }
}

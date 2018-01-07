package com.wolfteam20.schedulemobile.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.wolfteam20.schedulemobile.di.qualifiers.ApplicationContext;
import com.wolfteam20.schedulemobile.utils.Constants;
import com.wolfteam20.schedulemobile.utils.JWTUtilities;

import javax.inject.Inject;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public class PreferencesHelper implements PreferencesHelperContract {
    private Context mContext;
    private SharedPreferences mPrefs;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context, String preferencesName) {
        mContext = context;
        //Este metodo crea las preferencias con el nombre que le pases aunque no existan
        mPrefs = mContext.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
    }

    @Override
    public String getToken() {
        String token = "";
        if (mPrefs.getString(Constants.PREF_KEY_ACCESS_TOKEN, null) != null)
            token = mPrefs.getString(Constants.PREF_KEY_ACCESS_TOKEN, null);
        return token;
    }

    @Override
    public boolean isUserAdmin() {
        return mPrefs.getBoolean(Constants.PREF_IS_USER_ADMIN, false);
    }

    @Override
    public String getUsername() {
        return mPrefs.getString(Constants.PREF_KEY_USERNAME, null);
    }

    @Override
    public String getFullname() {
        return mPrefs.getString(Constants.PREF_KEY_FULLNAME, null);
    }

    @Override
    public void storeAccessToken(String token) {
        mPrefs.edit().putString(Constants.PREF_KEY_ACCESS_TOKEN, token).apply();
    }

    @Override
    public void storeUserRole() {
        boolean isUserAdmin = JWTUtilities.isUserAdmin(getToken());
        mPrefs.edit().putBoolean(Constants.PREF_IS_USER_ADMIN, isUserAdmin).apply();
    }
}

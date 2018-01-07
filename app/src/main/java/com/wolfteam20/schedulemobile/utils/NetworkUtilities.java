package com.wolfteam20.schedulemobile.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Efrain.Bastidas on 1/4/2018.
 */

public class NetworkUtilities {
    public static boolean isNetworkAvailable(Context context) {
        boolean isConnected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            isConnected = cm.getActiveNetworkInfo().isConnected();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return isConnected;
    }
}
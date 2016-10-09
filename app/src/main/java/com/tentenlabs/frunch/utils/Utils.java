package com.tentenlabs.frunch.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by seerasu1 on 09/10/16.
 */
public class Utils {

    public static boolean checkNetworkStatus(Context context) {
        ConnectivityManager check = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo cm = check.getActiveNetworkInfo();
        boolean isConnected = (cm != null) && cm.isAvailable() && cm.isConnected();
        return isConnected;
    }
}

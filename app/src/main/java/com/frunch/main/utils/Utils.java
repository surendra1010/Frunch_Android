package com.frunch.main.utils;

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

    public static boolean isValid(String value) {
        if(value != null && value.length() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean passwordCheck(String password, String confirmPassword) {
        if(password.equals(confirmPassword)) {
            return true;
        }
        else {
            return false;
        }
    }
}

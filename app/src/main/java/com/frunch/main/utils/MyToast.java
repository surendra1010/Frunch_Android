package com.frunch.main.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by seerasu1 on 09/10/16.
 */
public class MyToast {

    public static void showToast(Context context,String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}

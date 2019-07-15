package com.example.zomfit.utils;

import android.content.Context;
import android.widget.Toast;

public class BasicUtils {

    public static void makeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

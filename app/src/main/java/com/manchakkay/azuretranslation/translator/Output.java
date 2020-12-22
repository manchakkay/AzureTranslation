package com.manchakkay.azuretranslation.translator;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Output {
    public static void out(String msg) {
        Log.d("Translator", msg);
    }

    public static void tl(Context c, String msg) {
        Toast toast = Toast.makeText(c, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void ts(Context c, String msg) {
        Toast toast = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}

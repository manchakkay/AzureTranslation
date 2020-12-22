package com.manchakkay.azuretranslation.translator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;

public class Data {
    final String SETTINGS_FILENAME = "Languages";
    final String LIST_KEY = "Languages";
    final String DATE_KEY = "Date";

    final SharedPreferences s;

    @SuppressLint("CommitPrefEdits")
    Data(Context c) {
        s = c.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);
    }

    public Boolean verify() {
        if (s.contains(LIST_KEY) && s.contains(DATE_KEY)) {
            String temp_list = s.getString(LIST_KEY, "");
            String temp_date = s.getString(DATE_KEY, "0");

            if (temp_date != null && temp_list != null && !temp_list.equals("")) {

                try {
                    Date temp_date_obj = new Date(Long.parseLong(temp_date));
                    new Gson().fromJson(temp_list, new TypeToken<ArrayList<Pair<String, String>>>() {
                    }.getType());

                    long delay_hours = Math.abs(System.currentTimeMillis() - temp_date_obj.getTime()) / (3600000);

                    return delay_hours < 24;
                } catch (Exception e) {
                    return false;
                }

            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    public ArrayList<Pair<String, String>> get_languages() {
        String temp_list = s.getString(LIST_KEY, "");
        return new Gson().fromJson(temp_list, new TypeToken<ArrayList<Pair<String, String>>>() {
        }.getType());
    }

    public void save_languages(ArrayList<Pair<String, String>> temp_list) {
        try {
            String temp_list_str = new Gson().toJson(temp_list);

            s.edit().clear()
                    .putString(LIST_KEY, temp_list_str)
                    .putString(DATE_KEY, String.valueOf(System.currentTimeMillis()))
                    .apply();

        } catch (Exception e) {
            Output.out("<ERROR> -> " + e.getLocalizedMessage());
        }
    }
}

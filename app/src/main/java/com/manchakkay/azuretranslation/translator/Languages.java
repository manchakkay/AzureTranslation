package com.manchakkay.azuretranslation.translator;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.manchakkay.azuretranslation.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Languages {
    public ArrayList<Pair<String, String>> list;
    private final MainActivity main_activity;
    private final Data data;
    private final HashMap<String, View> views;

    Languages(MainActivity m, HashMap<String, View> v) {
        main_activity = m;
        views = v;
        data = new Data((Context) main_activity);
        if (data.verify()) {
            list = data.get_languages();

            Output.out("<SUCCESS> -> Reading languages from Storage");
            Output.out("<DATA> -> Languages list: \n" + list.toString());
            Output.ts((Context)main_activity, "Reading languages from Storage");

            show_languages();
        } else {
            this.get_languages();

            Output.out("<PROCESS> -> Loading languages from Azure");
            Output.ts((Context)main_activity, "Loading languages from Azure");
        }
    }

    public void get_languages() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RetrofitAPI.API_URL)
                .build();
        RetrofitAPI API = retrofit.create(RetrofitAPI.class);

        Call<GSON_Languages> call = API.get_languages();
        call.enqueue(new Languages.CallBack());
    }

    public void show_languages() {
        ListView lv = ((ListView) views.get("lang_list"));
        if (lv != null) {
            LanguagesAdapter adapter = new LanguagesAdapter(main_activity, this);
            lv.setAdapter(adapter);
        }

    }

    class CallBack implements retrofit2.Callback<GSON_Languages> {

        @Override
        public void onResponse(@NonNull Call<GSON_Languages> call, Response<GSON_Languages> response) {
            if (response.isSuccessful() && response.body() != null) {
                list = parse(response.body());
                data.save_languages(list);

                Output.out("<SUCCESS> -> Languages are loaded");
                Output.out("<DATA> -> Languages list: \n" + list.toString());
                Output.ts((Context)main_activity, "Languages are loaded");

                show_languages();
            } else {
                Output.out("<ERROR> -> " + response.code());
                Output.ts((Context)main_activity, "Languages aren't loaded");
            }
        }

        @Override
        public void onFailure(@NonNull Call<GSON_Languages> call, Throwable t) {
            Output.out("<ERROR> -> " + t.getLocalizedMessage());
            Output.ts((Context)main_activity, "Languages aren't loaded");
        }

        public ArrayList<Pair<String, String>> parse(GSON_Languages source) {
            ArrayList<Pair<String, String>> temp_list = new ArrayList<>();

            for (String k : source.translation.keySet()) {
                GSON_Language g = source.translation.get(k);

                if (g != null && g.name != null) {
                    Pair<String, String> p = new Pair<>(k, g.name);
                    temp_list.add(p);
                }
            }

            return temp_list;
        }
    }
}

class GSON_Languages {
    Map<String, GSON_Language> translation;
}

class GSON_Language {
    String name;
}

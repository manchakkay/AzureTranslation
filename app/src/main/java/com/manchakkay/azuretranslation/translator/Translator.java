package com.manchakkay.azuretranslation.translator;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.manchakkay.azuretranslation.MainActivity;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Translator {
    final MainActivity main_activity;
    final HashMap<String, View> views;

    public String language_selected = "";
    final Languages languages;

    public Translator(MainActivity m, HashMap<String, View> v) {
        main_activity = m;
        views = v;

        languages = new Languages(main_activity, views);
    }

    public void translate_text() {
        Output.out("<PROCESS> -> Translating text");

        EditText in_view = ((EditText) views.get("field_in"));

        if (in_view != null && !in_view.getText().toString().equals("")) {
            if (!language_selected.equals("")) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(RetrofitAPI.API_URL)
                        .build();
                RetrofitAPI API = retrofit.create(RetrofitAPI.class);

                GSON_Input temp_input = new GSON_Input();

                temp_input.Text = in_view.getText().toString();
                GSON_Input[] temp_inputs = {temp_input};

                Call<GSON_Output[]> call = API.get_translation(RetrofitAPI.API_URL + RetrofitAPI.POST_URL + language_selected, temp_inputs);
                call.enqueue(new Translator.CallBack());
            } else {
                Output.out("<WARNING> -> Language not selected");
                Output.ts((Context)main_activity, "Language not selected");
            }
        } else {
            Output.out("<WARNING> -> Text field is empty");
            Output.ts((Context)main_activity, "Text field is empty");
        }
    }

    class CallBack implements retrofit2.Callback<GSON_Output[]> {
        @Override
        public void onResponse(@NonNull Call<GSON_Output[]> call, Response<GSON_Output[]> response) {

            if (response.isSuccessful()) {
                Output.out("<SUCCESS> -> Text are translated");
                Output.ts((Context)main_activity, "Text are translated");

                TextView output = (TextView) views.get("field_out");
                if(output != null && response.body() != null){
                    output.setText(response.body()[0].translations[0].text);
                }
            } else {
                if(response.errorBody() != null) {
                    try {
                        Output.out("<ERROR> -> " + response.errorBody().string());
                        Output.ts((Context)main_activity, "Text aren't translated");
                    } catch (IOException e) {
                        Output.out("<ERROR> -> Text aren't translated");
                        Output.ts((Context)main_activity, "Text aren't translated");
                    }
                } else {
                    Output.out("<ERROR> -> Text aren't translated");
                    Output.ts((Context)main_activity, "Text aren't translated");
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<GSON_Output[]> call, Throwable t) {

            Output.out("<ERROR> -> " + t.getLocalizedMessage());
            Output.ts((Context)main_activity, "Text aren't translated");

        }
    }
}

class GSON_Input {
    String Text;
}

class GSON_Output {
    GSON_Output_text[] translations;
}

class GSON_Output_text {
    String text;
}


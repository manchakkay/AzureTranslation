package com.manchakkay.azuretranslation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.manchakkay.azuretranslation.translator.Translator;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Translator translator;
    final HashMap<String, View> views = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        views.put("field_in", (View) findViewById(R.id.input));
        views.put("field_out", (View) findViewById(R.id.output));
        views.put("translate_button", (View) findViewById(R.id.button));
        views.put("lang_list", (View) findViewById(R.id.languages));
        views.put("lang_text", (View) findViewById(R.id.selected_lang));

        translator = new Translator(MainActivity.this, views);
    }


    @SuppressLint("SetTextI18n")
    public void set_language(Pair<String, String> p) {
        translator.language_selected = p.first;

        TextView tv = ((TextView)views.get("lang_text"));
        if (tv != null){
            tv.setText("Выбранный язык — " + p.second);
        }
    }

    public void translate(View v) {
        translator.translate_text();
    }
}

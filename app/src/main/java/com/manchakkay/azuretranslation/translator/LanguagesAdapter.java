package com.manchakkay.azuretranslation.translator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.manchakkay.azuretranslation.MainActivity;

public class LanguagesAdapter extends BaseAdapter {
    final Languages languages;
    final MainActivity main_activity;

    public LanguagesAdapter(MainActivity m, Languages l) {
        this.languages = l;
        this.main_activity = m;
    }

    @Override
    public int getCount() {
        return languages.list.size();
    }

    @Override
    public Object getItem(int position) {
        return languages.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from((Context) main_activity).inflate(com.manchakkay.azuretranslation.R.layout.languages, parent, false);
        Pair<String, String> l = languages.list.get(position);

        TextView title = convertView.findViewById(com.manchakkay.azuretranslation.R.id.title);
        title.setText(l.second);

        convertView.setOnClickListener(v -> main_activity.set_language(l));

        return convertView;
    }
}

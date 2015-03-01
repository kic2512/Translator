package com.example.kic.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by kic on 3/1/15.
 */
public class SettingsActivity extends ActionBarActivity {
    private String lang = "en-ru";
    private Spinner spinner_from = null;
    private Spinner spinner_to = null;
    private Button button_save = null;
    private Button button_back = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinner_from = (Spinner)findViewById(R.id.spinner_from);
        spinner_to = (Spinner)findViewById(R.id.spinner_to);
        button_save = (Button)findViewById(R.id.button_save_translate_lang);
        button_back = (Button)findViewById(R.id.button_back);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang = String.valueOf(spinner_from.getSelectedItem()) +
                        "-" +
                        String.valueOf(spinner_to.getSelectedItem());
                Intent  main_activity = new Intent(SettingsActivity.this, MainActivity.class);
                main_activity.putExtra("lang", lang);
                setResult(Activity.RESULT_OK, main_activity);
                finish();
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  main_activity = new Intent(SettingsActivity.this, MainActivity.class);
                main_activity.putExtra("lang", lang);
                setResult(Activity.RESULT_OK, main_activity);
                finish();
            }
        });

    }
}

package com.example.kic.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by kic on 2/17/15.
 */
public class SecondActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void onClick(View view) {
        Intent  main_activity = new Intent(SecondActivity.this, MainActivity.class);
        setResult(Activity.RESULT_OK, main_activity);
        finish();
    }
}

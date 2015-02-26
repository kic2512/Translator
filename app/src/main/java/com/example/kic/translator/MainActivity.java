package com.example.kic.translator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kic.translator.utils.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {
    final String lang = "en-ru";
    private TextView translatedWord = null;
    private EditText initialWord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        translatedWord = (TextView) findViewById(R.id.translatedWordView);
        initialWord = (EditText) findViewById(R.id.initialWordText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) throws IOException, JSONException {
        GetTranslateFromURL getTranslateFromURL = new GetTranslateFromURL();
        String word = initialWord.getText().toString();
        String url_word_translate = getString(R.string.yandex_translate_URL) +
                                    "?key=" + getString(R.string.yandex_translate_key) +
                                    "&text=" + word +
                                    "&lang=" + lang;
        getTranslateFromURL.execute(url_word_translate);
    }

    public void onClickSecondActivity(View view) {
        Intent second_activity = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(second_activity);
    }

    public class GetTranslateFromURL extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... URL) {
            JSONObject json = null;
            try {
                json = JsonParser.getJsonFromUrl(URL[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (json != null)
                try {
                    JSONArray translate_text_arr = json.getJSONArray("text");
                    String translate_string = translate_text_arr.get(0).toString();

                    publishProgress(translate_string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            translatedWord.setText(values[0]);
        }
    }

}

package com.example.kic.translator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
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
    final String URL_GET_TRANSLATE = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20150217T101240Z.94ed0457947c725e.dfd4eac0e7d9b45dad578129dc354024e4ce3a69";
    final String lang = "en-ru";
    private TextView wordString = null;
    private EditText inputWord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordString = (TextView) findViewById(R.id.textView);
        inputWord = (EditText) findViewById(R.id.editText);
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

        //wordString.setText("Change label !");

        GetTranslateFromURL getTranslateFromURL = new GetTranslateFromURL();
        String word = inputWord.getText().toString();
        String url_word_translate = URL_GET_TRANSLATE + "&" + "text=" + word + "&lang=" + lang;
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
                    String translate_string = "";
                    for(int i=0;i<translate_text_arr.length(); ++i){
                        translate_string += translate_text_arr.getString(i);
                    }
                    publishProgress(translate_string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            wordString.setText(values[0]);
        }
    }

}

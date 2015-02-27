package com.example.kic.translator;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kic.translator.utils.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {
    final String lang = "en-ru";                        // TODO
    private TextView translatedWordView = null;
    private EditText initialWordText = null;
    private ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialWordText = (EditText) findViewById(R.id.initialWordText);
        translatedWordView = (TextView) findViewById(R.id.translatedWordView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
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
        String word = initialWordText.getText().toString();
        String url_word_translate = getString(R.string.yandex_translate_URL) +
                                    "?key=" + getString(R.string.yandex_translate_key) +
                                    "&text=" + word +
                                    "&lang=" + lang;
        getTranslateFromURL.execute(url_word_translate);
    }

    public class GetTranslateFromURL extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... URL) {
            publishProgress();
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
                    JSONArray jsonArray = json.getJSONArray("text");
                    String translatedWord = jsonArray.get(0).toString();
                    return translatedWord;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return "Oops... please, try again :)";
        }

        @Override
        protected void onProgressUpdate(Void... v) {
            super.onProgressUpdate(v);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String translatedWord) {
            super.onPostExecute(translatedWord);
            progressBar.setVisibility(View.INVISIBLE);
            translatedWordView.setText(translatedWord);
        }
    }

}

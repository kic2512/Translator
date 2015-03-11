package com.example.kic.translator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kic.translator.utils.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends ActionBarActivity {
    private String lang = null;                        // TODO
    private TextView translatedWordView = null;
    private EditText initialWordText = null;
    private ProgressBar progressBar = null;
    private final int REQUEST_CODE_LANG = 1;
    private Button button_translate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialWordText = (EditText) findViewById(R.id.initialWordText);
        translatedWordView = (TextView) findViewById(R.id.translatedWordView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        lang = getString(R.string.start_lang);
        translatedWordView.setMovementMethod(new ScrollingMovementMethod());
        button_translate = (Button)findViewById(R.id.translateButton);
        button_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetTranslateFromURL getTranslateFromURL = new GetTranslateFromURL();
                String word = initialWordText.getText().toString();
                try {
                    word = URLEncoder.encode(word, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String url_word_translate = getString(R.string.yandex_translate_URL) +
                        "?key=" + getString(R.string.yandex_translate_key) +
                        "&text=" + word +
                        "&lang=" + lang;
                getTranslateFromURL.execute(url_word_translate);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.putExtra("lang", lang);
            startActivityForResult(intent, REQUEST_CODE_LANG);
            return true;
        }
        if (id == R.id.main_action_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }
        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case REQUEST_CODE_LANG:
                    lang = data.getStringExtra("lang");
                // PLACE FOR OTHER ACTIVITY
            }
    }

    public class GetTranslateFromURL extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... URL) {
            JSONObject json = null;
            try {
                json = JsonParser.getJsonFromUrl(URL[0]);
                publishProgress();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            if (json != null)
                try {
                    JSONArray jsonArray = json.getJSONArray("text");
                    return jsonArray.get(0).toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return "Oops... please, try again :)";
        }

        @Override
        protected void onProgressUpdate(Void... v) {
            super.onProgressUpdate(v);
        }

        @Override
        protected void onPostExecute(String translatedWord) {
            super.onPostExecute(translatedWord);
            progressBar.setVisibility(View.INVISIBLE);
            translatedWordView.setText(translatedWord);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
    }

}

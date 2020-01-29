package com.example.httptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView _tv;
    private EditText _et;
    private String _s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void extract(View view) {
        _tv = findViewById(R.id.textview);
        _et = findViewById(R.id.urlfield);
        _s = _et.getText().toString();
        new HTMLExtractor().execute();
    }
    public class HTMLExtractor extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            _tv.setText(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            String text = "";
            try {
                URL url = new URL(_s);
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                while (br.readLine() != null) {
                    text += br.readLine();
                }
                br.close();
                do {
                    int start = text.indexOf("<");
                    int end = text.indexOf(">");
                    String sub = text.substring(start,end);
                    text.replace(sub,"");
                } while (text.indexOf("<") != -1);
                return text;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return text;
        }
    }
}
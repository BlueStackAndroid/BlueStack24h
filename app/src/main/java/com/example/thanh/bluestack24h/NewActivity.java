package com.example.thanh.bluestack24h;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class NewActivity extends AppCompatActivity {

    WebView webView;
    String link, title, description, date;
    String detail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        webView = (WebView) findViewById(R.id.webView);


//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setTitle("");

        Intent intent = getIntent();
        link = intent.getStringExtra("link");


        new GetData().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public class GetData extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(link).get();

//                Log.i("doc", "doInBackground: " + doc);

                title = doc.title();
                Elements date = doc.select("span.fr fon7 mr2 tt-capitalize");
                Elements description = doc.select("h3");

                doc.removeClass(".xemthem_new_ver width_common");
                String mota = description.text();

                Elements main = doc.select("#left_calculator");
//                Log.i("main", "main: " + main.text());
                detail += "<h2 style = \" color: red \">" + title
                        + "</h2>";
                detail += "<font size=\" 1.2em \" style = \" color: #005500 \"><em>"
                        + date.text() + "</em></font>";
                detail += "<p style = \" color: #999999 \"><b>" + "<font size=\" 4em \" >"
                        + mota + "</font></b></p>";
                detail += "<font size=\" 4em \" >"+  main + "</font>";

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            webView.loadDataWithBaseURL(
                    link,
                    "<style>img{display: inline;height: auto;max-width: 100%;}"
                            + " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
                            + detail, "text/html", "UTF-8", "");

        }


    }
}

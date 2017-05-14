package com.example.thanh.bluestack24h;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.thanh.bluestack24h.SQLite.Database;
import com.example.thanh.bluestack24h.adapter.AppAdapter;
import com.example.thanh.bluestack24h.rss.RSSItem;

import java.util.ArrayList;

public class RecenActivity extends AppCompatActivity {

    private Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Tin gần đây");

        ListView listView = (ListView) findViewById(R.id.listView);

        database = new Database(this);
        database.open();
        final ArrayList<RSSItem> listItems = database.getItems();
        AppAdapter appAdapter = new AppAdapter(listItems, getApplicationContext());
        database.close();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RSSItem item = listItems.get(i);

                Intent intent = new Intent(getApplicationContext(), NewActivity.class);
                intent.putExtra("link", item.getLink());
                startActivity(intent);
            }
        });


        listView.setAdapter(appAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                return true;
        }
        return true;
    }
}

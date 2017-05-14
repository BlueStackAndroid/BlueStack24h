package com.example.thanh.bluestack24h;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.thanh.bluestack24h.SQLite.Database;
import com.example.thanh.bluestack24h.adapter.AppAdapter;
import com.example.thanh.bluestack24h.rss.RSSItem;
import com.example.thanh.bluestack24h.rss.RssParser;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;


public class ChuyenMucFragment extends Fragment {

    ArrayList<RSSItem> items;
    RssParser rssParser = new RssParser();
    ListView listView;
    Database database;

    int page;
    private String url;

    public ChuyenMucFragment(String url){
        this.url = url;
        items = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_chuyen_muc,container,false);
        initImageLoader(getActivity());
        database = new Database(getActivity());
        listView = (ListView) view.findViewById(R.id.listView1);

        new GetListItem(getContext()).execute();

        /*listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0){
//                    new GetListItem(getContext()).execute();
                }
            }
        });*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                database.open();
                ArrayList<RSSItem> listItems = database.getItems();
                RSSItem item = items.get(i);
//                if (!listItems.contains(item)){
//                    database.addItem(item);
//                }
                boolean check = false;
                for (RSSItem rssItem : listItems) {

                    if (rssItem.getTitle().equals(item.getTitle())){
                        check = true;
                    }
                }
                if (check == false){
                    Log.i("rssItem", "title: " + item.getTitle());
                    database.addItem(item);
                }
                database.close();
                Intent intent = new Intent(getActivity(), NewActivity.class);
                intent.putExtra("link", item.getLink());
                startActivity(intent);
            }
        });



        return view;
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public class GetListItem extends AsyncTask<Void, Void, Void> {

        Context context;
        ProgressDialog pd;

        GetListItem(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            items = rssParser.getRSSFeedItems(url);

            Log.i("rss", items.size() + "");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            AppAdapter adapter = new AppAdapter(items, context);
            listView.setAdapter(adapter);

        }

    }
}

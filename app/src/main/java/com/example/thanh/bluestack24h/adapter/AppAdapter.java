package com.example.thanh.bluestack24h.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanh.bluestack24h.R;
import com.example.thanh.bluestack24h.rss.RSSItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by thanh on 5/9/17.
 */

public class AppAdapter extends BaseAdapter {
    private ArrayList<RSSItem> listItem;
    private LayoutInflater layoutInflater;
    private Context context;

    public AppAdapter() {
    }

    public AppAdapter(ArrayList<RSSItem> listItem, Context acontext) {
        this.listItem = listItem;
        this.context = acontext;
        layoutInflater = LayoutInflater.from(acontext);
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RSSItem item = listItem.get(position);
        convertView = layoutInflater.inflate(R.layout.activity_listview , null);
        TextView tvTenTin = (TextView) convertView.findViewById(R.id.tvTenTin);
        TextView tvMoTa = (TextView) convertView.findViewById(R.id.tvMoTa);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        tvTenTin.setText(item.getTitle());
        tvMoTa.setText(item.getDescription());

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(item.getImg(), img);
//        img.setImageResource(tinTuc.getHinhAnh());
        return convertView;
    }

}

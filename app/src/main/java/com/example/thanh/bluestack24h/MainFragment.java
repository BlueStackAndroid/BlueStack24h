package com.example.thanh.bluestack24h;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thanh.bluestack24h.adapter.ViewPagerAdapter;


/**
 * Created by thanh on 4/21/17.
 */

public class MainFragment extends Fragment{

    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);

        reference(view);
        setUpViewPager(mViewPager);

        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }


    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter=new ViewPagerAdapter(getFragmentManager());

        adapter.addFragment(new ChuyenMucFragment("http://vnexpress.net/rss/tin-moi-nhat.rss"),"Trang chủ");
        adapter.addFragment(new ChuyenMucFragment("http://vnexpress.net/rss/thoi-su.rss"),"Thời sự");
        adapter.addFragment(new ChuyenMucFragment("http://vnexpress.net/rss/the-thao.rss"),"Thể Thao");
        adapter.addFragment(new ChuyenMucFragment("http://vnexpress.net/rss/giai-tri.rss"),"Giải trí");
        adapter.addFragment(new ChuyenMucFragment("http://vnexpress.net/rss/kinh-doanh.rss"),"Kinh doanh");
        adapter.addFragment(new ChuyenMucFragment("http://vnexpress.net/rss/the-gioi.rss"),"Thế giới");
        adapter.addFragment(new ChuyenMucFragment("http://vnexpress.net/rss/gia-dinh.rss"),"Gia đình");
        adapter.addFragment(new ChuyenMucFragment("http://vnexpress.net/rss/giao-duc.rss"),"Giáo dục");
        adapter.addFragment(new ChuyenMucFragment("http://vnexpress.net/rss/du-lich.rss"),"Du lịch");
        adapter.addFragment(new ChuyenMucFragment("http://vnexpress.net/rss/suc-khoe.rss"),"Sức khỏe");
        adapter.addFragment(new ChuyenMucFragment("http://vnexpress.net/rss/cuoi.rss"),"Cười");

        viewPager.setAdapter(adapter);
    }

    private void reference(View view) {
        mTabLayout=(TabLayout) view.findViewById(R.id.tabs);
        mViewPager=(ViewPager) view.findViewById(R.id.viewpager);
    }
}

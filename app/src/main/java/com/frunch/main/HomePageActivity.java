package com.frunch.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.frunch.main.adapters.HomePageAdapter;

/**
 * Created by seerasu1 on 18/10/16.
 */
public class HomePageActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new HomePageAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);
    }
}

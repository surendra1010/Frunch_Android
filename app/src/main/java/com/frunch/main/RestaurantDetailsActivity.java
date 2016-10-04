package com.frunch.main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.frunch.main.adapter.TestTitleFragmentAdapter;
import com.frunch.main.fragments.DetailFrag;
import com.frunch.main.fragments.MainFrag;
import com.frunch.main.fragments.TestFragment;
import com.frunch.main.model.BaseItem;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;

public class RestaurantDetailsActivity extends BaseActivity implements TestFragment.fragListener,
        MainFrag.callListener,
        DetailFrag.delListener,
        DetailFrag.numListener{
        FragmentManager fm;
        Fragment fragment;
        ArrayList<BaseItem> myitemlist = new ArrayList<BaseItem>();

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.restaurant_details_activity);


        // this gets the swiping menu fragment set up and running
        mAdapter = new TestTitleFragmentAdapter(getSupportFragmentManager());
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(TitlePageIndicator.IndicatorStyle.Triangle);
        mIndicator = indicator;


        // this sets up the right bar fragment and connects MainFrag to it.
        fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.right_frag_container);

        if (fragment == null) {

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.right_frag_container, new MainFrag());
        ft.commit();
               /* Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);*/
        }


        //in case a listener is required that activates on every page change. This listener can send the messages to the rightFrag
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
@Override
public void onPageSelected(int position) {
        //Toast.makeText(MainActivity.this, "Changed to page " + position, Toast.LENGTH_SHORT).show();
        }

@Override
public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

@Override
public void onPageScrollStateChanged(int state) {
        }
        });
        }

        /***
         * This click is a receiver for an implementation in the left_PAGER.
         * it receives a nice XML item that works properly.
         * everything is cool.
         */
        MainFrag f;
@Override
public void onItemClick(XmlResourceParser xmlItem) {
        FragmentManager fm = getSupportFragmentManager();
        if(fm.getBackStackEntryCount()==0){
        f = (MainFrag)fm.findFragmentById(R.id.right_frag_container);
        }
        else{
        fm.popBackStack();
        fm.executePendingTransactions();
        }

        if(f!=null && xmlItem!=null)
        f.update(xmlItem);
        }
@Override
public void onButtonClick(BaseItem item) {
        // TODO Auto-generated method stub
        if(item!=null)
        myitemlist.add(item);

        //	Log.d("XXX",item.title);
        FragmentTransaction ft = fm.beginTransaction();
        DetailFrag dilogFrg = new DetailFrag();
        dilogFrg.setMyitemlist(myitemlist);
        ft.replace(R.id.right_frag_container, dilogFrg);
        ft.addToBackStack(null);
        ft.commit();
        }

/**
 * This interface is coming from the delete fragment and is supposed to handle the deletion of the listiew i
 */
@Override
public void onDelClick(int i) {
        //Log.d("XXX", Integer.toString(i)+""+Integer.toString(myitemlist.size()));
        // there was a pop operation over here.. but apparently it wasnt required.. a douple operaition was happening..
        // god only knows where the actual delete operation is happening and how its surviving fragment swatches
        // if ever theres an issue with the bloody delete operation later. track from this poin onwards.
        // myitemlist.remove(i);
        }

@Override
public void onNumChange(int i,int p) {

        BaseItem bit = myitemlist.get(p);
        bit.num=i;
        myitemlist.set(p, bit);
        }

        }

package com.frunch.main.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.frunch.main.Fragments.DeliverRestaurantsFragment;
import com.frunch.main.Fragments.OrderRestaurantsFragment;

/**
 * Created by seerasu1 on 18/10/16.
 */
public class HomePageAdapter extends FragmentPagerAdapter {

    public HomePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            DeliverRestaurantsFragment deliverRestaurantsFragment = new DeliverRestaurantsFragment();
            return deliverRestaurantsFragment;
        }
        else {
            OrderRestaurantsFragment orderRestaurantsFragment = new OrderRestaurantsFragment();
            return orderRestaurantsFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        if(position == 0) {
            return "Deliver";
        }
        else {
            return "Order";
        }
    }
}

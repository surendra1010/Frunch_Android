package com.tentenlabs.frunch.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tentenlabs.frunch.Fragments.MenuCategoryFragment;
import com.tentenlabs.frunch.utils.ItemMenuCategory;

import java.util.List;

/**
 * Created by seerasu1 on 10/10/16.
 */
public class RestaurantMenuAdapter extends FragmentPagerAdapter {

    List<ItemMenuCategory> ItemMenuCategoryList;

    public RestaurantMenuAdapter(FragmentManager fm, List<ItemMenuCategory> ItemMenuCategoryList) {
        super(fm);
        this.ItemMenuCategoryList = ItemMenuCategoryList;
    }

    @Override
    public int getCount() {
        return ItemMenuCategoryList.size();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putParcelable("ItemCategory",ItemMenuCategoryList.get(position));
        MenuCategoryFragment menuCategoryFragment = new MenuCategoryFragment();
        menuCategoryFragment.setArguments(b);
        return menuCategoryFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return ItemMenuCategoryList.get(position).getName();
    }

}

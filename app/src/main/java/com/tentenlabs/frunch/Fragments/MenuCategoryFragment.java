package com.tentenlabs.frunch.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tentenlabs.frunch.R;
import com.tentenlabs.frunch.adapters.MenuListAdapter;
import com.tentenlabs.frunch.utils.ItemMenuCategory;

/**
 * Created by seerasu1 on 11/10/16.
 */
public class MenuCategoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_category_layout,container,false);

        RecyclerView menuListView = (RecyclerView)view.findViewById(R.id.menu_list);
        ItemMenuCategory itemMenuCategory = (ItemMenuCategory) getArguments().getParcelable("ItemCategory");

        if(itemMenuCategory.getMenuItemObjectList().size() > 0) {
            menuListView.setAdapter(new MenuListAdapter(getActivity(),itemMenuCategory.getMenuItemObjectList()));
            menuListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        else {
            TextView no_items_text = (TextView)view.findViewById(R.id.no_items_text);
            no_items_text.setVisibility(View.VISIBLE);
        }

        return view;
    }
}

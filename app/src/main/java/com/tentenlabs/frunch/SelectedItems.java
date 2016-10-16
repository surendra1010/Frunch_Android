package com.tentenlabs.frunch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tentenlabs.frunch.adapters.SelectedItemsAdapter;
import com.tentenlabs.frunch.utils.MenuItemObject;

import java.util.List;

/**
 * Created by seerasu1 on 15/10/16.
 */
public class SelectedItems extends AppCompatActivity{

    RecyclerView itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_items_layout);

        itemsList = (RecyclerView)findViewById(R.id.selected_items_list);
        itemsList.setLayoutManager(new LinearLayoutManager(this));

        List<MenuItemObject> selectedItemsList = getIntent().getParcelableArrayListExtra("selectedList");
        Log.e("selectedList",String.valueOf(selectedItemsList.size()));

        itemsList.setAdapter(new SelectedItemsAdapter(selectedItemsList));

    }
}

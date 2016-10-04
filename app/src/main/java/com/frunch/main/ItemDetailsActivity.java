package com.frunch.main;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.frunch.main.fragments.MainFrag;

public class ItemDetailsActivity extends AppCompatActivity {
    MainFrag f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        Intent intent = getIntent();
        XmlResourceParser xmlItem  = intent.getParcelableExtra("selectedItem");
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
}

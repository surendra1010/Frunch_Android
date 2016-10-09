package com.tentenlabs.frunch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tentenlabs.frunch.utils.RestarantObject;

/**
 * Created by seerasu1 on 09/10/16.
 */
public class RestarantMenu extends AppCompatActivity {

    RestarantObject restarantObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restarantObject = (RestarantObject)getIntent().getSerializableExtra("restarant") ;
        getSupportActionBar().setTitle(restarantObject.getName());
    }


}

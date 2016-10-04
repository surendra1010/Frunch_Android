package com.frunch.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.frunch.main.repo.UserRepository;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.VoidCallback;


public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurants_list);
        //setContentView(R.layout.restaurant_details_activity);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        System.out.println("ksfjlkadsjflkasjflkasjkflslfjaslkfjaslfjlasjfdlsa");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
       /* Intent intent = new Intent(this, RestaurantDetailsActivity.class);
        startActivity(intent);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            final RestAdapter restAdapter = new RestAdapter(getApplicationContext(), "http://frunch.mybluemix.net//api");
            final UserRepository userRepo = restAdapter.createRepository(UserRepository.class);
            // Launching the login activity
            userRepo.logout(new VoidCallback() {
                @Override
                public void onSuccess() {
                    // logged out
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(Throwable t) {
                    // logout failed
                    Log.d("Error while Logout", t.getMessage());
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
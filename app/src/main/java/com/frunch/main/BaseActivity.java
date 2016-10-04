package com.frunch.main;

import java.util.Random;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.frunch.main.repo.UserRepository;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.VoidCallback;
import com.viewpagerindicator.PageIndicator;

import com.frunch.main.adapter.TestFragmentAdapter;


/**
 * This basesampleactivity was a base fragment class for the left side slider module.
 * @author WINDAdmin
 *
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final Random RANDOM = new Random();

    TestFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.random:
                final int page = RANDOM.nextInt(mAdapter.getCount());
                Toast.makeText(this, "Changing to page " + page, Toast.LENGTH_SHORT).show();
                mPager.setCurrentItem(page);
                return true;*/

           /* case R.id.add_page:
                if (mAdapter.getCount() < 10) {
                    mAdapter.setCount(mAdapter.getCount() + 1);
                    mIndicator.notifyDataSetChanged();
                }
                return true;*/
            case R.id.action_logout:
                final RestAdapter restAdapter = new RestAdapter(getApplicationContext(), "http://frunch.mybluemix.net/api");
                final UserRepository userRepo = restAdapter.createRepository(UserRepository.class);
                userRepo.logout(new VoidCallback() {
                    @Override
                    public void onSuccess() {
                        // logged out
                        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
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

           /* case R.id.remove_page:
                if (mAdapter.getCount() > 1) {
                    mAdapter.setCount(mAdapter.getCount() - 1);
                    mIndicator.notifyDataSetChanged();
                }
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.codesgood.espressoworkshop.activities;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.codesgood.espressoworkshop.R;
import com.codesgood.espressoworkshop.fragments.ChatFragment;
import com.codesgood.espressoworkshop.fragments.SplashFragment;

public class MainActivity extends AppCompatActivity {

    private final static int DISMISS_SPLASH = 1;
    private final static int SPLASH_DELAY = 3000;

    private ActionBarDrawerToggle mDrawerToggle;
    private SplashFragment mSplashFragment;

    private Handler mSplashHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == DISMISS_SPLASH) {
                mSplashFragment.dismiss();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        }

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.string.app_name,  /* "open drawer" description */
                R.string.app_name  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();

        if (drawerLayout != null) {
            drawerLayout.addDrawerListener(mDrawerToggle);
        }

        if (savedInstanceState == null) {
            mSplashFragment = SplashFragment.getInstance();
            mSplashFragment.show(getSupportFragmentManager(), SplashFragment.TAG);
            mSplashHandler.sendEmptyMessageDelayed(DISMISS_SPLASH, SPLASH_DELAY);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, ChatFragment.getInstance(), ChatFragment.TAG)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.codesgood.espressoworkshop.activities;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.codesgood.espressoworkshop.R;
import com.codesgood.espressoworkshop.fragments.ChatFragment;
import com.codesgood.espressoworkshop.fragments.SplashFragment;

public class MainActivity extends AppCompatActivity {

    private final static int DISMISS_SPLASH = 1;
    private final static int SPLASH_DELAY = 0;

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

    public interface BotListener {
        void onMessageReceived();
        void onMessageSent();
    }

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

        View aboutOption = findViewById(R.id.about_option);
        if (aboutOption != null) {
            aboutOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(AboutActivity.getInstance(MainActivity.this));
                }
            });
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
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            mDrawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setListener(BotListener listener) {
        ChatFragment fragment = (ChatFragment) getSupportFragmentManager().findFragmentByTag(ChatFragment.TAG);
        if (fragment != null)
            fragment.setListener(listener);
    }
}

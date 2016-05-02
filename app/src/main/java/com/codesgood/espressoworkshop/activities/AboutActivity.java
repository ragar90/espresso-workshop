package com.codesgood.espressoworkshop.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codesgood.espressoworkshop.R;

/**
 * @author Amilcar Serrano
 * @since  4/30/16.
 */
public class AboutActivity extends AppCompatActivity {

    public static Intent getInstance(Context context){
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setLogo(R.drawable.ic_espresso_icon);
        }

        ImageView headerImage = (ImageView) findViewById(R.id.image_header);

        Glide.with(this).load("https://assets.toptal.io/uploads/blog/image/1001/toptal-blog-image-1428390766623.jpg").into(headerImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

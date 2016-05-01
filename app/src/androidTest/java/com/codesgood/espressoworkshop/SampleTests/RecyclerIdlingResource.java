package com.codesgood.espressoworkshop.SampleTests;

import android.support.test.espresso.IdlingResource;

import com.codesgood.espressoworkshop.activities.MainActivity;

/**
 * Created by Amilcar on 5/1/16.
 */
public class RecyclerIdlingResource implements IdlingResource, MainActivity.BotListener {

    private MainActivity mActivity;
    private boolean mMessageReceived = false;
    private ResourceCallback callback;

    public RecyclerIdlingResource(MainActivity activity) {
        mActivity = activity;
        mActivity.setListener(this);
    }

    @Override
    public String getName() {
        return RecyclerIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        if(mMessageReceived){
            callback.onTransitionToIdle();
        }

        return mMessageReceived;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onMessageReceived() {
        mMessageReceived = true;
    }

    @Override
    public void onMessageSent() {

    }
}

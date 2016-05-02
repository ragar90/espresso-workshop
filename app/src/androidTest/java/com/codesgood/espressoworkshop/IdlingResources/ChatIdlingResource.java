package com.codesgood.espressoworkshop.IdlingResources;

import android.support.test.espresso.IdlingResource;

import com.codesgood.espressoworkshop.activities.MainActivity;

/**
 * @author Amilcar Serrano
 * @since 4/30/16
 */
public class ChatIdlingResource implements IdlingResource, MainActivity.BotListener {

    private MainActivity mActivity;
    private boolean mMessageReceived = false;
    private ResourceCallback callback;

    public ChatIdlingResource(MainActivity activity) {
        mActivity = activity;
        mActivity.setListener(this);
    }

    @Override
    public String getName() {
        return ChatIdlingResource.class.getName();
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

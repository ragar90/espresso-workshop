package com.codesgood.espressoworkshop.SampleTests;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.codesgood.espressoworkshop.R;
import com.codesgood.espressoworkshop.activities.MainActivity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * @author Amilcar Serrano
 * @since 4/30/16
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NetworkTests {

    private String mMessage;
    private String mUserName;
    private RecyclerIdlingResource mIdleResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mMessage = "Hello";
        mUserName = "Network_test_user";
    }

    @Test
    public void receiveMessageTest() {
        //Set username
        onView(withId(R.id.username_text)).perform(typeText(mUserName), closeSoftKeyboard());
        onView(withId(R.id.edit_username_button)).perform(click());

        onView(withId(R.id.username_text)).check(matches(withText(mUserName)));
        onView(withId(R.id.username_text)).check(matches(not(isEnabled())));

        //Change message text
        onView(withId(R.id.new_message)).perform(typeText(mMessage), closeSoftKeyboard());
        onView(withId(R.id.new_message)).check(matches(withText(mMessage)));

        //Sending message and checking added response
        onView(withId(R.id.send_button)).perform(click());

        mIdleResource = new RecyclerIdlingResource(mActivityRule.getActivity());
        Espresso.registerIdlingResources(mIdleResource);

        onView(withId(R.id.chat_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.chat_recycler)).check(matches(hasDescendant(withText(mMessage))));
    }


    @After
    public void unregisterIntentServiceIdlingResource() {
        Espresso.unregisterIdlingResources(mIdleResource);
    }

    @AfterClass
    public static void cleanValues() {
        SharedPreferences preferences = InstrumentationRegistry.getTargetContext().getSharedPreferences("CHAT_PREFERENCES", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

}


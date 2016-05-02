package com.codesgood.espressoworkshop.SampleTests;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.codesgood.espressoworkshop.R;
import com.codesgood.espressoworkshop.activities.AboutActivity;
import com.codesgood.espressoworkshop.activities.MainActivity;

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
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
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
public class MainActivityTests {

    private String mMessage;
    private String mUserName;

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(
            MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mMessage = "Sorry I got stuck in a lopp";
        mUserName = "Test_user";
    }

    @Test
    public void changeUserNameTest() {
        onView(withId(R.id.username_text)).perform(typeText(mUserName), closeSoftKeyboard());
        onView(withId(R.id.edit_username_button)).perform(click());

        onView(withId(R.id.username_text)).check(matches(withText(mUserName)));
        onView(withId(R.id.username_text)).check(matches(not(isEnabled())));
    }

    @Test
    public void changeMessageTest() {
        onView(withId(R.id.new_message)).perform(typeText(mMessage), closeSoftKeyboard());
        onView(withId(R.id.new_message)).check(matches(withText(mMessage)));
    }

    @Test
    public void sendMessageTest() {
        onView(withId(R.id.new_message)).perform(typeText(mMessage), closeSoftKeyboard());
        onView(withId(R.id.new_message)).check(matches(withText(mMessage)));

        onView(withId(R.id.send_button)).perform(click());

        onView(withId(R.id.chat_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.chat_recycler)).check(matches(hasDescendant(withText(mMessage))));
    }

    @Test
    public void openDrawerTest() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.about_option)).check(matches(isDisplayed()));
    }

    @Test
    public void openAboutTest() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.about_option)).perform(click());

        intended(hasComponent(AboutActivity.class.getName()));
        onView(withId(R.id.image_header)).check(matches(isEnabled()));
    }

    @Test
    public void closeDrawerTest() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
        onView(withId(R.id.about_option)).check(matches(not(isDisplayed())));
    }

    @AfterClass
    public static void cleanValues() {
        SharedPreferences preferences = InstrumentationRegistry.getTargetContext().getSharedPreferences("CHAT_PREFERENCES", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

}


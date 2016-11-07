package com.max.tang.demokiller.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.max.tang.demokiller.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NavigationActivityTest {

    @Rule
    public ActivityTestRule<NavigationActivity> mActivityTestRule = new ActivityTestRule<>(NavigationActivity.class);

    @Test
    public void navigationActivityTest() {
        ViewInteraction appCompatTextView = onView(
                allOf(withText("Login Demo"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatAutoCompleteTextView = onView(withId(R.id.email));
        appCompatAutoCompleteTextView.perform(scrollTo(), click());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(withId(R.id.email));
        appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("crafttang@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText = onView(withId(R.id.password));
        appCompatEditText.perform(scrollTo(), replaceText("ugghjbvcdfghhb"), closeSoftKeyboard());

        ViewInteraction checkableImageButton = onView(withId(R.id.text_input_password_toggle));
        checkableImageButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton = onView(allOf(withId(R.id.email_sign_in_button),
                withText("Sign in or register"), withParent(allOf(withId(R.id.email_login_form),
                        withParent(withId(R.id.login_form))))));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatTextView2 = onView(allOf(withText("Watch Activity"), isDisplayed()));
        appCompatTextView2.perform(click());

        pressBack();

        ViewInteraction appCompatTextView3 = onView(allOf(withText("Data Binding"), isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.newUserRb), withText("New user"),
                        withParent(withId(R.id.existingOrNewUser)),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.loginOrCreateButton), withText("Create user"), isDisplayed()));
        appCompatButton2.perform(click());

        pressBack();

    }

}

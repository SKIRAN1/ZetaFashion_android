package com.example.zetafashion_android;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static java.util.regex.Pattern.matches;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginPageActivityTest {
    @Rule
    public ActivityTestRule<LoginPageActivity> activityTestRule = new ActivityTestRule<>(LoginPageActivity.class);

    @Test
    public void emailIsEmpty() {
        onView(withId(R.id.tf_Email)).perform(clearText());
        onView(withId(R.id.btn_login)).perform(click());
    }

}
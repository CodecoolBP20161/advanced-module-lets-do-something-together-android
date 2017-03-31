package com.codecool.actimate;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;

import com.codecool.actimate.view.LoginActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;



@RunWith(AndroidJUnit4.class)
public class LoginTests {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);
    private LoginActivity testActivity;

    @Before
    public void setUp() throws Exception {
        testActivity = activityRule.getActivity();
    }

    @Test
    public void emailIsEmpty() {

        onView(withId(R.id.email)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.email))
                .check(matches(simulateEmptyInputError(
                        testActivity.getString(R.string.error_field_required))));

    }

    public static Matcher<View> simulateEmptyInputError(String string) {
        return simulateEmptyInputError(is(string));
    }

    public static Matcher<View> simulateEmptyInputError(final Matcher<String> stringMatcher) {
        checkNotNull(stringMatcher);

        return new BoundedMatcher<View, TextInputLayout>(TextInputLayout.class) {

            String errMsg = null;

            @Override
            public void describeTo(Description description) {
                stringMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(TextInputLayout textInputLayout) {
                CharSequence error = textInputLayout.getError();
                if (error != null) {
                    errMsg = error.toString();
                    return stringMatcher.matches(errMsg);
                }
                return false;
            }
        };
    }
}

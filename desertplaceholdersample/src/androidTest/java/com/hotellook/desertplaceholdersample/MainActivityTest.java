package com.hotellook.desertplaceholdersample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.hotellook.desertplaceholder.DesertPlaceholder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


  @Rule
  public final ActivityTestRule<MainActivity> activityRule  = new ActivityTestRule<MainActivity>(MainActivity.class) {
    @Override
    protected void beforeActivityLaunched() {
      //...
      DesertPlaceholder.animationEnabled = false;
    }
  };

  @Test
  public void placeholder() {
    onView(withId(R.id.placeholder))
        .perform(click()) // will freeze here if animation enabled
        .check(matches(isDisplayed()));
  }

}
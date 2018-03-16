package com.example.android.bakingtime;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.*;




/**
 * Created by sakshimajmudar on 15/03/18.
 */
@RunWith(AndroidJUnit4.class)
public class ShowIngredientTest {
    @Rule public ActivityTestRule<MasterListMain> mainActivityActivityTestRule = new ActivityTestRule<>(MasterListMain.class);

    @Test
    public void clickIngredient_displaysIngredientsForSelectedRecipe(){
        onView(withId(R.id.tv_ingredient)).perform(click());

        onView(withId(R.id.tv_title_ingredient)).check(matches(isDisplayed()));


    }
}

package pos.com.pos;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Switch;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pos.com.pos.main.view.MainActivity;
import pos.com.pos.shoppingCart.ShoppingCart;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.isA;

/**
 * Created by HJ Chin on 2/1/2018.
 */

@RunWith(AndroidJUnit4.class)
public class InputBoxFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class){
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            ShoppingCart.getInstance().emptyCart();
        }

        @Override
        protected void afterActivityLaunched() {
            super.afterActivityLaunched();
            //open All Items fragment
            onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));

            //click on first item
            onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        }
    };

    @Test
    public void testFillQuantityBox(){
        onView(withId(R.id.quantity)).perform(replaceText("0"),closeSoftKeyboard());
        onView(withId(R.id.quantity)).check(matches(withText("0")));

        onView(withId(R.id.quantity)).perform(replaceText("10"),closeSoftKeyboard());
        onView(withId(R.id.quantity)).check(matches(withText("10")));
    }

    @Test
    public void testAdjustQuantityButton(){
        /*
            increase quantity value to max 1000
         */
        onView(withId(R.id.quantity)).perform(replaceText("998"), pressImeActionButton());
        onView(withId(R.id.quantity)).check(matches(withText("998")));

        onView(withId(R.id.btnIncrease)).perform(click());
        onView(withId(R.id.quantity)).check(matches(withText("999")));

        for(int i=0;i<4;i++){
            onView(withId(R.id.btnIncrease)).perform(click());
        }
        onView(withId(R.id.quantity)).check(matches(withText("1000")));

        /*
            decrease quantity value to negative
         */
        onView(withId(R.id.quantity)).perform(replaceText("0"), pressImeActionButton());
        onView(withId(R.id.quantity)).check(matches(withText("0")));
        onView(withId(R.id.btnDecrease)).perform(click());
        onView(withId(R.id.quantity)).check(matches(withText("0")));
    }

    @Test
    public void testChangeDiscountOption(){
        onView(withId(R.id.discountA)).perform(new CheckViewAction());
        onView(withId(R.id.discountA)).check(matches(isChecked()));
        onView(withId(R.id.discountB)).check(matches(isNotChecked()));
        onView(withId(R.id.discountC)).check(matches(isNotChecked()));
        onView(withId(R.id.discountD)).check(matches(isNotChecked()));
        onView(withId(R.id.discountE)).check(matches(isNotChecked()));

        onView(withId(R.id.discountB)).perform(new CheckViewAction());
        onView(withId(R.id.discountA)).check(matches(isNotChecked()));
        onView(withId(R.id.discountB)).check(matches(isChecked()));
        onView(withId(R.id.discountC)).check(matches(isNotChecked()));
        onView(withId(R.id.discountD)).check(matches(isNotChecked()));
        onView(withId(R.id.discountE)).check(matches(isNotChecked()));

        onView(withId(R.id.discountC)).perform(new CheckViewAction());
        onView(withId(R.id.discountA)).check(matches(isNotChecked()));
        onView(withId(R.id.discountB)).check(matches(isNotChecked()));
        onView(withId(R.id.discountC)).check(matches(isChecked()));
        onView(withId(R.id.discountD)).check(matches(isNotChecked()));
        onView(withId(R.id.discountE)).check(matches(isNotChecked()));

        onView(withId(R.id.discountD)).perform(new CheckViewAction());
        onView(withId(R.id.discountA)).check(matches(isNotChecked()));
        onView(withId(R.id.discountB)).check(matches(isNotChecked()));
        onView(withId(R.id.discountC)).check(matches(isNotChecked()));
        onView(withId(R.id.discountD)).check(matches(isChecked()));
        onView(withId(R.id.discountE)).check(matches(isNotChecked()));

        onView(withId(R.id.discountE)).perform(new CheckViewAction());
        onView(withId(R.id.discountA)).check(matches(isNotChecked()));
        onView(withId(R.id.discountB)).check(matches(isNotChecked()));
        onView(withId(R.id.discountC)).check(matches(isNotChecked()));
        onView(withId(R.id.discountD)).check(matches(isNotChecked()));
        onView(withId(R.id.discountE)).check(matches(isChecked()));
    }

    public static class CheckViewAction implements ViewAction{

        @Override
        public Matcher<View> getConstraints() {

            return new Matcher<View>() {
                @Override
                public boolean matches(Object item) {
                    return isA(Switch.class).matches(item);
                }

                @Override
                public void describeMismatch(Object item, Description mismatchDescription) {

                }

                @Override
                public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

                }

                @Override
                public void describeTo(Description description) {

                }
            };
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public void perform(UiController uiController, View view) {
            Switch scView = (Switch) view;
            scView.setChecked(true);
        }
    }
}

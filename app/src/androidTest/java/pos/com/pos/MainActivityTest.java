package pos.com.pos;

import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pos.com.pos.discount.model.DiscountItem;
import pos.com.pos.library.model.Item;
import pos.com.pos.main.view.MainActivity;
import pos.com.pos.shoppingCart.ShoppingCart;
import pos.com.pos.shoppingCart.model.ShoppingCartItem;
import pos.com.pos.util.Util;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;

/**
 * Created by HJ Chin on 28/12/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class){
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            ShoppingCart.getInstance().emptyCart();
        }
    };

    @Test
    public void testShowDefaultFragments(){
        onView(withText(activityTestRule.getActivity().getString(R.string.library))).check(matches(isDisplayed()));
        onView(withText(Item.discountItem.name)).check(matches(isDisplayed()));
        onView(withText(Item.skuItem.name)).check(matches(isDisplayed()));

        onView(withText(activityTestRule.getActivity().getString(R.string.shopping_cart))).check(matches(isDisplayed()));
    }

    @Test
    public void testShowDiscountListFragment(){
        onView(withText(Item.discountItem.name)).check(matches(isDisplayed()));
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withText(activityTestRule.getActivity().getString(R.string.all_discounts))).check(matches(isDisplayed()));
        onView(withText(DiscountItem.discountA.name)).check(matches(isDisplayed()));

        activityTestRule.getActivity().backStack();
        onView(withText(activityTestRule.getActivity().getString(R.string.library))).check(matches(isDisplayed()));
        onView(withText(Item.discountItem.name)).check(matches(isDisplayed()));
    }

    @Test
    public void testShowItemListFragment(){

        IdlingRegistry.getInstance().register(activityTestRule.getActivity().getIdlingCounter());

        onView(withText(Item.skuItem.name)).check(matches(isDisplayed()));
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(withText(activityTestRule.getActivity().getString(R.string.all_items))).check(matches(isDisplayed()));

        onView(withText("accusamus beatae ad facilis cum similique qui sunt")).check(matches(isDisplayed()));

        activityTestRule.getActivity().backStack();
        onView(withText(activityTestRule.getActivity().getString(R.string.library))).check(matches(isDisplayed()));
        onView(withText(Item.discountItem.name)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddItemIntoShoppingCart(){

        IdlingRegistry.getInstance().register(activityTestRule.getActivity().getIdlingCounter());

        addFirstItem();

        ShoppingCart shoppingCart = ShoppingCart.getInstance();
        ShoppingCartItem[] items = shoppingCart.getItems();

        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText(items[0].getItem().title)))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText("x"+items[0].getQuantity())))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText(Util.formatMoney(items[0].totalBeforeDiscount()))))));

        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(1,hasDescendant(withText(Util.formatMoney(shoppingCart.getSubTotalString()))))));
        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(2,hasDescendant(withText("("+Util.formatMoney(shoppingCart.getDiscountString()+")"))))));

        onView(withId(R.id.charge_button)).check(matches(withText(Util.formatChargeText(shoppingCart.getChargeString()))));

        /*
            add same item with different quantity
         */
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.quantity)).perform(replaceText("10"),pressImeActionButton());
        onView(withId(R.id.btnSave)).perform(click());

        shoppingCart = ShoppingCart.getInstance();
        items = shoppingCart.getItems();

        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText(items[0].getItem().title)))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText("x"+items[0].getQuantity())))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText(Util.formatMoney(items[0].totalBeforeDiscount()))))));

        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(1,hasDescendant(withText(Util.formatMoney(shoppingCart.getSubTotalString()))))));
        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(2,hasDescendant(withText("("+Util.formatMoney(shoppingCart.getDiscountString()+")"))))));

        onView(withId(R.id.charge_button)).check(matches(withText(Util.formatChargeText(shoppingCart.getChargeString()))));

        /*
            add second item with quantity 10, discountB
         */
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(withId(R.id.quantity)).perform(replaceText("10"), pressImeActionButton());
        onView(withId(R.id.discountB)).perform(new InputBoxFragmentTest.CheckViewAction());
        onView(withId(R.id.btnSave)).perform(click());

        items = shoppingCart.getItems();

        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText(items[0].getItem().title)))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText("x"+items[0].getQuantity())))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText(Util.formatMoney(items[0].totalBeforeDiscount()))))));

        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(1,hasDescendant(withText(items[1].getItem().title)))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(1,hasDescendant(withText("x"+items[1].getQuantity())))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(1,hasDescendant(withText(Util.formatMoney(items[1].totalBeforeDiscount()))))));

        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(2,hasDescendant(withText(Util.formatMoney(shoppingCart.getSubTotalString()))))));
        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(3,hasDescendant(withText("("+Util.formatMoney(shoppingCart.getDiscountString()+")"))))));

        onView(withId(R.id.charge_button)).check(matches(withText(Util.formatChargeText(shoppingCart.getChargeString()))));

        /*
            Open inputBox and press cancel
         */
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));
        onView(withId(R.id.btnCancel)).perform(click());

        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText(items[0].getItem().title)))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText("x"+items[0].getQuantity())))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText(Util.formatMoney(items[0].totalBeforeDiscount()))))));

        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(1,hasDescendant(withText(items[1].getItem().title)))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(1,hasDescendant(withText("x"+items[1].getQuantity())))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(1,hasDescendant(withText(Util.formatMoney(items[1].totalBeforeDiscount()))))));

        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(2,hasDescendant(withText(Util.formatMoney(shoppingCart.getSubTotalString()))))));
        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(3,hasDescendant(withText("("+Util.formatMoney(shoppingCart.getDiscountString()+")"))))));

        onView(withId(R.id.charge_button)).check(matches(withText(Util.formatChargeText(shoppingCart.getChargeString()))));
    }

    @Test
    public void testEditAndSaveItem(){
        addFirstItem();

        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.btnIncrease)).perform(click());
        onView(withId(R.id.btnIncrease)).perform(click());
        onView(withId(R.id.btnSave)).perform(click());

        ShoppingCart shoppingCart = ShoppingCart.getInstance();
        ShoppingCartItem[] items = shoppingCart.getItems();

        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText(items[0].getItem().title)))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText("x"+items[0].getQuantity())))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText(Util.formatMoney(items[0].totalBeforeDiscount()))))));

        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(1,hasDescendant(withText(Util.formatMoney(shoppingCart.getSubTotalString()))))));
        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(2,hasDescendant(withText("("+Util.formatMoney(shoppingCart.getDiscountString()+")"))))));

        onView(withId(R.id.charge_button)).check(matches(withText(Util.formatChargeText(shoppingCart.getChargeString()))));

        /*
            Edit and cancel item
         */
        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.btnIncrease)).perform(click());
        onView(withId(R.id.btnIncrease)).perform(click());
        onView(withId(R.id.discountB)).perform(new InputBoxFragmentTest.CheckViewAction());
        onView(withId(R.id.btnCancel)).perform(click());

        items = shoppingCart.getItems();

        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText(items[0].getItem().title)))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText("x"+items[0].getQuantity())))));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(0,hasDescendant(withText(Util.formatMoney(items[0].totalBeforeDiscount()))))));

        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(1,hasDescendant(withText(Util.formatMoney(shoppingCart.getSubTotalString()))))));
        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withId(R.id.shopping_cart_list)).check(matches(atPosition(2,hasDescendant(withText("("+Util.formatMoney(shoppingCart.getDiscountString()+")"))))));

        onView(withId(R.id.charge_button)).check(matches(withText(Util.formatChargeText(shoppingCart.getChargeString()))));

        /*
            Remove item
         */
        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.shopping_cart_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.quantity)).perform(replaceText("0"),pressImeActionButton());
        onView(withId(R.id.btnSave)).perform(click());

        onView(withId(R.id.charge_button)).check(matches(withText(Util.formatChargeText(shoppingCart.getChargeString()))));

    }

    @Test
    public void testClearSales(){
        addFirstItem();
        onView(withId(R.id.clear_button)).perform(click());
        ShoppingCart shoppingCart = ShoppingCart.getInstance();
        onView(withId(R.id.charge_button)).check(matches(withText(Util.formatChargeText(shoppingCart.getChargeString()))));
    }

    private void addFirstItem() {
        onView(withText(Item.skuItem.name)).check(matches(isDisplayed()));
        //open ItemListFragment
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        //open inputBoxDialog
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        //save item
        onView(withId(R.id.btnSave)).perform(click());
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}

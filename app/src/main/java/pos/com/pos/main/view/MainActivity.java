package pos.com.pos.main.view;

import android.databinding.DataBindingUtil;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pos.com.pos.R;
import pos.com.pos.inputBox.view.InputBoxFragment;
import pos.com.pos.item.model.Item;
import pos.com.pos.library.model.SKUItem;
import pos.com.pos.item.view.ItemListFragment;
import pos.com.pos.databinding.ActivityMainBinding;
import pos.com.pos.library.model.DiscountItem;
import pos.com.pos.discount.view.DiscountListFragment;
import pos.com.pos.library.view.LibraryFragment;
import pos.com.pos.main.Presenter.MainPresenter;
import pos.com.pos.shoppingCart.ShoppingCart;
import pos.com.pos.shoppingCart.model.ShoppingCartItem;
import pos.com.pos.shoppingCart.view.ShoppingCartFragment;
import pos.com.pos.util.AppExecutors;
import pos.com.pos.util.Util;

import static pos.com.pos.inputBox.view.InputBoxFragment.SHOPPING_CART_ITEM;

public class MainActivity extends AppCompatActivity implements
        MainView,
        LibraryFragment.Callback,
        DiscountListFragment.Callback,
        ItemListFragment.Callback,
        ShoppingCartFragment.Callback,
        InputBoxFragment.Callback{

    private ActivityMainBinding binding;
    private MainPresenter presenter;
    private static CountingIdlingResource countingIdlingResource;
    private ShoppingCartFragment shoppingCartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countingIdlingResource = new CountingIdlingResource("counter");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenter = new MainPresenter(this);
    }

    @Override
    public void showLibraryFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.leftFrame,LibraryFragment.newInstance(),"library");
        transaction.commit();
    }

    @Override
    public void showDiscountFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.leftFrame, DiscountListFragment.newInstance(),"discount");
        transaction.addToBackStack("discount");
        transaction.commit();
    }

    @Override
    public void showItemListFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.leftFrame, ItemListFragment.newInstance(countingIdlingResource),"itemList");
        transaction.addToBackStack("itemList");
        transaction.commit();
    }

    @Override
    public void showShoppingCartFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        shoppingCartFragment = ShoppingCartFragment.newInstance();
        transaction.replace(R.id.rightFrame, shoppingCartFragment,"shoppingCart");
        transaction.commit();
    }

    @Override
    public void refreshShoppingCart() {

    }

    @Override
    public void onItemClick(pos.com.pos.library.model.Item item) {
        if(item instanceof DiscountItem){
            showDiscountFragment();
        }

        if(item instanceof SKUItem){
            showItemListFragment();
        }
    }

    @Override
    public void onItemClick(DiscountItem item) {

    }

    @Override
    public void onSKUItemClick(Item item) {
        InputBoxFragment inputBox = new InputBoxFragment();
        Bundle data = new Bundle();
        data.putParcelable(SHOPPING_CART_ITEM, new ShoppingCartItem(item, pos.com.pos.discount.model.DiscountItem.discountA,1));
        inputBox.setArguments(data);
        inputBox.show(getFragmentManager(),"inputBox");
    }

    public void backStack(){
        AppExecutors.getInstance().mainThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        });
    }

    public CountingIdlingResource getIdlingCounter(){
        return countingIdlingResource;
    }

    @Override
    public void onShoppingCartItemClick(ShoppingCartItem item) {
        InputBoxFragment inputBox = new InputBoxFragment();
        Bundle data = new Bundle();
        data.putParcelable(SHOPPING_CART_ITEM, item);
        inputBox.setArguments(data);
        inputBox.show(getFragmentManager(),"inputBox");
    }

    @Override
    public void onInputBoxSave() {
        if(shoppingCartFragment != null){
            shoppingCartFragment.refresh();
        }
    }
}

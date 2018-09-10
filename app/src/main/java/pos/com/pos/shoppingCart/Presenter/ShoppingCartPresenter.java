package pos.com.pos.shoppingCart.Presenter;

import java.util.ArrayList;

import pos.com.pos.shoppingCart.ShoppingCart;
import pos.com.pos.shoppingCart.model.DiscountItem;
import pos.com.pos.shoppingCart.model.Item;
import pos.com.pos.shoppingCart.model.ShoppingCartItem;
import pos.com.pos.shoppingCart.model.SubTotalItem;
import pos.com.pos.shoppingCart.view.ShoppingCartFragmentView;
import pos.com.pos.util.Util;

/**
 * Created by HJ Chin on 2/1/2018.
 */

public class ShoppingCartPresenter {

    private ShoppingCart shoppingCart;
    private ShoppingCartFragmentView view;

    public ShoppingCartPresenter(ShoppingCart shoppingCart, ShoppingCartFragmentView v){
        this.shoppingCart = shoppingCart;
        this.view = v;
        refresh();
    }

    public void refresh(){

        ArrayList<Item> item = new ArrayList<>();

        ShoppingCartItem[] cartItem = shoppingCart.getItems();

        if(cartItem.length > 0){
            for(ShoppingCartItem i : cartItem){
                item.add(i);
            }

            item.add(new SubTotalItem(shoppingCart.getSubTotalString()));
            item.add(new DiscountItem(shoppingCart.getDiscountString()));
        }

        view.refreshList(item);
        view.updateChargeButton(Util.formatChargeText(shoppingCart.getChargeString()));
    }

    public void clearSales() {
        shoppingCart.emptyCart();
        refresh();
    }
}

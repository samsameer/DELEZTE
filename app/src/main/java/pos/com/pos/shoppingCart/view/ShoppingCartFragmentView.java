package pos.com.pos.shoppingCart.view;

import java.util.List;

import pos.com.pos.shoppingCart.model.Item;
/**
 * Created by HJ Chin on 2/1/2018.
 */

public interface ShoppingCartFragmentView {
    void refreshList(List<Item> items);
    void updateChargeButton(String s);
}

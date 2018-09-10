package pos.com.pos.item.view;

import pos.com.pos.item.model.Item;

/**
 * Created by HJ Chin on 29/12/2017.
 */

public interface ItemListView {

    void showItems(Item[] items);
    void showError(Throwable throwable);
}

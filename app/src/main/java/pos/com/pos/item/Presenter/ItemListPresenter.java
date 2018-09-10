package pos.com.pos.item.Presenter;

import android.support.test.espresso.idling.CountingIdlingResource;

import pos.com.pos.item.model.ItemRepo;
import pos.com.pos.item.model.Item;
import pos.com.pos.item.view.ItemListView;

/**
 * Created by HJ Chin on 29/12/2017.
 */

public class ItemListPresenter {

    private final ItemRepo repo;
    private final ItemListView view;
    private final CountingIdlingResource countingIdlingResource;

    public ItemListPresenter(CountingIdlingResource countingIdlingResource, ItemRepo repo, ItemListView view){
        this.countingIdlingResource = countingIdlingResource;
        this.repo = repo;
        this.view = view;
        loadItems();
    }

    public void loadItems(){
        countingIdlingResource.increment();

        repo.getItems(new ItemRepo.GetItemListCallback() {
            @Override
            public void onResponse(Item[] sku) {
                view.showItems(sku);
                countingIdlingResource.decrement();
            }

            @Override
            public void onErrorResponse(Throwable throwable) {
                view.showError(throwable);
                countingIdlingResource.decrement();
            }
        });
    }

    public void destroy() {
        repo.disposeSubscription();
    }
}

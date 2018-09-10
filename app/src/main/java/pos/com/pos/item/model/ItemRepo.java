package pos.com.pos.item.model;

import java.util.Random;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import pos.com.pos.data.HttpClientInterface;
import pos.com.pos.util.AppExecutors;

/**
 * Created by HJ Chin on 29/12/2017.
 */

public class ItemRepo {

    public interface GetItemListCallback{
            void onResponse(Item[] sku);
            void onErrorResponse(Throwable throwable);
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private HttpClientInterface httpClient;
    private ItemDao itemDao;
    private AppExecutors appExecutor;

    public ItemRepo(ItemDao itemDao, HttpClientInterface httpClient, AppExecutors executor){
        this.itemDao = itemDao;
        this.httpClient = httpClient;
        this.appExecutor = executor;
    }

    public void getItems(final GetItemListCallback callback){

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                //For simplicity, mechanism to get latest copy from server is not done yet.
                final Item[] items = itemDao.loadAll();

                if(items == null || items.length == 0){
                    compositeDisposable.add(httpClient.getPOSApi().getItems()
                            .subscribe(new Consumer<Item[]>() {
                                @Override
                                public void accept(Item[] items) throws Exception {

                                    for(Item item: items){
                                        item.price = item.id * new Random().nextInt(99 - 10 + 1) + 10;
                                    }

                                    itemDao.save(items);
                                    final Item[] i = itemDao.loadAll();

                                    appExecutor.mainThreadExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.onResponse(i);
                                        }
                                    });
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(final Throwable throwable) throws Exception {

                                    appExecutor.mainThreadExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.onErrorResponse(throwable);
                                        }
                                    });
                                }
                            }));
                }else{

                    appExecutor.mainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponse(items);
                        }
                    });
                }
            }
        };

        appExecutor.diskIOExecutor().execute(runnable);
    }

    public void disposeSubscription(){
        if(compositeDisposable!=null){
            compositeDisposable.dispose();
        }
    }

}

package pos.com.pos.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by HJ Chin on 30/12/2017.
 */

public class AppExecutors{

    private static AppExecutors appExecutors;

    private final Executor diskIOExecutor;
    private final Executor mainThreadExecutor;

    private AppExecutors(Executor mainThreadExecutor, Executor diskIOExecutor){
        this.mainThreadExecutor = mainThreadExecutor;
        this.diskIOExecutor = diskIOExecutor;
    }

    public static AppExecutors getInstance(){
        if(appExecutors == null){
            appExecutors = new AppExecutors(new MainThreadExecutor(),new DiskIOExecutor());
        }

        return appExecutors;
    }


    public Executor diskIOExecutor(){
        return diskIOExecutor;
    }

    public Executor mainThreadExecutor(){
        return mainThreadExecutor;
    }

    private static class DiskIOExecutor implements Executor{

        private final Executor executor;

        DiskIOExecutor(){
            executor = Executors.newSingleThreadExecutor();
        }

        @Override
        public void execute(@NonNull Runnable runnable) {
            executor.execute(runnable);
        }
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}

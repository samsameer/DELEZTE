package pos.com.pos.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import pos.com.pos.item.model.Item;
import pos.com.pos.item.model.ItemDao;

/**
 * Created by HJ Chin on 30/12/2017.
 */

@Database(entities = {Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"POS.db").build();
        }
        return instance;
    }
}

package pos.com.pos.item.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by HJ Chin on 30/12/2017.
 */

@Dao
public interface ItemDao {

    @Insert(onConflict = REPLACE)
    void save(Item[] item);

    @Query("SELECT * FROM Item")
    Item[] loadAll();
}

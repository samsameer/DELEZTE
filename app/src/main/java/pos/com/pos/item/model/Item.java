package pos.com.pos.item.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HJ Chin on 29/12/2017.
 */

@Entity
public class Item implements Parcelable{
    public int albumId;

    @PrimaryKey
    public int id;
    public String title;
    public String url;
    public String thumbnailUrl;
    public int price;

    public Item(){
    }

    public Item(int id, String title, String url, String thumbnailUrl, int price){
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.price = price;
    }

    protected Item(Parcel in) {
        albumId = in.readInt();
        id = in.readInt();
        title = in.readString();
        url = in.readString();
        thumbnailUrl = in.readString();
        price = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(albumId);
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(thumbnailUrl);
        parcel.writeInt(price);
    }
}

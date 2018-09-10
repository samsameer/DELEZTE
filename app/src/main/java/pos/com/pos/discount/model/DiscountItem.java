package pos.com.pos.discount.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by HJ Chin on 30/12/2017.
 */

public class DiscountItem implements Comparable<DiscountItem>, Parcelable{
    public String id;
    public String name;
    public double discount;

    public DiscountItem(String id, String name, double discount){
        this.id = id;
        this.name = name;
        this.discount = discount;
    }

    protected DiscountItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        discount = in.readDouble();
    }

    public static final Creator<DiscountItem> CREATOR = new Creator<DiscountItem>() {
        @Override
        public DiscountItem createFromParcel(Parcel in) {
            return new DiscountItem(in);
        }

        @Override
        public DiscountItem[] newArray(int size) {
            return new DiscountItem[size];
        }
    };

    @Override
    public int compareTo(@NonNull DiscountItem o) {
        return id.compareTo(o.id);
    }

    public static final ArrayList<DiscountItem> discountItemArrayList = new ArrayList<>();

    public static final DiscountItem discountA = new DiscountItem("discountA","Discount A",0);
    public static final DiscountItem discountB = new DiscountItem("discountB","Discount B",10);
    public static final DiscountItem discountC = new DiscountItem("discountC","Discount C",35.5);
    public static final DiscountItem discountD = new DiscountItem("discountD","Discount D",50);
    public static final DiscountItem discountE = new DiscountItem("discountE","Discount E",100);

    static{
        discountItemArrayList.add(discountA);
        discountItemArrayList.add(discountB);
        discountItemArrayList.add(discountC);
        discountItemArrayList.add(discountD);
        discountItemArrayList.add(discountE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeDouble(discount);
    }
}

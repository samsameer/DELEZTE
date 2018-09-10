package pos.com.pos.shoppingCart.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

import pos.com.pos.discount.model.DiscountItem;
import pos.com.pos.item.model.Item;
import pos.com.pos.util.Util;

/**
 * Created by HJ Chin on 31/12/2017.
 */

public class ShoppingCartItem implements Parcelable, pos.com.pos.shoppingCart.model.Item{

    private final String id;
    private final Item item;
    private final DiscountItem discountItem;
    private final int quantity;
    private double totalDiscount;
    private Double totalAfterDiscount;
    private Double totalBeforeDiscount;

    private static int runningNumber = 1;
    private static int getNextId(){
        return runningNumber++;
    }

    public ShoppingCartItem(Item skuItem, DiscountItem discountItem, int quantity){
        this.item = skuItem;
        this.discountItem = discountItem;
        this.quantity = quantity;
        id = String.valueOf(getNextId());
        calTotal();
    }

    protected ShoppingCartItem(Parcel in) {
        id = in.readString();
        item = in.readParcelable(Item.class.getClassLoader());
        discountItem = in.readParcelable(DiscountItem.class.getClassLoader());
        quantity = in.readInt();
        totalDiscount = in.readDouble();
        if (in.readByte() == 0) {
            totalAfterDiscount = null;
        } else {
            totalAfterDiscount = in.readDouble();
        }
        if (in.readByte() == 0) {
            totalBeforeDiscount = null;
        } else {
            totalBeforeDiscount = in.readDouble();
        }
    }

    public static final Creator<ShoppingCartItem> CREATOR = new Creator<ShoppingCartItem>() {
        @Override
        public ShoppingCartItem createFromParcel(Parcel in) {
            return new ShoppingCartItem(in);
        }

        @Override
        public ShoppingCartItem[] newArray(int size) {
            return new ShoppingCartItem[size];
        }
    };

    private void calTotal(){
        //discount is in form of XX%

        if(Double.compare(discountItem.discount,0.0) == 0){
            totalBeforeDiscount = Math.round(item.price * quantity*100)/100.0;
            totalAfterDiscount = totalBeforeDiscount;
            totalDiscount = 0.0;
        }else if(Double.compare(discountItem.discount,100.0) == 0){
            totalAfterDiscount = 0.0;
            totalDiscount = Math.round(item.price * discountItem.discount * quantity)/100.0;
            totalBeforeDiscount = totalDiscount;
        }else{
            totalBeforeDiscount = Math.round(item.price * quantity * 100.0)/100.0;
            totalAfterDiscount = Math.round(item.price * (100-discountItem.discount) * quantity)/100.0;
            totalDiscount = totalBeforeDiscount - totalAfterDiscount;
        }
    }

    public double totalDiscount(){
        return totalDiscount;
    }

    public double totalAfterDiscount(){
         if(totalAfterDiscount == null){
             calTotal();
         }

         return totalAfterDiscount;
    }


    public double totalBeforeDiscount(){
        return totalBeforeDiscount;
    }

    public String totalAfterDiscountString(){
        return format(totalAfterDiscount);
    }

    public String totalDiscountString(){
        return format(totalDiscount);
    }

    public String totalBeforeDiscountString(){
        return format(totalBeforeDiscount);
    }

    private String format(double value){
        return Util.formatDisplay(value);
    }

    public String getItemName(){
        return item.title;
    }

    public String getQuantityString(){
        return String.valueOf(quantity);
    }

    public int getQuantity(){
        return quantity;
    }

    public DiscountItem getDiscount(){
        return discountItem;
    }

    public Item getItem(){
        return item;
    }

    public String getId(){
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(id);
        parcel.writeParcelable(item, i);
        parcel.writeParcelable(discountItem, i);
        parcel.writeInt(quantity);
        parcel.writeDouble(totalDiscount);
        if (totalAfterDiscount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(totalAfterDiscount);
        }
        if (totalBeforeDiscount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(totalBeforeDiscount);
        }
    }

}

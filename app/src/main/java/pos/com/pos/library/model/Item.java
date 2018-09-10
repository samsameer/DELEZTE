package pos.com.pos.library.model;

import java.util.ArrayList;

/**
 * Created by HJ Chin on 28/12/2017.
 */

public abstract class Item {
    public String name;
    public String id;

    public static final DiscountItem discountItem = new DiscountItem("discount", "All Discounts");
    public static final SKUItem skuItem = new SKUItem("item", "All Items");

    public static ArrayList<Item> libraryItemArrayList;

    static{
        libraryItemArrayList = new ArrayList<>();
        libraryItemArrayList.add(discountItem);
        libraryItemArrayList.add(skuItem);
    }

}

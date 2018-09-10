package pos.com.pos.shoppingCart.model;

/**
 * Created by HJ Chin on 2/1/2018.
 */

public class DiscountItem implements Item {

    public final String text = "Discount";
    public String amount;

    public DiscountItem(String amount){
        this.amount = amount;
    }
}

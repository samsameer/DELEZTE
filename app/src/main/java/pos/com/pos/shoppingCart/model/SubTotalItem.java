package pos.com.pos.shoppingCart.model;

/**
 * Created by HJ Chin on 2/1/2018.
 */

public class SubTotalItem implements Item {

    public final String text = "Subtotal";
    public String amount;

    public SubTotalItem(String amount){
        this.amount = amount;
    }

}

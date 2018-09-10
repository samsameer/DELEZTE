package pos.com.pos.ShoppingCart;

import org.junit.Test;

import pos.com.pos.discount.model.DiscountItem;
import pos.com.pos.item.model.Item;
import pos.com.pos.shoppingCart.model.ShoppingCartItem;

import static org.junit.Assert.assertEquals;

/**
 * Created by HJ Chin on 31/12/2017.
 */

public class ShoppingCartItemTest {

    private Item skuItem;

    public ShoppingCartItemTest(){
        skuItem = new Item();
        skuItem.title = "product A";
        skuItem.price = 100;
    }

    @Test
    public void testNoDiscount(){

        DiscountItem discountItem0 = DiscountItem.discountA;

        ShoppingCartItem cartItem1 = new ShoppingCartItem(
                skuItem,
                discountItem0,
                1);

        assertEquals("100.00",cartItem1.totalAfterDiscountString());
        assertEquals("0.00",cartItem1.totalDiscountString());

        ShoppingCartItem cartItem2 = new ShoppingCartItem(
                skuItem,
                discountItem0,
                100);

        assertEquals("10000.00",cartItem2.totalBeforeDiscountString());
        assertEquals("10000.00",cartItem2.totalAfterDiscountString());
        assertEquals("0.00",cartItem2.totalDiscountString());
    }

    @Test
    public void test10Discount(){
        DiscountItem discountItem10 = DiscountItem.discountB;

        ShoppingCartItem cartItem3 = new ShoppingCartItem(
                skuItem,
                discountItem10,
                1);

        assertEquals("100.00",cartItem3.totalBeforeDiscountString());
        assertEquals("90.00",cartItem3.totalAfterDiscountString());
        assertEquals("10.00",cartItem3.totalDiscountString());

        ShoppingCartItem cartItem4 = new ShoppingCartItem(
                skuItem,
                discountItem10,
                20);

        assertEquals("2000.00",cartItem4.totalBeforeDiscountString());
        assertEquals("1800.00",cartItem4.totalAfterDiscountString());
        assertEquals("200.00",cartItem4.totalDiscountString());
    }

    @Test
    public void test355Discount(){
        DiscountItem discountItem355 = DiscountItem.discountC;

        ShoppingCartItem cartItem1 = new ShoppingCartItem(
                skuItem,
                discountItem355,
                1);

        assertEquals("100.00",cartItem1.totalBeforeDiscountString());
        assertEquals("64.50",cartItem1.totalAfterDiscountString());
        assertEquals("35.50",cartItem1.totalDiscountString());

        ShoppingCartItem cartItem2 = new ShoppingCartItem(
                skuItem,
                discountItem355,
                12);

        assertEquals("1200.00",cartItem2.totalBeforeDiscountString());
        assertEquals("774.00",cartItem2.totalAfterDiscountString());
        assertEquals("426.00",cartItem2.totalDiscountString());
    }

    @Test
    public void test100Discount(){
        DiscountItem discountItem100 = DiscountItem.discountE;

        ShoppingCartItem cartItem1 = new ShoppingCartItem(
                skuItem,
                discountItem100,
                1);

        assertEquals("100.00",cartItem1.totalBeforeDiscountString());
        assertEquals("0.00",cartItem1.totalAfterDiscountString());
        assertEquals("100.00",cartItem1.totalDiscountString());

        ShoppingCartItem cartItem2 = new ShoppingCartItem(
                skuItem,
                discountItem100,
                12);

        assertEquals("100.00",cartItem1.totalBeforeDiscountString());
        assertEquals("0.00",cartItem1.totalAfterDiscountString());
        assertEquals("100.00",cartItem1.totalDiscountString());
    }
}

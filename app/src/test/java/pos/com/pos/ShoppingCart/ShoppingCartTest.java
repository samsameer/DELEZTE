package pos.com.pos.ShoppingCart;

import org.junit.Before;
import org.junit.Test;

import pos.com.pos.discount.model.DiscountItem;
import pos.com.pos.item.model.Item;
import pos.com.pos.shoppingCart.ShoppingCart;
import pos.com.pos.shoppingCart.model.ShoppingCartItem;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by HJ Chin on 31/12/2017.
 */

public class ShoppingCartTest {

    private Item createSimpleItem() {
        return new Item(1,
                "product A",
                "url",
                "thumbUrl",
                100);
    }

    @Before
    public void setup(){
        ShoppingCart.getInstance().emptyCart();
    }

    @Test
    public void testDefaultEmptyCart() {
        ShoppingCart shoppingCart = ShoppingCart.getInstance();
        assertEquals("0.00", shoppingCart.getSubTotalString());
        assertEquals("0.00", shoppingCart.getDiscountString());
        assertEquals("0.00", shoppingCart.getChargeString());
        assertEquals(0, shoppingCart.getItemCount());
    }

    @Test
    public void testAddSingleItem(){
        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        /*
        * Add single Item
        * */
        ShoppingCartItem item = new ShoppingCartItem(createSimpleItem(),
                DiscountItem.discountA,
                1);
        shoppingCart.addItem(item);
        assertEquals("100.00",shoppingCart.getSubTotalString());
        assertEquals("0.00",shoppingCart.getDiscountString());
        assertEquals("100.00",shoppingCart.getChargeString());
        assertEquals(1,shoppingCart.getItemCount());
    }

    @Test
    public void testAddSameItemSameDiscount(){
        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        /*
        * Add single Item
        * */
        ShoppingCartItem item = new ShoppingCartItem(createSimpleItem(),
                DiscountItem.discountA,
                1);
        shoppingCart.addItem(item);
        assertEquals("100.00",shoppingCart.getSubTotalString());
        assertEquals("0.00",shoppingCart.getDiscountString());
        assertEquals("100.00",shoppingCart.getChargeString());
        assertEquals(1,shoppingCart.getItemCount());

        /*
            Add same Item with different quantity
         */
        item = new ShoppingCartItem(createSimpleItem(),
                DiscountItem.discountA,
                2);
        shoppingCart.addItem(item);
        assertEquals("300.00",shoppingCart.getSubTotalString());
        assertEquals("0.00",shoppingCart.getDiscountString());
        assertEquals("300.00",shoppingCart.getChargeString());
        assertEquals(1,shoppingCart.getItemCount());

        /*
            Add same Item with different quantity
         */
        item = new ShoppingCartItem(createSimpleItem(),
                DiscountItem.discountA,
                5);
        shoppingCart.addItem(item);
        assertEquals("800.00",shoppingCart.getSubTotalString());
        assertEquals("0.00",shoppingCart.getDiscountString());
        assertEquals("800.00",shoppingCart.getChargeString());
        assertEquals(1,shoppingCart.getItemCount());
    }

    @Test
    public void testAddSameItemDifferentDiscount(){

        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        /*
        * Add single Item
        * */
        ShoppingCartItem item = new ShoppingCartItem(createSimpleItem(),
                DiscountItem.discountA,
                1);
        shoppingCart.addItem(item);
        assertEquals("100.00",shoppingCart.getSubTotalString());
        assertEquals("0.00",shoppingCart.getDiscountString());
        assertEquals("100.00",shoppingCart.getChargeString());
        assertEquals(1,shoppingCart.getItemCount());

        /*
        * Add another same skuItem with different discount
        * */
        ShoppingCartItem item2 = new ShoppingCartItem(createSimpleItem(),
                DiscountItem.discountB,
                2);

        shoppingCart.addItem(item2);
        assertEquals("300.00",shoppingCart.getSubTotalString());
        assertEquals("20.00",shoppingCart.getDiscountString());
        assertEquals("280.00",shoppingCart.getChargeString());
        assertEquals(2,shoppingCart.getItemCount());

        /*
        * Add another same skuItem with same previous discount
        * */
        ShoppingCartItem item3 = new ShoppingCartItem(createSimpleItem(),
                DiscountItem.discountB,
                1);

        shoppingCart.addItem(item3);
        assertEquals("400.00",shoppingCart.getSubTotalString());
        assertEquals("30.00",shoppingCart.getDiscountString());
        assertEquals("370.00",shoppingCart.getChargeString());
        assertEquals(2,shoppingCart.getItemCount());
    }

    @Test
    public void testAddDifferentItemDifferentDiscount(){

        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        /*
        * Add different skuItem with discountC
        * */
        ShoppingCartItem item = new ShoppingCartItem(createSimpleItem(),
                DiscountItem.discountD,
                2);

        shoppingCart.addItem(item);
        assertEquals("200.00",shoppingCart.getSubTotalString());
        assertEquals("100.00",shoppingCart.getDiscountString());
        assertEquals("100.00",shoppingCart.getChargeString());
        assertEquals(1,shoppingCart.getItemCount());

        Item skuItem2 = new Item(2,
                "product B",
                "url",
                "thumbUrl",
                5);

        ShoppingCartItem item2 = new ShoppingCartItem(skuItem2,
                DiscountItem.discountD,
                2);

        shoppingCart.addItem(item2);
        assertEquals("210.00",shoppingCart.getSubTotalString());
        assertEquals("105.00",shoppingCart.getDiscountString());
        assertEquals("105.00",shoppingCart.getChargeString());
        assertEquals(2,shoppingCart.getItemCount());


        Item skuItem3 = new Item(3,
                "product B",
                "url",
                "thumbUrl",
                5);

        /*
            Add different skuItem with discountE
         */
        ShoppingCartItem item5 = new ShoppingCartItem(skuItem3,
                DiscountItem.discountB,
                1);
        shoppingCart.addItem(item5);
        assertEquals("215.00",shoppingCart.getSubTotalString());
        assertEquals("105.50",shoppingCart.getDiscountString());
        assertEquals("109.50",shoppingCart.getChargeString());
        assertEquals(3,shoppingCart.getItemCount());
    }

    @Test
    public void testUpdateItem(){
        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        Item skuItem = new Item(1,
                "product A",
                "url",
                "thumbUrl",
                100);

        ShoppingCartItem item = new ShoppingCartItem(skuItem,
                DiscountItem.discountA,
                1);
        shoppingCart.addItem(item);
        assertEquals("100.00",shoppingCart.getSubTotalString());
        assertEquals("0.00",shoppingCart.getDiscountString());
        assertEquals("100.00",shoppingCart.getChargeString());
        assertEquals(1,shoppingCart.getItemCount());

        /*
            Update with shopping cart item
        */
        ShoppingCartItem updateItem = new ShoppingCartItem(skuItem,
                DiscountItem.discountA,
                5);

        shoppingCart.updateItemByType(updateItem);
        assertEquals("500.00",shoppingCart.getSubTotalString());
        assertEquals("0.00",shoppingCart.getDiscountString());
        assertEquals("500.00",shoppingCart.getChargeString());
        assertEquals(1,shoppingCart.getItemCount());

        /*
            update with invalid item
         */
        ShoppingCartItem updateItem2 = new ShoppingCartItem(skuItem,
                DiscountItem.discountB,
                2);
        try{
            shoppingCart.updateItemByType(updateItem2);
            assertTrue("successfully update invalid item",false);
        }catch(IllegalArgumentException ex){
            assertTrue("invalid item to update",true);
        }
    }

    @Test
    public void testRemoveItem(){
        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        Item skuItem = new Item(1,
                "product A",
                "url",
                "thumbUrl",
                100);

        ShoppingCartItem item = new ShoppingCartItem(skuItem,
                DiscountItem.discountA,
                1);
        shoppingCart.addItem(item);

        ShoppingCartItem item2 = new ShoppingCartItem(skuItem,
                DiscountItem.discountB,
                10);
        shoppingCart.addItem(item2);

        assertEquals("1100.00",shoppingCart.getSubTotalString());
        assertEquals("100.00",shoppingCart.getDiscountString());
        assertEquals("1000.00",shoppingCart.getChargeString());
        assertEquals(2,shoppingCart.getItemCount());

        shoppingCart.removeItem(item);
        assertEquals("1000.00",shoppingCart.getSubTotalString());
        assertEquals("100.00",shoppingCart.getDiscountString());
        assertEquals("900.00",shoppingCart.getChargeString());
        assertEquals(1,shoppingCart.getItemCount());

        ShoppingCartItem item3 = new ShoppingCartItem(skuItem,
                DiscountItem.discountD,
                10);
        try{
            shoppingCart.removeItem(item3);
            assertTrue("successfully remove invalid item",false);
        }catch (IllegalArgumentException ex){
            assertTrue("invalid item to remove",true);
        }
    }

    @Test
    public void testEmptyCart(){
        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        Item skuItem = new Item(1,
                "product A",
                "url",
                "thumbUrl",
                100);

        ShoppingCartItem item = new ShoppingCartItem(skuItem,
                DiscountItem.discountA,
                1);
        shoppingCart.addItem(item);

        ShoppingCartItem item2 = new ShoppingCartItem(skuItem,
                DiscountItem.discountB,
                10);
        shoppingCart.addItem(item2);

        assertEquals("1100.00",shoppingCart.getSubTotalString());
        assertEquals("100.00",shoppingCart.getDiscountString());
        assertEquals("1000.00",shoppingCart.getChargeString());
        assertEquals(2,shoppingCart.getItemCount());

        shoppingCart.emptyCart();

        assertEquals("0.00",shoppingCart.getSubTotalString());
        assertEquals("0.00",shoppingCart.getDiscountString());
        assertEquals("0.00",shoppingCart.getChargeString());
        assertEquals(0,shoppingCart.getItemCount());
    }

    @Test
    public void testHasItem(){
        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        Item skuItem = new Item(1,
                "product A",
                "url",
                "thumbUrl",
                100);

        ShoppingCartItem item = new ShoppingCartItem(skuItem,
                DiscountItem.discountA,
                1);
        shoppingCart.addItem(item);

        ShoppingCartItem item2 = new ShoppingCartItem(skuItem,
                DiscountItem.discountB,
                10);

        assertEquals(true,shoppingCart.has(item));
        assertEquals(false,shoppingCart.has(item2));
    }
}

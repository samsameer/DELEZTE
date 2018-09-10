package pos.com.pos.InputBox;

import org.junit.Before;
import org.junit.Test;

import pos.com.pos.discount.model.DiscountItem;
import pos.com.pos.inputBox.presenter.InputBoxPresenter;
import pos.com.pos.inputBox.view.InputBoxFragment;
import pos.com.pos.inputBox.view.InputBoxFragmentView;
import pos.com.pos.item.model.Item;
import pos.com.pos.shoppingCart.ShoppingCart;
import pos.com.pos.shoppingCart.model.ShoppingCartItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by HJ Chin on 1/1/2018.
 */

public class InputBoxPresenterTest {

    @Before
    public void setup(){
        ShoppingCart.getInstance().emptyCart();
    }

    private ShoppingCartItem createSimpleItem(){
        return new ShoppingCartItem(
                new Item(1,"Product A","","",100),
                DiscountItem.discountA,
                1
        );
    }

    private InputBoxPresenter createPresenterWithSingleItem(){
        ShoppingCartItem item = createSimpleItem();
        InputBoxPresenter presenter = new InputBoxPresenter(
                ShoppingCart.getInstance(),
                item,
                mock(InputBoxFragmentView.class),
                mock(InputBoxFragment.Callback.class)

        );

        return presenter;
    }

    @Test
    public void testSaveNewItem(){
        InputBoxPresenter presenter = createPresenterWithSingleItem();
        presenter.save();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("100.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("100.00",ShoppingCart.getInstance().getChargeString());
    }

    @Test
    public void testIncreaseQuantity(){
        InputBoxPresenter presenter = createPresenterWithSingleItem();
        presenter.increaseQuantity();
        presenter.increaseQuantity();
        presenter.increaseQuantity();
        presenter.increaseQuantity();

        presenter.save();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("500.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("500.00",ShoppingCart.getInstance().getChargeString());

        /*
            test max quantity 1000
         */
        for(int i=0;i<1001;i++){
            presenter.increaseQuantity();
        }

        presenter.save();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("100000.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("100000.00",ShoppingCart.getInstance().getChargeString());
    }

    @Test
    public void testDecreaseQuantity(){
        InputBoxPresenter presenter = createPresenterWithSingleItem();
        presenter.increaseQuantity();
        presenter.increaseQuantity();
        presenter.increaseQuantity();
        presenter.increaseQuantity();

        presenter.decreaseQuantity();
        presenter.save();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("400.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("400.00",ShoppingCart.getInstance().getChargeString());
    }

    @Test
    public void testDecreaseZeroQuantity(){
        InputBoxPresenter presenter = createPresenterWithSingleItem();
        presenter.decreaseQuantity();
        presenter.save();

        assertEquals(0,ShoppingCart.getInstance().getItemCount());
        assertEquals("0.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("0.00",ShoppingCart.getInstance().getChargeString());
    }

    @Test
    public void testSetQuantity(){
        InputBoxPresenter presenter = createPresenterWithSingleItem();

        try{
            presenter.setQuantity(-100);
            assertTrue("able to set -100 as quantity", false);
        }catch (IllegalArgumentException ex){
            assertTrue("-100 is not a valid quantity", true);
        }

        try{
            presenter.setQuantity(100000);
            assertTrue("able to set 100000 as quantity", false);
        }catch (IllegalArgumentException ex){
            assertTrue("100000 is not a valid quantity", true);
        }

        presenter.setQuantity(100);
        presenter.save();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("10000.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("10000.00",ShoppingCart.getInstance().getChargeString());
    }

    @Test
    public void testAssignDiscount(){
        InputBoxPresenter presenter = createPresenterWithSingleItem();
        presenter.setDiscount(DiscountItem.discountB);
        presenter.save();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("100.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("10.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("90.00",ShoppingCart.getInstance().getChargeString());
    }

    @Test
    public void testCancel(){
        InputBoxPresenter presenter = createPresenterWithSingleItem();
        presenter.cancel();
        assertEquals(0,ShoppingCart.getInstance().getItemCount());
        assertEquals("0.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("0.00",ShoppingCart.getInstance().getChargeString());
    }

    @Test
    public void testUpdateExistingItemQuantity(){

        ShoppingCartItem item = createSimpleItem();
        ShoppingCart.getInstance().addItem(item);

        InputBoxPresenter presenter = new InputBoxPresenter(
                ShoppingCart.getInstance(),
                item,
                mock(InputBoxFragmentView.class),
                mock(InputBoxFragment.Callback.class)
        );

        presenter.setQuantity(10);
        presenter.save();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("1000.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("1000.00",ShoppingCart.getInstance().getChargeString());
    }

    @Test
    public void testUpdateExistingItemDiscount(){

        ShoppingCartItem item = createSimpleItem();
        ShoppingCart.getInstance().addItem(item);

        InputBoxPresenter presenter = new InputBoxPresenter(
                ShoppingCart.getInstance(),
                item,
                mock(InputBoxFragmentView.class),
                mock(InputBoxFragment.Callback.class)
        );

        /*
            Change Discount rate
         */
        presenter.setDiscount(DiscountItem.discountD);
        presenter.save();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("100.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("50.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("50.00",ShoppingCart.getInstance().getChargeString());

        /*
            Change multiple time discount rate
         */
        presenter.setDiscount(DiscountItem.discountB);
        presenter.setDiscount(DiscountItem.discountE);
        presenter.setDiscount(DiscountItem.discountC);
        presenter.setDiscount(DiscountItem.discountA);
        presenter.save();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("100.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("100.00",ShoppingCart.getInstance().getChargeString());

        /*
            set the same discount rate
         */
        presenter.setDiscount(DiscountItem.discountA);
        presenter.save();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("100.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("100.00",ShoppingCart.getInstance().getChargeString());

        /*
            Change the discount rate and quantity
         */
        presenter.setDiscount(DiscountItem.discountD);
        presenter.setQuantity(50);
        presenter.save();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("5000.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("2500.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("2500.00",ShoppingCart.getInstance().getChargeString());
    }

    @Test
    public void testRemoveExistingItem(){

        /*
            Remove single item from list of single item
         */
        ShoppingCartItem item = createSimpleItem();
        ShoppingCart.getInstance().addItem(item);

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("100.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("100.00",ShoppingCart.getInstance().getChargeString());

        InputBoxPresenter presenter = new InputBoxPresenter(
            ShoppingCart.getInstance(),
            item,
            mock(InputBoxFragmentView.class),
            mock(InputBoxFragment.Callback.class)
        );

        presenter.setQuantity(0);
        presenter.save();

        assertEquals(0,ShoppingCart.getInstance().getItemCount());
        assertEquals("0.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("0.00",ShoppingCart.getInstance().getChargeString());
    }

    @Test
    public void testRemoveExistingItemFromList(){
        /*
            Remove item from list of X items
         */

        ShoppingCartItem item1 = new ShoppingCartItem(
                new Item(1,"product A","","",100),
                DiscountItem.discountD,
                10
        );
        ShoppingCart.getInstance().addItem(item1);

        ShoppingCartItem item2 = new ShoppingCartItem(
                new Item(2,"product B","","",5),
                DiscountItem.discountD,
                5
        );
        ShoppingCart.getInstance().addItem(item2);

        ShoppingCartItem item3 = new ShoppingCartItem(
                new Item(3,"product C","","",20),
                DiscountItem.discountA,
                10
        );
        ShoppingCart.getInstance().addItem(item3);

        assertEquals(3,ShoppingCart.getInstance().getItemCount());
        assertEquals("1225.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("512.50",ShoppingCart.getInstance().getDiscountString());
        assertEquals("712.50",ShoppingCart.getInstance().getChargeString());

        InputBoxPresenter presenter1 = new InputBoxPresenter(
                ShoppingCart.getInstance(),
                item1,
                mock(InputBoxFragmentView.class),
                mock(InputBoxFragment.Callback.class)
        );
        presenter1.setQuantity(0);
        presenter1.save();

        assertEquals(2,ShoppingCart.getInstance().getItemCount());
        assertEquals("225.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("12.50",ShoppingCart.getInstance().getDiscountString());
        assertEquals("212.50",ShoppingCart.getInstance().getChargeString());

        InputBoxPresenter presenter2 = new InputBoxPresenter(
            ShoppingCart.getInstance(),
            item2,
            mock(InputBoxFragmentView.class),
            mock(InputBoxFragment.Callback.class)
        );
        presenter2.setQuantity(0);
        presenter2.save();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("200.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("200.00",ShoppingCart.getInstance().getChargeString());
    }

    @Test
    public void testCancelExisting(){
        ShoppingCartItem item = createSimpleItem();
        ShoppingCart.getInstance().addItem(item);

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("100.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("100.00",ShoppingCart.getInstance().getChargeString());

        InputBoxPresenter presenter = new InputBoxPresenter(
                ShoppingCart.getInstance(),
                item,
                mock(InputBoxFragmentView.class),
                mock(InputBoxFragment.Callback.class)
        );

        presenter.setQuantity(0);
        presenter.setDiscount(DiscountItem.discountD);
        presenter.cancel();

        assertEquals(1,ShoppingCart.getInstance().getItemCount());
        assertEquals("100.00",ShoppingCart.getInstance().getSubTotalString());
        assertEquals("0.00",ShoppingCart.getInstance().getDiscountString());
        assertEquals("100.00",ShoppingCart.getInstance().getChargeString());
    }

}

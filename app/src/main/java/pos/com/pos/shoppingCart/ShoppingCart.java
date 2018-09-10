package pos.com.pos.shoppingCart;

import java.util.LinkedHashMap;
import java.util.Map;

import pos.com.pos.shoppingCart.model.ShoppingCartItem;
import pos.com.pos.util.Util;

/**
 * Created by HJ Chin on 31/12/2017.
 */

public class ShoppingCart {

    private static ShoppingCart cart;

    private Map<String,ShoppingCartItem> shoppingList;
    private Map<String, String> typeList; //Map<<item_id>_<discount_id>,<item_id>>

    private double subTotal;
    private double discount;
    private double charge;

    private ShoppingCart(){
        shoppingList = new LinkedHashMap<>();
        typeList = new LinkedHashMap<>();
    }

    public static ShoppingCart getInstance(){
        if(cart == null){
            cart = new ShoppingCart();
        }

        return cart;
    }

    public void addItem(ShoppingCartItem scItem){

        String retrievedId = typeList.get(getType(scItem));
//        System.out.println("add");
//        System.out.println("Item:"+scItem.getItemName()+
//                ", subtotal:"+scItem.totalBeforeDiscountString()+
//                ", discount:"+String.valueOf(scItem.totalDiscount())+
//                ", charge:"+String.valueOf(scItem.totalAfterDiscountString()));

        if(retrievedId == null){
            shoppingList.put(scItem.getId(),scItem);
            typeList.put(getType(scItem),scItem.getId());
        }else{
            ShoppingCartItem retrieved = shoppingList.get(retrievedId);
            int newTotal = retrieved.getQuantity()+scItem.getQuantity();
            removeItemByType(retrieved);
            ShoppingCartItem mergeScItem = new ShoppingCartItem(scItem.getItem(),scItem.getDiscount(),newTotal);
            shoppingList.put(mergeScItem.getId(),mergeScItem);
            typeList.put(getType(mergeScItem), mergeScItem.getId());
        }

        calTotal();
    }

    public void updateItemByType(ShoppingCartItem scItem){

        String retrievedId = typeList.get(getType(scItem));

        if(retrievedId == null){
            throw new IllegalArgumentException("Invalid shopping cart item");
        }else{
            ShoppingCartItem retrieved = shoppingList.get(retrievedId);
            removeItemByType(retrieved);
            shoppingList.put(scItem.getId(),scItem);
            typeList.put(getType(scItem), scItem.getId());
        }

        calTotal();
    }

    public void removeItem(ShoppingCartItem scItem){

        if(shoppingList.get(scItem.getId()) == null){
            throw new IllegalArgumentException("Invalid shopping cart item");
        }

//        System.out.println("remove");
//        System.out.println("Item:"+scItem.getItemName()+
//                ", subtotal:"+scItem.totalBeforeDiscountString()+
//                ", discount:"+String.valueOf(scItem.totalDiscount())+
//                ", charge:"+String.valueOf(scItem.totalAfterDiscountString()));

        shoppingList.remove(scItem.getId());
        typeList.remove(getType(scItem));
        calTotal();
    }

    public void removeItemByType(ShoppingCartItem scItem){
        String retrievedIid = typeList.get(getType(scItem));

        if(retrievedIid == null){
            throw new IllegalArgumentException("shopping cart item with same type not found");
        }

        removeItem(shoppingList.get(retrievedIid));
    }

    public int getItemCount(){
        return shoppingList.size();
    }

    public ShoppingCartItem[] getItems(){
        return shoppingList.values().toArray(new ShoppingCartItem[shoppingList.size()]);
    }

    public void emptyCart(){
        shoppingList.clear();
        typeList.clear();
        calTotal();
    }

    public boolean has(ShoppingCartItem scItem){
        if(shoppingList.get(scItem.getId()) == null){
            return false;
        }
        return true;
    }

    public boolean hasType(ShoppingCartItem scItem){
        if(typeList.get(getType(scItem)) == null){
            return false;
        }
        return true;
    }

    private String getType(ShoppingCartItem item){
        return String.valueOf(item.getItem().id)+"_"+item.getDiscount().id;
    }

    private void calTotal(){
        subTotal = 0;
        discount = 0;
        charge = 0;

        //System.out.println("Cal");

        for(Map.Entry<String, ShoppingCartItem> entry: shoppingList.entrySet()){
            subTotal += entry.getValue().totalBeforeDiscount();
            discount += entry.getValue().totalDiscount();
            charge += entry.getValue().totalAfterDiscount();

//            System.out.println("Item:"+entry.getValue().getItemName()+
//                    ", subtotal:"+entry.getValue().totalBeforeDiscountString()+
//                    ", discount:"+String.valueOf(entry.getValue().totalDiscount())+
//                    ", charge:"+String.valueOf(entry.getValue().totalAfterDiscountString()));

        }
    }

    public String getSubTotalString(){
        return Util.formatDisplay(subTotal);
    }

    public String getDiscountString(){
        return Util.formatDisplay(discount);
    }

    public String getChargeString(){
        return Util.formatDisplay(charge);
    }
}

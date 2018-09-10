# Overview
1. This sample POS system has an screen with 2 fragments, left fragment and right fragment.

2. Left fragment is library fragment. user can choose whether he want to add item or discount into shopping cart. If
   the user select ‘All Discounts’ option, the fragment will be replaced with a new
   fragment contains list of discounts called DiscountListFragment. If the user select
   ‘All Items’ option, the fragment will be replaced with another fragment contains
   list of items called ItemListFragment.

3. ItemListFragment show All Items Data, Item has name, price and thumbnail. Retrieve
   data with this REST https://jsonplaceholder.typicode.com/photos. Store data to database.

       JSON Format :
       {
         albumId: 1,
         id: 1,
         title: "accusamus beatae ad facilis cum similique quisunt",
         url: "http://placehold.it/600/92c952",
         thumbnailUrl: "http://placehold.it/150/92c952"
       }

   - Image = thumbnailUrl
   - Name = title
   - Price = calculation from id * random number (10 - 99 )

4. DiscountListFragment show All Discounts data. This fragment show after user clicked the
   All Discount menu at the Library Framgent. Discount has percentage value 0 - 100%.
   Discounts are created localy with 5 values:
   1. Discount A - 0%
   2. Discount B - 10%
   3. Discount C - 35.5%
   4. Discount D - 50%
   5. Discount E - 100%

5. Right fragment is shopping cart fragment. Shopping cart contains items that the user are going to buy.
   Click on item on ItemListFragment to add item to Shopping Cart. When user click item, it will show popup
   User can input quantity max to 1000. There are 5 switch discount, discount A to E.

6. If add same item (same item ID with same discount), it will be merged as one
   item and the quantity will be increase.

   Example 1:

       Add Item A -- discount 15% -- quantity 1
       Add Item A – discount 15% -- quantity 2

       On Shopping Cart itu will be merged as :
       Item A --- discount 15% -- quantity 3

   Example 2:

       Add Item A -- discount 15% -- quantity 1
       Add Item A – discount 100% -- quantity 2

       It wont be merged, because different discount

7. Shopping cart fragment description

    | Item          |Quantity       | Price |
    | ------------- |:-------------:| -----:|
    | Item 1        | x1 | $100.00 |
    | Item 2      | x2     |   $12.00 |
    | Subtotal |       |    $112.00 |
    |Discount | |($10.00)|

    Legend:
    - $100.00 = Total price on item without discount, item price * quantity
    - ($10.00) = Total discounts for all items, Total price on item * discount percentage

    There are following in the shopping cart:
    - a 'Clear Sales' button to clear shopping cart
    - a text display of total charges, Subtotal - Discount

8. Edit item item that already on
   shopping by clicking it. Edit item
   layout will be a popup but the quantity will be already
   filled and discount already toggle
   according what we apply earlier.


# Libraries
- Retrofit, RxJava and RxAndroid
- Glide
- Room
- Mockito
- Espresso


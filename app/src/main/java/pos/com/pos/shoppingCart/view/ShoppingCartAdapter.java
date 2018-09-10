package pos.com.pos.shoppingCart.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pos.com.pos.R;
import pos.com.pos.databinding.FragmentShoppingCartItemBinding;
import pos.com.pos.shoppingCart.model.DiscountItem;
import pos.com.pos.shoppingCart.model.Item;
import pos.com.pos.shoppingCart.model.ShoppingCartItem;
import pos.com.pos.shoppingCart.model.SubTotalItem;
import pos.com.pos.util.Util;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private List<Item> values;
    private final ShoppingCartFragment.Callback callback;

    public ShoppingCartAdapter(List<Item> items, ShoppingCartFragment.Callback listener) {
        values = items;
        callback = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentShoppingCartItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.fragment_shopping_cart_item,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = values.get(position);

        if(holder.item instanceof ShoppingCartItem){
            ShoppingCartItem item = (ShoppingCartItem)holder.item;
            holder.binding.name.setText(item.getItemName());
            holder.binding.quantity.setText("x"+item.getQuantityString());
            holder.binding.price.setText(Util.formatMoney(item.totalBeforeDiscount()));
            holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callback.onShoppingCartItemClick(
                            (ShoppingCartItem) values.get(holder.getAdapterPosition())
                    );
                }
            });
        }

        if(holder.item instanceof SubTotalItem){
            SubTotalItem item = (SubTotalItem)holder.item;
            holder.binding.name.setText(item.text);
            holder.binding.quantity.setText("");
            holder.binding.price.setText(Util.formatMoney(item.amount));
            holder.binding.getRoot().setOnClickListener(null);
        }

        if(holder.item instanceof DiscountItem){
            DiscountItem item = (DiscountItem)holder.item;
            holder.binding.name.setText(item.text);
            holder.binding.quantity.setText("");
            holder.binding.price.setText("("+Util.formatMoney(item.amount)+")");
            holder.binding.getRoot().setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void setData(List<Item> data) {
        values = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Item item;
        public final FragmentShoppingCartItemBinding binding;

        ViewHolder(FragmentShoppingCartItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

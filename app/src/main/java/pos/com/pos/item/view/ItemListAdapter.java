package pos.com.pos.item.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import pos.com.pos.R;
import pos.com.pos.item.model.Item;
import pos.com.pos.databinding.FragmentSkuItemBinding;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private  List<Item> values;
    private final ItemListFragment.Callback callback;

    public ItemListAdapter(List<Item> items,
                           ItemListFragment.Callback listener) {
        values = items;
        callback = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentSkuItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.fragment_sku_item, parent,false);
        return new ViewHolder(binding);
    }

    public void setValues(List<Item> items){
        values = items;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = values.get(position);

        Glide
            .with(holder.binding.getRoot().getContext())
            .load(holder.item.thumbnailUrl)
            .into(holder.binding.itemImage);

        holder.binding.name.setText(holder.item.title);
        holder.binding.price.setText("$"+holder.item.price);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != callback) {
                    callback.onSKUItemClick(holder.item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Item item;
        FragmentSkuItemBinding binding;

        ViewHolder(FragmentSkuItemBinding binding ) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

package pos.com.pos.discount.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import pos.com.pos.R;
import pos.com.pos.databinding.FragmentDiscountItemBinding;
import pos.com.pos.discount.model.DiscountItem;

import java.util.List;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {

    private final List<DiscountItem> values;
    private final DiscountListFragment.Callback callback;

    DiscountAdapter(List<DiscountItem> items, DiscountListFragment.Callback listener) {
        values = items;
        callback = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentDiscountItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.fragment_discount_item,parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = values.get(position);
        holder.binding.name.setText(values.get(position).name);
        holder.binding.description.setText(String.valueOf(values.get(position).discount)+" %");
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FragmentDiscountItemBinding binding;
        DiscountItem item;

        ViewHolder(FragmentDiscountItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

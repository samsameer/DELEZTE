package pos.com.pos.shoppingCart.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pos.com.pos.R;
import pos.com.pos.databinding.FragmentShoppingCartBinding;
import pos.com.pos.shoppingCart.Presenter.ShoppingCartPresenter;
import pos.com.pos.shoppingCart.ShoppingCart;
import pos.com.pos.shoppingCart.model.Item;
import pos.com.pos.shoppingCart.model.ShoppingCartItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callback}
 * interface.
 */
public class ShoppingCartFragment extends Fragment implements ShoppingCartFragmentView{

    private Callback callback;
    private FragmentShoppingCartBinding binding;
    private ShoppingCartAdapter shoppingCartAdapter;
    private ShoppingCartPresenter presenter;

    public ShoppingCartFragment() {
    }

    public static ShoppingCartFragment newInstance() {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_shopping_cart,container,false);
        binding.frameName.setText(getActivity().getString(R.string.shopping_cart));
        binding.shoppingCartList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        shoppingCartAdapter = new ShoppingCartAdapter(new ArrayList<Item>(), callback);
        binding.shoppingCartList.setAdapter(shoppingCartAdapter);
        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.clearSales();
            }
        });

        presenter = new ShoppingCartPresenter(
                ShoppingCart.getInstance(),
                this
        );


        return binding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            callback = (Callback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public void refresh() {
        presenter.refresh();
    }

    @Override
    public void refreshList(List<Item> items) {
        shoppingCartAdapter.setData(items);
    }

    @Override
    public void updateChargeButton(String s) {
        binding.chargeButton.setText(s);
    }

    public interface Callback {
        void onShoppingCartItemClick(ShoppingCartItem item);
    }
}

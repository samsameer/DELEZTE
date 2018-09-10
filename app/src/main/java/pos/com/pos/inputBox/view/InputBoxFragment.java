package pos.com.pos.inputBox.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import pos.com.pos.R;
import pos.com.pos.databinding.InputBoxBinding;
import pos.com.pos.discount.model.DiscountItem;
import pos.com.pos.inputBox.presenter.InputBoxPresenter;
import pos.com.pos.shoppingCart.ShoppingCart;
import pos.com.pos.shoppingCart.model.ShoppingCartItem;
import pos.com.pos.util.InputFilterMinMax;
import pos.com.pos.util.Util;

/**
 * Created by HJ Chin on 1/1/2018.
 */

public class InputBoxFragment extends DialogFragment implements InputBoxFragmentView {

    public interface Callback{
        void onInputBoxSave();
    }

    private Callback callback;
    private InputBoxBinding binding;
    private InputBoxPresenter presenter;

    public static String SHOPPING_CART_ITEM = "shoppingCartItem";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement InputBoxFragment callback");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(inflater, R.layout.input_box,null,false);

        final ShoppingCartItem item = getArguments().getParcelable(SHOPPING_CART_ITEM);

        if(item == null){
            throw new NullPointerException("shopping cart item is null");
        }

        builder.setView(binding.getRoot());

        presenter = new InputBoxPresenter(
                ShoppingCart.getInstance(),
                item,
                this,
                callback);

        return builder.create();
    }

    @Override
    public void initView(ShoppingCartItem item) {
        binding.label.setText(item.getItem().title+" "+ Util.formatMoney(item.getItem().price));

        binding.btnCancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                presenter.cancel();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                presenter.save();
            }
        });

        binding.btnDecrease.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                presenter.decreaseQuantity();
            }
        });

        binding.btnIncrease.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                presenter.increaseQuantity();
            }
        });

        binding.quantity.setFilters(new InputFilter[]{new InputFilterMinMax(0,1000)});
        binding.quantity.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String value = ((EditText)v).getText().toString();
                    try{
                        presenter.setQuantity(Integer.parseInt(value));
                    }catch (Exception e){

                    }
                    return true;
                }
                return false;
            }
        });

        binding.discountA.setTag(DiscountItem.discountA);
        binding.discountA.setOnCheckedChangeListener(new OnCheckChangeListener());
        binding.discountB.setTag(DiscountItem.discountB);
        binding.discountB.setOnCheckedChangeListener(new OnCheckChangeListener());
        binding.discountC.setTag(DiscountItem.discountC);
        binding.discountC.setOnCheckedChangeListener(new OnCheckChangeListener());
        binding.discountD.setTag(DiscountItem.discountD);
        binding.discountD.setOnCheckedChangeListener(new OnCheckChangeListener());
        binding.discountE.setTag(DiscountItem.discountE);
        binding.discountE.setOnCheckedChangeListener(new OnCheckChangeListener());

        setDiscount(item.getDiscount());
    }

    private class OnCheckChangeListener implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            DiscountItem discountItem = (DiscountItem)compoundButton.getTag();
            if(b){
                presenter.setDiscount(discountItem);
            }
        }
    }

    @Override
    public void refreshQuantity(int quantity) {
        binding.quantity.setText(String.valueOf(quantity));
    }

    @Override
    public void finish() {
        dismiss();
    }

    @Override
    public void setDiscount(DiscountItem discountItem) {

        binding.discountA.setOnCheckedChangeListener(null);
        binding.discountB.setOnCheckedChangeListener(null);
        binding.discountC.setOnCheckedChangeListener(null);
        binding.discountD.setOnCheckedChangeListener(null);
        binding.discountE.setOnCheckedChangeListener(null);

        if(discountItem.compareTo(DiscountItem.discountA) == 0){
            binding.discountA.setChecked(true);
            binding.discountB.setChecked(false);
            binding.discountC.setChecked(false);
            binding.discountD.setChecked(false);
            binding.discountE.setChecked(false);
        }

        if(discountItem.compareTo(DiscountItem.discountB) == 0){
            binding.discountA.setChecked(false);
            binding.discountB.setChecked(true);
            binding.discountC.setChecked(false);
            binding.discountD.setChecked(false);
            binding.discountE.setChecked(false);
        }

        if(discountItem.compareTo(DiscountItem.discountC) == 0){
            binding.discountA.setChecked(false);
            binding.discountB.setChecked(false);
            binding.discountC.setChecked(true);
            binding.discountD.setChecked(false);
            binding.discountE.setChecked(false);
        }

        if(discountItem.compareTo(DiscountItem.discountD) == 0){
            binding.discountA.setChecked(false);
            binding.discountB.setChecked(false);
            binding.discountC.setChecked(false);
            binding.discountD.setChecked(true);
            binding.discountE.setChecked(false);
        }

        if(discountItem.compareTo(DiscountItem.discountE) == 0){
            binding.discountA.setChecked(false);
            binding.discountB.setChecked(false);
            binding.discountC.setChecked(false);
            binding.discountD.setChecked(false);
            binding.discountE.setChecked(true);
        }

        binding.discountA.setOnCheckedChangeListener(new OnCheckChangeListener());
        binding.discountB.setOnCheckedChangeListener(new OnCheckChangeListener());
        binding.discountC.setOnCheckedChangeListener(new OnCheckChangeListener());
        binding.discountD.setOnCheckedChangeListener(new OnCheckChangeListener());
        binding.discountE.setOnCheckedChangeListener(new OnCheckChangeListener());
    }
}

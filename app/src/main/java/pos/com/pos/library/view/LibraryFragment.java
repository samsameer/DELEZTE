package pos.com.pos.library.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pos.com.pos.R;
import pos.com.pos.databinding.FragmentLeftFrameBinding;
import pos.com.pos.library.model.DiscountItem;
import pos.com.pos.library.model.Item;
import pos.com.pos.library.model.SKUItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callback}
 * interface.
 */
public class LibraryFragment extends Fragment {

    private Callback callback;

    public LibraryFragment() {
    }

    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentLeftFrameBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_left_frame,container,false);
        binding.frameName.setText(getString(R.string.library));
        binding.list.setLayoutManager(new LinearLayoutManager(container.getContext()));
        binding.list.setAdapter(new LibraryAdapter(Item.libraryItemArrayList, callback));
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

    public interface Callback {
        void onItemClick(Item item);
    }
}

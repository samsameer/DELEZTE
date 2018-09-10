package pos.com.pos.item.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import pos.com.pos.R;
import pos.com.pos.data.AppDatabase;
import pos.com.pos.databinding.FragmentLeftFrameBinding;
import pos.com.pos.item.Presenter.ItemListPresenter;
import pos.com.pos.item.model.ItemRepo;
import pos.com.pos.item.model.Item;
import pos.com.pos.data.HttpClient;
import pos.com.pos.util.AppExecutors;

public class ItemListFragment extends Fragment implements ItemListView{

    private ItemListPresenter presenter;

    private Callback callback;
    private ItemListAdapter adapter;
    private FragmentLeftFrameBinding binding;
    private CountingIdlingResource countingIdlingResource;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ItemListPresenter(
                countingIdlingResource,
                new ItemRepo(AppDatabase.getInstance(getActivity()).itemDao(),
                        HttpClient.getInstance(),
                        AppExecutors.getInstance()),
                this);
    }

    public void setCountingIdlingResource(CountingIdlingResource countingIdlingResource){
        this.countingIdlingResource = countingIdlingResource;
    }

    public static ItemListFragment newInstance(CountingIdlingResource countingIdlingResource) {
        ItemListFragment fragment = new ItemListFragment();
        fragment.setCountingIdlingResource(countingIdlingResource);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_left_frame,container,false);
        binding.frameName.setText(getString(R.string.all_items));
        binding.list.setLayoutManager(new LinearLayoutManager(container.getContext()));

        adapter = new ItemListAdapter(new ArrayList<Item>(), callback);
        binding.list.setAdapter(adapter);
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

    @Override
    public void showItems(Item[] items) {
        adapter.setValues(Arrays.asList(items));
    }

    @Override
    public void showError(Throwable throwable) {
        Snackbar.make(binding.frameName,"Error loading Items",Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    public interface Callback {
        void onSKUItemClick(Item item);
    }
}

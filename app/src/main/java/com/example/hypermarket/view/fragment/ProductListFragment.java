package com.example.hypermarket.view.fragment;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.R;
import com.example.hypermarket.adapter.ProductListAdapter;
import com.example.hypermarket.databinding.FragmentProductListBinding;
import com.example.hypermarket.eventBus.ProductListSortMassage;
import com.example.hypermarket.model.Product;
import com.example.hypermarket.viewModel.ProductListFragmentViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {

    public static final String SEARCH_STRING_ARG = "searchStringArg";
    public static final String SEARCHABLE_ARG = "searchableArg";

    private ProductListAdapter productAdapter;
    private RecyclerView recyclerView;
    private String searchText;
    private Boolean searchable;
    private String mOrderType, mOrderBy;
    private int productListPage = 1;
    private TextView sortTextView;
    private int mSortType;
    private ProgressBar progressBar;
    private boolean newList = true;

    private FragmentProductListBinding mBinding;
    private ProductListFragmentViewModel mViewModel;

    public static ProductListFragment newInstance(String searchText, Boolean searchable) {

        Bundle args = new Bundle();
        args.putString(SEARCH_STRING_ARG, searchText);
        args.putBoolean(SEARCHABLE_ARG, searchable);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchable = getArguments().getBoolean(SEARCHABLE_ARG);
        searchText = getArguments().getString(SEARCH_STRING_ARG);
        mViewModel = ViewModelProviders.of(this).get(ProductListFragmentViewModel.class);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false);

        initUi();
        setRecyclerView();
        setListeners();

        mBinding.titleToolbarProductList.setText(searchText);

        mViewModel.getProductList().observe(this, productList -> {
            loadingProgressBar(false);
            setAdapterList(newList, productList);
        });

        return mBinding.getRoot();
    }

    private void initUi() {
        recyclerView = mBinding.productListRecyclerView;
        sortTextView = mBinding.sortModeTextView;
        progressBar = mBinding.productListProgressBar;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setRecyclerView() {
        productAdapter = new ProductListAdapter((AppCompatActivity) getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(productAdapter);
        loadingProgressBar(true);
        mViewModel.loadFilteredListFromApi(searchText, mOrderBy, mOrderType, productListPage);
//        recyclerView.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
//            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mViewModel
//                    .getProductList().getValue().size() - 2) {
//                newList = false ;
//                mViewModel.loadFilteredListFromApi(searchText , mOrderBy , mOrderType,++productListPage);
//            }
//        });
    }

    private void setAdapterList(boolean newList, List<Product> productList) {
        productAdapter.setProducts(productList);
        if (productAdapter.getProductList().size() == 0) {
            mBinding.nullMassageProductListTextview.setVisibility(View.VISIBLE);
        }
        else {
            mBinding.nullMassageProductListTextview.setVisibility(View.GONE);
        }
    }

    private void setListeners() {
        mBinding.backToolbarProductList.setOnClickListener(view1 -> getActivity().onBackPressed());

        mBinding.sortRelative.setOnClickListener(view12 -> {
            DialogFragment dialogFragment = SortDialogFragment.newInstance(mSortType);
            dialogFragment.show(getFragmentManager(), null);
        });

        mBinding.filterRelative.setOnClickListener(view -> getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, android.R.anim.fade_out)
                .replace(R.id.fragment_container, FilterFragment.newInstance())
                .addToBackStack("FilterTransaction")
                .commit());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSortChanged(ProductListSortMassage productListSortMassage) {
        mSortType = productListSortMassage.getEnumIndex();
        SortDialogFragment.Sorts sort = SortDialogFragment.getEnumSorts(mSortType);
        switch (sort) {
            case NEWEST:
                mOrderType = "date";
                sortTextView.setText("جدیدترین");
                break;
            case RATED:
                mOrderType = "popularity";
                sortTextView.setText("پربازدیدترین");
                break;
            case VISITED:
                mOrderType = "rating";
                sortTextView.setText("پرفروش ترین");
                break;
            case LOW_TO_HIGH:
                mOrderType = "price";
                mOrderBy = "asc";
                sortTextView.setText("قیمت از کم به زیاد");
                break;
            case HIGH_TO_LOW:
                mOrderType = "price";
                mOrderBy = "desc";
                sortTextView.setText("قیمت از زیاد به کم");
                break;
        }
        loadingProgressBar(true);
        mViewModel.loadFilteredListFromApi(searchText, mOrderType, mOrderBy, productListPage);
    }

    private void loadingProgressBar(boolean load) {
        if (load) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.setEmptySelectedTerm();
    }
}

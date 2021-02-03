package com.example.hypermarket.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.R;
import com.example.hypermarket.adapter.ProductMainAdapter;
import com.example.hypermarket.databinding.FragmentCategoryDetailBinding;
import com.example.hypermarket.utils.UiUtils;
import com.example.hypermarket.viewModel.CategoriesViewModel;
import com.example.hypermarket.viewModel.ProductBasketViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDetailFragment extends Fragment {

    public static final String CATEGORY_ID_ARG = "categoryIdArg";

    private int categoryid;
    private RecyclerView latestProductsRecyclerView, popularProductsRecyclerView;
    private ProductMainAdapter latestProductsAdapter, popularProductsAdapter;
    private ProgressBar progressBar;

    private FragmentCategoryDetailBinding mBinding;
    private CategoriesViewModel mViewModel;
    private ProductBasketViewModel productBasketViewModel;

    public static CategoryDetailFragment newInstance(int categoryid) {
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID_ARG, categoryid);
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryid = getArguments().getInt(CATEGORY_ID_ARG);
        mViewModel = ViewModelProviders.of(getActivity()).get(CategoriesViewModel.class);
        productBasketViewModel = ViewModelProviders.of(this).get(ProductBasketViewModel.class);
        mViewModel.loadCategoryById(categoryid);
        mViewModel.loadNewProductListFromApi(categoryid);
        mViewModel.loadRatedProductListFromApi(categoryid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_detail, container, false);
        mBinding.setCategoryDetailViewModel(mViewModel);
        initUi();
        setupRecyclerViews();
        setObservers();
        setListeners();

        return mBinding.getRoot();
    }

    private void initUi() {
        latestProductsRecyclerView = mBinding.lateestProductsRecyclerViewDetail;
        popularProductsRecyclerView = mBinding.popularProductsRecyclerViewDetail;
        progressBar = mBinding.progressBarCategoryDetail;
    }

    private void setupRecyclerViews() {
        latestProductsAdapter = new ProductMainAdapter((AppCompatActivity) getActivity());
        popularProductsAdapter = new ProductMainAdapter((AppCompatActivity) getActivity());
        latestProductsRecyclerView.setAdapter(latestProductsAdapter);
        popularProductsRecyclerView.setAdapter(popularProductsAdapter);
    }

    private void setObservers() {
        mViewModel.getRatedProducts().observe(this, productList -> {
            popularProductsAdapter.setProducts(productList);
            progressBar.setVisibility(View.INVISIBLE);
        });

        mViewModel.getNewProducts().observe(this, productList -> {
            latestProductsAdapter.setProducts(productList);
            progressBar.setVisibility(View.INVISIBLE);
        });

        productBasketViewModel.getCartProductBasketList().observe(this, shoppingBagList -> {
            UiUtils.setBadgeicon(shoppingBagList.size(), mBinding.shoppingCartIconCategoryFragment.basketTextview);
        });
    }

    private void setListeners() {
        mBinding.backCateogryDetailImageview.setOnClickListener(view -> getActivity().onBackPressed());

        mBinding.shoppingCartIconCategoryFragment.basketImageview.setOnClickListener(view -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ProductBasketFragment.newInstance())
                .addToBackStack(null)
                .commit());
    }
}

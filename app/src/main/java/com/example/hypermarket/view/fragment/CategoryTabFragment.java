package com.example.hypermarket.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.R;
import com.example.hypermarket.adapter.CategoryAdapter;
import com.example.hypermarket.databinding.FragmentCategoryTabBinding;
import com.example.hypermarket.viewModel.CategoriesViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryTabFragment extends Fragment {

    private static final String CATEGORY_PARENT_ID_ARG = "categoryIdArg";

    private int parentId ;

    private FragmentCategoryTabBinding mBinding ;
    private CategoriesViewModel mViewModel ;

    public static CategoryTabFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(CATEGORY_PARENT_ID_ARG , id);
        CategoryTabFragment fragment = new CategoryTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentId = getArguments().getInt(CATEGORY_PARENT_ID_ARG);
        mViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class) ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding =  DataBindingUtil.inflate(inflater ,R.layout.fragment_category_tab, container, false);
        setupRecyclerView();

        return mBinding.getRoot() ;
    }

    private void setupRecyclerView(){
        RecyclerView recyclerView = mBinding.categoryListRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CategoryAdapter categoryAdapter = new CategoryAdapter((AppCompatActivity) getActivity());
        categoryAdapter.setCategories(mViewModel.getSubCategories(parentId));
        recyclerView.setAdapter(categoryAdapter) ;
    }
}

package com.example.hypermarket.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.hypermarket.R;
import com.example.hypermarket.databinding.FragmentSearchBinding;
import com.example.hypermarket.view.activity.ProductListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private FragmentSearchBinding mBinding;

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);

        mBinding.backImageViewSearch.setOnClickListener(view1 -> getActivity().onBackPressed());

        mBinding.searchEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                startActivity(ProductListActivity.newIntent(getContext(), textView.getText().toString(), true));
                return true;
            }
            return false;
        });

        return mBinding.getRoot();
    }
}

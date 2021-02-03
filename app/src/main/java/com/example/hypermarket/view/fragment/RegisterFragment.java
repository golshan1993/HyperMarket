package com.example.hypermarket.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hypermarket.R;
import com.example.hypermarket.databinding.FragmentRegisterBinding;
import com.example.hypermarket.viewModel.LoginRegisterViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    private FragmentRegisterBinding mBinding ;
    private LoginRegisterViewModel mViewModel ;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        
        Bundle args = new Bundle();
        
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(LoginRegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_register , container , false);

        setListeners();

        return mBinding.getRoot();
    }

    private void setListeners(){
        mBinding.toolbarRegisterIvBack.setOnClickListener(view -> getActivity().onBackPressed());
        mBinding.registerBtn.setOnClickListener(view -> register());
    }

    private void register(){
        boolean result = false ;
        if(checkInputs())
            result = mViewModel.registerCustomer(mBinding.registerEmail.getText().toString());

        if(result)
            getActivity().onBackPressed();
    }

    private boolean checkInputs()
    {
        return true;
    }
}

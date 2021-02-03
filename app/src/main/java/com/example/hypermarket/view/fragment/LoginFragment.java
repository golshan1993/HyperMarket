package com.example.hypermarket.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hypermarket.R;
import com.example.hypermarket.databinding.FragmentLoginBinding;
import com.example.hypermarket.viewModel.LoginRegisterViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding mBinding ;
    private LoginRegisterViewModel mViewModel ;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(LoginRegisterViewModel.class) ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_login , container , false);

        setListeners();
        return mBinding.getRoot();
    }

    private void setListeners(){
        mBinding.toolbarLoginIvBack.setOnClickListener(view -> getActivity().onBackPressed());

        mBinding.loginTvRegister.setOnClickListener(view -> getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container , RegisterFragment.newInstance())
                .commit());

        mBinding.basketLoginButton.setOnClickListener(view -> checkLogin());
    }

    private void checkLogin(){
        if(mViewModel.loginCustomer(mBinding.basketLoginEmail.getText().toString())) {
            Toast.makeText(getActivity(), "با موفقیت وارد شدید", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }
    }
}

package com.example.hypermarket.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.hypermarket.R;
import com.example.hypermarket.database.CustomerAddressModel;
import com.example.hypermarket.databinding.FragmentAddEditAddressBinding;
import com.example.hypermarket.repositories.CustomerRepository;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditAddressFragment extends Fragment {

    private FragmentAddEditAddressBinding mBinding ;

    public static AddEditAddressFragment newInstance() {

        Bundle args = new Bundle();

        AddEditAddressFragment fragment = new AddEditAddressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddEditAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_add_edit_address , container , false);

        mBinding.rcSaveAddress.setOnClickListener(view -> {
            if(checkInputs()) {
                AddAddress();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return mBinding.getRoot() ;
    }

    private void AddAddress(){
        CustomerAddressModel address = new CustomerAddressModel();
        address.setAddress(mBinding.rcAddress.getText().toString());
        address.setFirstName(mBinding.rcName.getText().toString());
        address.setCustomerId(CustomerRepository.getInstance(getContext()).getCustomer().getValue().getId());
        CustomerRepository.getInstance(getContext()).inserCustomerAddress(address);
    }

    private boolean checkInputs(){
        return true;
    }
}

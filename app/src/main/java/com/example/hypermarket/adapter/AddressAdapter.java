package com.example.hypermarket.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hypermarket.R;
import com.example.hypermarket.database.CustomerAddressModel;
import com.example.hypermarket.databinding.ItemAddressBinding;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {

    private List<CustomerAddressModel> addressList = new ArrayList<>();
    private AppCompatActivity mActivity ;
    private int checkedPosition = 0 ;

    public AddressAdapter(AppCompatActivity mActivity ) {
        this.mActivity = mActivity;
    }

    public AddressAdapter(AppCompatActivity mActivity , List<CustomerAddressModel> addressList) {
        this.mActivity = mActivity;
        this.addressList = addressList;
    }

    public void setAddressList(List<CustomerAddressModel> addressList) {
        this.addressList = addressList;
        notifyDataSetChanged();
    }

    public CustomerAddressModel getSelectedAddress(){
        return addressList.get(checkedPosition);
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Activity activity = (Activity) parent.getContext();
        ItemAddressBinding binding = DataBindingUtil.inflate(activity.getLayoutInflater()
                , R.layout.item_address , parent , false);
        return new AddressHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        CustomerAddressModel address = addressList.get(position);
        holder.bind(address);
    }

    @Override
    public int getItemCount() {
        return addressList == null ? 0 : addressList.size();
    }

    public class AddressHolder extends RecyclerView.ViewHolder {

        private CustomerAddressModel mAddress;
        private ItemAddressBinding mBinding ;

        public AddressHolder(@NonNull ItemAddressBinding itemAddressBinding) {
            super(itemAddressBinding.getRoot());
            mBinding = itemAddressBinding ;
        }

        public void bind(final CustomerAddressModel address) {
            this.mAddress = address;
            if(getAdapterPosition() == checkedPosition)
            {
                mBinding.chkBtn.setChecked(true);
            }
            else
            {
                mBinding.chkBtn.setChecked(false);
            }
            mBinding.chkBtn.setOnCheckedChangeListener((compoundButton, b) -> {
                if(b)
                {
                    checkedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
            mBinding.orderFullName.setText(mAddress.getFirstName());
            mBinding.address.setText(mAddress.getAddress());
        }
    }
}

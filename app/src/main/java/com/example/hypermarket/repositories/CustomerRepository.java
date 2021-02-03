package com.example.hypermarket.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hypermarket.MyApplication;
import com.example.hypermarket.database.CustomerAddressModel;
import com.example.hypermarket.database.RoomDB;
import com.example.hypermarket.model.Customer;
import com.example.hypermarket.model.Order;
import com.example.hypermarket.network.Api;
import com.example.hypermarket.network.RetrofitInstance;
import com.example.hypermarket.utils.Preferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerRepository {

    private static CustomerRepository mInstance;
    private Context mContext;

    private RoomDB roomDB ;

    private MutableLiveData<Customer> mCustomer = new MutableLiveData<>();

    private CustomerRepository(Context mContext) {
        this.mContext = mContext;
        roomDB = MyApplication.getInstance().getRoomDb();
    }

    public static CustomerRepository getInstance(Context context) {
        if (mInstance == null)
            mInstance = new CustomerRepository(context);
        return mInstance;
    }

    public LiveData<List<CustomerAddressModel>> getAllCustomerAddress(){
        return roomDB.customerAddressDao().getAllCustomerAddress(getCustomer().getValue().getId());
    }
    public void inserCustomerAddress (CustomerAddressModel customerAddressModel){
        roomDB.customerAddressDao().insert(customerAddressModel);
    }
    public void deleteCustomerAddress(CustomerAddressModel customerAddressModel){
        roomDB.customerAddressDao().delete(customerAddressModel);
    }
    public void updateCustomerAddress(CustomerAddressModel customerAddressModel){
        roomDB.customerAddressDao().update(customerAddressModel);
    }

    public void registerCustomer(Customer customer) {
        RetrofitInstance.getRetrofit().create(Api.class).registerCustomer(customer).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    mCustomer.setValue(response.body());
                    saveCustomer();
                }
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });
    }

    public void loginCustomer(String email) {
        RetrofitInstance.getRetrofit().create(Api.class).getCustomer(email).enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    mCustomer.setValue(response.body().get(0));
                    saveCustomer();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveCustomer() {
        Preferences.setLoginedCustomer(mContext, mCustomer.getValue());
    }

    public void loadLoginnedCustomer() {
        mCustomer.setValue(Preferences.getLoginedCustomer(mContext));
    }

    public void logoutCustomer() {
        mCustomer.setValue(null);
        saveCustomer();
    }

    public MutableLiveData<Customer> getCustomer() {
        return mCustomer;
    }

    public MutableLiveData<Order> sendOrder(Order order) {
        MutableLiveData<Order> resultOrder = new MutableLiveData<>();
        RetrofitInstance.getRetrofit().create(Api.class).sendOrder(order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    resultOrder.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
        return resultOrder;
    }
}

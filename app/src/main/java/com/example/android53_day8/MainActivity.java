package com.example.android53_day8;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://dummyjson.com/";
    private Retrofit mRetrofit;
    private DummyService dummyServices;

    private BottomNavigationView bottom_nav;
    private ArrayList<Product> mListData;

    private RecyclerView rvDemo;

    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mRetrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
        initData();
        rvDemo = findViewById(R.id.rvDemo);

        productAdapter = new ProductAdapter(mListData,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        rvDemo.setLayoutManager(gridLayoutManager);
        rvDemo.setAdapter(productAdapter);

        initView();

    }

    private void initData() {
        mListData = new ArrayList<>();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        dummyServices = mRetrofit.create(DummyService.class);
        getAllProduct();
        for(Product prd : mListData){
            Log.d("TAG", "initData: "+prd);

        }
        updateData(mListData);
    }

    private void initView() {
        bottom_nav = findViewById(R.id.bottom_nav);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottom_nav, navController);

//        navController.navigate;
        Bundle bundle = new Bundle();
        bundle.putString("ID_PRODUCT", "1");
        navController.navigate(R.id.action_homeFragment_to_productDetailsFragment, bundle);
    }


 private void getAllProduct() {
     dummyServices.getProducts().enqueue(new Callback<ProductResponse>() {
         @Override
         public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
             if(response.isSuccessful()){
                 if(response.code()==200){
                     ProductResponse productResponse = response.body();
                     mListData = (ArrayList<Product>) productResponse.getProducts();
                     updateData(mListData);
//                        Log.d("TAG", "onResponse: " + productResponse);
                 }
             }
         }
         @Override
         public void onFailure(Call<ProductResponse> call, Throwable t) {
             Log.d("TAG", "onFailure: " + t.getMessage());
         }
     });
 }
    private void updateData(ArrayList<Product> mListData) {
        if (productAdapter != null && mListData != null && mListData.size() > 0) {
            productAdapter.updateData(mListData);
        }
    }

    public void demoStream() {
        ArrayList<Product> hotDeals = (ArrayList<Product>) mListData.stream()
                .filter(product -> product.getRating() > 4.0)
                .collect(Collectors.toList());

        ArrayList<Product> orderByPrice = (ArrayList<Product>) mListData.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .collect(Collectors.toList());


    }
}
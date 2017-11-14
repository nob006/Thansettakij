package com.gpk.thansettakij.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpk.thansettakij.R;
import com.gpk.thansettakij.adapter.NewsListAdapter;
import com.gpk.thansettakij.adapter.ProductsListAdapter;
import com.gpk.thansettakij.base.BaseFragment;
import com.gpk.thansettakij.constant.Constant;
import com.gpk.thansettakij.databinding.FragmentNewsBinding;
import com.gpk.thansettakij.http.RestClient;
import com.gpk.thansettakij.model.NewsModel;
import com.gpk.thansettakij.model.ProductModel;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by nobtingtong on 10/28/2017.
 */

public class ProductsFragment extends BaseFragment {

    private FragmentNewsBinding binding;
    private ProductsListAdapter productsListAdapter;
    private LinearLayoutManager layoutManager;

    public static ProductsFragment newFragment() {
        ProductsFragment fragment = new ProductsFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_news , container , false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initObj();
        initView();
        initListener();
        reqProductList();
    }

    private void initObj(){
        layoutManager = new LinearLayoutManager(getContext());
        productsListAdapter = new ProductsListAdapter(getContext());
    }

    private void initView(){
        binding.tvMenuName.setText(getResources().getString(R.string.text_product));
        binding.includeRecyclerView.recyclerView.setLayoutManager(layoutManager);
        binding.includeRecyclerView.recyclerView.setAdapter(productsListAdapter);
    }

    private void initListener(){
        productsListAdapter.setListener(new ProductsListAdapter.onItemClickListener() {
            @Override
            public void onClick(ProductModel data) {
                replaceFragment(WebViewFragment.newFragment(data.getLink() , data.getProduct_name()));
            }
        });
    }

    public void reqProductList(){
        String url = String.format(Constant.API_PRODUCT , Constant.EVENT_ID);
        RequestParams requestParams = new RequestParams();
        RestClient restClient = new RestClient(getContext());
        restClient.get(url, requestParams, new RestClient.RestClientListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<ProductModel>>(){}.getType();
                List<ProductModel> listNews = gson.fromJson(json, type);
                productsListAdapter.setListModel(listNews);

            }

            @Override
            public void onFailure(String message) {
//                showToast(message);
            }
        });
    }



}

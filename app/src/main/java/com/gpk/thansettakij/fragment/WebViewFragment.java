package com.gpk.thansettakij.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.gpk.thansettakij.R;
import com.gpk.thansettakij.base.BaseFragment;
import com.gpk.thansettakij.databinding.FragmentScanQrBinding;
import com.gpk.thansettakij.databinding.FragmentWebviewBinding;

/**
 * Created by nobtingtong on 10/28/2017.
 */

public class WebViewFragment extends BaseFragment {

    private static final String KET_GET_URL = "KET_GET_URL";
    private static final String KET_GET_TITLE = "KET_GET_TITLE";

    private FragmentWebviewBinding binding;
    private String url;
    private String title;

    public static WebViewFragment newFragment(String url , boolean showBackButton) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KET_GET_URL , url);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static WebViewFragment newFragment(String url , String title) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KET_GET_URL , url);
        bundle.putString(KET_GET_TITLE , title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_webview , container , false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initObj();
        initView();
        initListener();
    }
    private void initObj(){
        url = getArguments().getString(KET_GET_URL);
        title = getArguments().getString(KET_GET_TITLE);
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private void initView(){
        binding.tvTitle.setText(title);
        binding.webView.loadUrl(url);
    }

    private void initListener(){
        binding.imgArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }
}

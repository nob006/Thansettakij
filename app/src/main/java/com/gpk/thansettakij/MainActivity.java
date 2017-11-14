package com.gpk.thansettakij;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpk.thansettakij.base.BaseActivity;
import com.gpk.thansettakij.constant.Constant;
import com.gpk.thansettakij.databinding.ActivityMainBinding;
import com.gpk.thansettakij.fragment.BoothFragment;
import com.gpk.thansettakij.fragment.LoginFragment;
import com.gpk.thansettakij.fragment.NewsFragment;
import com.gpk.thansettakij.fragment.ProductsFragment;
import com.gpk.thansettakij.fragment.RegisterFragment;
import com.gpk.thansettakij.fragment.ScanQrFragment;
import com.gpk.thansettakij.fragment.TeamFragment;
import com.gpk.thansettakij.fragment.WebViewFragment;
import com.gpk.thansettakij.http.RestClient;
import com.gpk.thansettakij.model.ActiveModel;
import com.gpk.thansettakij.model.TeamModel;
import com.gpk.thansettakij.pref.CustomerPref;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    private View viewMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main);


        reqActive();
    }

    private void reqActive() {
        String url = String.format(Constant.API_ACTIVE);
        RequestParams requestParams = new RequestParams();
        RestClient restClient = new RestClient(MainActivity.this);
        restClient.get(url, requestParams, new RestClient.RestClientListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                ActiveModel activeModel = gson.fromJson(json, ActiveModel.class);
                Constant.EVENT_ID = activeModel.getEvent_id();
                Constant.EVENT_NAME = activeModel.getEvent_name();
                initView();
                initListener();
            }

            @Override
            public void onFailure(String message) {
//                showToast(message);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DEV" , "onResume");
        checkUserRegister();
    }

    public void checkUserRegister(){
        if (checkRegister()) {
            binding.includeNev.layoutCheckIn.setVisibility(View.VISIBLE);
            binding.includeNev.tvRegister.setVisibility(View.GONE);
        }
        else {
            binding.includeNev.layoutCheckIn.setVisibility(View.GONE);
            binding.includeNev.tvRegister.setVisibility(View.VISIBLE);
        }
    }

    private void initView(){
        viewMenu = binding.includeMenu.layoutMenu;
        binding.eventName.setText(Constant.EVENT_NAME);
        replaceFragment(NewsFragment.newFragment());
    }

    private boolean isExpand = false;
    private void initListener(){
        binding.includeNev.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpand){
                    collapseMenu();
                }
                else {
                    expandMenu();
                }
                isExpand = !isExpand;
            }
        });

        binding.includeMenu.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.rg_news : replaceFragment(NewsFragment.newFragment()); break;
                    case R.id.rg_product : replaceFragment(ProductsFragment.newFragment()); break;
                    case R.id.rg_booth : replaceFragment(BoothFragment.newFragment());break;
                    case R.id.rg_team : replaceFragment(TeamFragment.newFragment()); break;
                    case R.id.rg_about : replaceFragment(WebViewFragment.newFragment(Constant.URL_ABOUT , getResources().getString(R.string.text_about))); break;
                }
                collapseMenu();
            }
        });

        binding.includeNev.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(RegisterFragment.newFragment() , true);
            }
        });

        binding.includeNev.tvCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(ScanQrFragment.newFragment() , true);
            }
        });
    }

    public void expandMenu() {
        final View v = viewMenu;
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public void collapseMenu() {
        final View v = viewMenu;
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}

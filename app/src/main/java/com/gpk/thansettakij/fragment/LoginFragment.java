package com.gpk.thansettakij.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpk.thansettakij.R;
import com.gpk.thansettakij.base.BaseFragment;
import com.gpk.thansettakij.databinding.FragmentLoginBinding;

/**
 * Created by nobtingtong on 10/28/2017.
 */

public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding binding;

    public static LoginFragment newFragment() {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_login , container , false);
        return binding.getRoot();
    }
}

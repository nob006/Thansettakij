package com.gpk.thansettakij.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpk.thansettakij.R;
import com.gpk.thansettakij.model.NewsModel;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by nobtingtong on 10/28/2017.
 */

public class BaseFragment extends Fragment{

    protected void replaceFragment(int id , Fragment fragment){
        FragmentTransaction transaction =  getFragmentManager().beginTransaction();
        transaction.replace(id , fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    protected void replaceFragment(Fragment fragment){
        replaceFragment(R.id.frame_content , fragment);
    }
}

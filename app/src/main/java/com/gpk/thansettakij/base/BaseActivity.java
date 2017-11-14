package com.gpk.thansettakij.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gpk.thansettakij.R;
import com.gpk.thansettakij.pref.CustomerPref;

/**
 * Created by nobtingtong on 10/28/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected void replaceFragment(int id , Fragment fragment){
        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        transaction.replace(id , fragment);
        transaction.commit();
    }
    protected void replaceFragment(Fragment fragment){
        replaceFragment(R.id.frame_content , fragment);
    }

    protected void replaceFragment(Fragment fragment , boolean backStack){
        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content , fragment);
        if (backStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    protected boolean checkRegister(){
        CustomerPref customerPref = new CustomerPref(getApplicationContext());
        return customerPref.getLoginStatus();
    }
}

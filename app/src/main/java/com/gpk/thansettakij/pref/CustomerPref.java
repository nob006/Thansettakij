package com.gpk.thansettakij.pref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nobtingtong on 11/14/2017.
 */

public class CustomerPref {
    private static final String KEY_CUSTOMER_ID = "KEY_CUSTOMER_ID";
    private static final String KEY_LOGIN = "KEY_LOGIN";

    Context context;
    SharedPreferences sharedPerfs;
    SharedPreferences.Editor editor;

    // Prefs Keys
    static String perfsName = "UserHelper";
    static int perfsMode = 0;

    public CustomerPref(Context context) {
        this.context = context;
        this.sharedPerfs = this.context.getSharedPreferences(perfsName, perfsMode);
        this.editor = sharedPerfs.edit();
    }
    public void createSession(String customerId) {
        editor.putBoolean(KEY_LOGIN, true);
        editor.putString(KEY_CUSTOMER_ID, customerId);

        editor.commit();
    }

    public void deleteSession() {
        editor.clear();
        editor.commit();
    }

    public boolean getLoginStatus() {
        return sharedPerfs.getBoolean(KEY_LOGIN, false);
    }
    public String getCustomerId() {
        return sharedPerfs.getString(KEY_CUSTOMER_ID, "1");
    }
}

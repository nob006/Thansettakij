package com.gpk.thansettakij.model;

import org.parceler.Parcel;

/**
 * Created by nobtingtong on 11/13/2017.
 */

@Parcel
public class DataModel {
    int key;
    String value;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.gpk.thansettakij.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gpk.thansettakij.R;

/**
 * Created by Adisorn on 8/30/2017.
 */

public class ImageCaseUtils {
    public static void load(Context context , String url , ImageView imgView){
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.logo_ic)
                .into(imgView);
    }
    public static void load(Context context , String url , ImageView imgView , int defaultImage){
        Glide.with(context)
                .load(url)
                .placeholder(defaultImage)
                .into(imgView);
    }
    public static void load(Context context , int id , ImageView imgView ){
        Glide.with(context)
                .load(id)
                .into(imgView);
    }
}

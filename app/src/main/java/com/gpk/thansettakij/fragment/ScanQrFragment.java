package com.gpk.thansettakij.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.Result;
import com.gpk.thansettakij.R;
import com.gpk.thansettakij.base.BaseFragment;
import com.gpk.thansettakij.constant.Constant;
import com.gpk.thansettakij.databinding.FragmentLoginBinding;
import com.gpk.thansettakij.databinding.FragmentScanQrBinding;
import com.gpk.thansettakij.http.RestClient;
import com.gpk.thansettakij.model.CheckInModel;
import com.gpk.thansettakij.model.TeamModel;
import com.gpk.thansettakij.pref.CustomerPref;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by nobtingtong on 10/28/2017.
 */

public class ScanQrFragment extends BaseFragment implements ZXingScannerView.ResultHandler{

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 999;

    private FragmentScanQrBinding binding;

    public static ScanQrFragment newFragment() {
        ScanQrFragment fragment = new ScanQrFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_scan_qr , container , false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.scanView.setSquareViewFinder(true);
    }

    @Override
    public void onResume() {
        super.onResume();


        requestPermission();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopCamera();
    }

    private void startCamera(){
        binding.scanView.setResultHandler(this);
        binding.scanView.startCamera();
    }

    private void stopCamera(){
        binding.scanView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.d("DEV" ,"QR : " + result.getText());
        reqCheckIn(result.getText());
    }

    private void requestPermission(){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else {
            startCamera();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    startCamera();
                } else {
                    Log.d("Log" , "DEV");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    public void reqCheckIn(String boothId){
        String url = String.format(Constant.API_CHECK_IN ,
                Constant.EVENT_ID ,
                boothId ,
                new CustomerPref(getContext()).getCustomerId());

        RequestParams requestParams = new RequestParams();
        RestClient restClient = new RestClient(getContext());
        restClient.get(url, requestParams, new RestClient.RestClientListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                CheckInModel listNews = gson.fromJson(json, CheckInModel.class);

                Toast.makeText(getContext() , getResources().getString(R.string.text_check_in_success) , Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }

            @Override
            public void onFailure(String message) {
//                showToast(message);
            }
        });
    }
}

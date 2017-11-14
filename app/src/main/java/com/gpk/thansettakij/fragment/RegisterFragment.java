package com.gpk.thansettakij.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpk.thansettakij.MainActivity;
import com.gpk.thansettakij.R;
import com.gpk.thansettakij.adapter.BoothListAdapter;
import com.gpk.thansettakij.base.BaseFragment;
import com.gpk.thansettakij.constant.Constant;
import com.gpk.thansettakij.databinding.FragmentNewsBinding;
import com.gpk.thansettakij.databinding.FragmentRegisterBinding;
import com.gpk.thansettakij.dialog.SelectDialog;
import com.gpk.thansettakij.http.RestClient;
import com.gpk.thansettakij.model.BoothModel;
import com.gpk.thansettakij.model.DataModel;
import com.gpk.thansettakij.model.RegisterModel;
import com.gpk.thansettakij.pref.CustomerPref;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nobtingtong on 10/28/2017.
 */

public class RegisterFragment extends BaseFragment {

    private FragmentRegisterBinding binding;
    private ArrayList<DataModel> profileList;
    private ArrayList<DataModel> educaionList;
    private ArrayList<DataModel> sexList;

    private DataModel profitSelect ;
    private DataModel educationSelect ;
    private DataModel sexSelect ;

    public static RegisterFragment newFragment() {
        RegisterFragment fragment = new RegisterFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_register , container , false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initObj();
        initView();
        initListener();

        reqProfile();
        reqEducation();
        reqSex();
    }

    private void initObj(){

    }

    private void initView(){

    }

    private void initListener(){
        binding.edtIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileList != null){
                    SelectDialog selectDialog = SelectDialog.newDialog(getActivity() ,profileList );
                    selectDialog.show(getFragmentManager() , "");
                    selectDialog.setOnDialogSelect(new SelectDialog.OnDialogSelect() {
                        @Override
                        public void onClick(DataModel data) {
                            binding.edtIncome.setText(data.getValue());
                            profitSelect = data;
                        }
                    });
                }
            }
        });
        binding.edtSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sexList != null){
                    SelectDialog selectDialog = SelectDialog.newDialog(getActivity() ,sexList );
                    selectDialog.show(getFragmentManager() , "");
                    selectDialog.setOnDialogSelect(new SelectDialog.OnDialogSelect() {
                        @Override
                        public void onClick(DataModel data) {
                            binding.edtSex.setText(data.getValue());
                            sexSelect = data;
                        }
                    });
                }
            }
        });
        binding.edtEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (educaionList != null){
                    SelectDialog selectDialog = SelectDialog.newDialog(getActivity() ,educaionList );
                    selectDialog.show(getFragmentManager() , "");
                    selectDialog.setOnDialogSelect(new SelectDialog.OnDialogSelect() {
                        @Override
                        public void onClick(DataModel data) {
                            binding.edtEducation.setText(data.getValue());
                            educationSelect = data;
                        }
                    });
                }
            }
        });

        binding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.edtName.getText().toString().trim();
                String email = binding.edtEmail.getText().toString().trim();
                String tel = binding.edtTel.getText().toString().trim();
                String sex = binding.edtSex.getText().toString().trim();
                String education = binding.edtEducation.getText().toString().trim();
                String company = binding.edtName.getText().toString().trim();
                String income = binding.edtIncome.getText().toString().trim();

                if (validateUserInput(name , email , tel , sex , education , company , income)){
                    reqRegister(name , email , tel , sex , education , company , income);
                }
            }
        });

    }

    private boolean validateUserInput(String name , String email , String tel , String sex , String education , String company , String income){

        if (name.isEmpty()){
            return false;
        }
        else  if (email.isEmpty()){
            return false;
        }
        else  if (tel.isEmpty()){
            return false;
        }
        else  if (sex.isEmpty()){
            return false;
        }
        else  if (education.isEmpty()){
            return false;
        }
        else  if (company.isEmpty()){
            return false;
        }
        else  if (income.isEmpty()){
            return false;
        }

        return true;
    }

    public void reqProfile(){
        String url = String.format(Constant.API_PROFILE);
        RequestParams requestParams = new RequestParams();
        RestClient restClient = new RestClient(getContext());
        restClient.get(url, requestParams, new RestClient.RestClientListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DataModel>>(){}.getType();
                profileList = gson.fromJson(json, type);
            }

            @Override
            public void onFailure(String message) {
//                showToast(message);
            }
        });
    }

    public void reqEducation(){
        String url = String.format(Constant.API_EDUCATION);
        RequestParams requestParams = new RequestParams();
        RestClient restClient = new RestClient(getContext());
        restClient.get(url, requestParams, new RestClient.RestClientListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DataModel>>(){}.getType();
                educaionList = gson.fromJson(json, type);
            }

            @Override
            public void onFailure(String message) {
//                showToast(message);
            }
        });
    }

    public void reqSex(){
        String url = String.format(Constant.API_SEX);
        RequestParams requestParams = new RequestParams();
        RestClient restClient = new RestClient(getContext());
        restClient.get(url, requestParams, new RestClient.RestClientListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<DataModel>>(){}.getType();
                sexList = gson.fromJson(json, type);
            }

            @Override
            public void onFailure(String message) {
//                showToast(message);
            }
        });
    }

    public void reqRegister(String name , String email , String tel , String sex , String education , String company , String income){

        String url = String.format(Constant.API_REGISTER
                ,name
                ,tel
                ,sexSelect.getKey()
                ,educationSelect.getKey()
                ,company
                ,profitSelect.getKey()
                ,email
                ,Constant.EVENT_ID
                , FirebaseInstanceId.getInstance().getToken());
        RequestParams requestParams = new RequestParams();

        RestClient restClient = new RestClient(getContext());
        restClient.get(url, requestParams, new RestClient.RestClientListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                RegisterModel registerModel = gson.fromJson(json, RegisterModel.class);

                CustomerPref customerPref = new CustomerPref(getContext());
                customerPref.createSession(registerModel.getCustomer_id());

                Toast.makeText(getContext() , getResources().getString(R.string.text_register_success) , Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).checkUserRegister();
                getFragmentManager().popBackStack();
            }

            @Override
            public void onFailure(String message) {
//                showToast(message);
            }
        });
    }



}

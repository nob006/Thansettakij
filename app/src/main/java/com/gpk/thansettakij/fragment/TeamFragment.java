package com.gpk.thansettakij.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpk.thansettakij.R;
import com.gpk.thansettakij.adapter.BoothListAdapter;
import com.gpk.thansettakij.adapter.ProductsListAdapter;
import com.gpk.thansettakij.adapter.TeamListAdapter;
import com.gpk.thansettakij.base.BaseFragment;
import com.gpk.thansettakij.constant.Constant;
import com.gpk.thansettakij.databinding.FragmentNewsBinding;
import com.gpk.thansettakij.http.RestClient;
import com.gpk.thansettakij.model.BoothModel;
import com.gpk.thansettakij.model.ProductModel;
import com.gpk.thansettakij.model.TeamModel;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by nobtingtong on 10/28/2017.
 */

public class TeamFragment extends BaseFragment {

    private FragmentNewsBinding binding;
    private TeamListAdapter teamListAdapter;
    private LinearLayoutManager layoutManager;

    public static TeamFragment newFragment() {
        TeamFragment fragment = new TeamFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_news , container , false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initObj();
        initView();
        initListener();
        reqTeamList();
    }

    private void initObj(){
        layoutManager = new LinearLayoutManager(getContext());
        teamListAdapter = new TeamListAdapter(getContext());
    }

    private void initView(){
        binding.tvMenuName.setText(getResources().getString(R.string.text_team));
        binding.includeRecyclerView.recyclerView.setLayoutManager(layoutManager);
        binding.includeRecyclerView.recyclerView.setAdapter(teamListAdapter);
    }

    private void initListener(){
        teamListAdapter.setListener(new TeamListAdapter.onItemClickListener() {
            @Override
            public void onClick(TeamModel data) {
                replaceFragment(WebViewFragment.newFragment(data.getLink() , data.getTeam_name()));
            }
        });
    }


    public void reqTeamList(){
        String url = String.format(Constant.API_TEAMS , Constant.EVENT_ID);
        RequestParams requestParams = new RequestParams();
        RestClient restClient = new RestClient(getContext());
        restClient.get(url, requestParams, new RestClient.RestClientListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<TeamModel>>(){}.getType();
                List<TeamModel> listNews = gson.fromJson(json, type);
                teamListAdapter.setListModel(listNews);

            }

            @Override
            public void onFailure(String message) {
//                showToast(message);
            }
        });
    }



}

package com.gpk.thansettakij.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpk.thansettakij.R;
import com.gpk.thansettakij.adapter.SelectDialogAdapter;
import com.gpk.thansettakij.databinding.DialogSelectBinding;
import com.gpk.thansettakij.databinding.LayoutRecyclerviewBinding;
import com.gpk.thansettakij.model.DataModel;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nobtingtong on 11/13/2017.
 */

public class SelectDialog extends DialogFragment {

    private DialogSelectBinding binding;

    private LinearLayoutManager layoutManager;
    private SelectDialogAdapter selectDialogAdapter;
    private ArrayList<DataModel> dataModels;
    private OnDialogSelect onDialogSelect;

    public static SelectDialog newDialog(Context context , ArrayList<DataModel> dataModels){
        SelectDialog selectDialog = new SelectDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("DDD" , Parcels.wrap(dataModels));
        selectDialog.setArguments(bundle);
        return selectDialog;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Light_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater , R.layout.dialog_select , container , false);

        initObj();
        initView();
        initListener();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initObj(){
        dataModels = Parcels.unwrap(getArguments().getParcelable("DDD"));
        layoutManager = new LinearLayoutManager(getActivity());
        selectDialogAdapter = new SelectDialogAdapter(getActivity());
        selectDialogAdapter.setListModel(dataModels);
    }

    private void initView(){
        binding.includeRecyclerView.recyclerView.setLayoutManager(layoutManager);
        binding.includeRecyclerView.recyclerView.setAdapter(selectDialogAdapter);
    }

    public void initListener(){
        selectDialogAdapter.setListener(new SelectDialogAdapter.onItemClickListener() {
            @Override
            public void onClick(DataModel data) {
                if (onDialogSelect != null) {
                    onDialogSelect.onClick(data);
                }
                dismiss();
            }
        });
    }

    public void setOnDialogSelect(OnDialogSelect onDialogSelect) {
        this.onDialogSelect = onDialogSelect;
    }

    public interface OnDialogSelect{
        public void onClick(DataModel data);
    }

}

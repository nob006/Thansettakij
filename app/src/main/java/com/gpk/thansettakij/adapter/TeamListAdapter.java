package com.gpk.thansettakij.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpk.thansettakij.databinding.ListTextItemBinding;
import com.gpk.thansettakij.model.BoothModel;
import com.gpk.thansettakij.model.TeamModel;

import java.util.List;

/**
 * Created by nobtingtong on 8/31/2017.
 */

public class TeamListAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<TeamModel> listModel;
    private onItemClickListener listener;

    public TeamListAdapter(Context context) {
        this.context = context;
    }

    public void setListModel(List<TeamModel> listModel) {
        this.listModel = listModel;
        notifyDataSetChanged();
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ListTextItemBinding binding;

        public ViewHolder(ListTextItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            final TeamModel data = listModel.get(position);
            binding.tvSubject.setText(data.getTeam_name());
            binding.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.onClick(data);
                    }
                }
            });
            binding.executePendingBindings();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListTextItemBinding binding = ListTextItemBinding.inflate(layoutInflater , parent , false);
        return new TeamListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TeamListAdapter.ViewHolder holder1 = (TeamListAdapter.ViewHolder)holder;
        holder1.bind(position);
    }

    @Override
    public int getItemCount() {
        return (listModel == null) ?  0 : listModel.size();
    }

    public interface onItemClickListener{
        void onClick(TeamModel data);
    }
}

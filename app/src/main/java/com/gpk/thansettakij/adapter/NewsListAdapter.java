package com.gpk.thansettakij.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpk.thansettakij.databinding.ListItemBinding;
import com.gpk.thansettakij.model.NewsModel;
import com.gpk.thansettakij.utils.ImageCaseUtils;

import java.util.List;

/**
 * Created by nobtingtong on 8/31/2017.
 */

public class NewsListAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<NewsModel> listModel;
    private onItemClickListener listener;

    public NewsListAdapter(Context context) {
        this.context = context;
    }

    public void setListModel(List<NewsModel> listModel) {
        this.listModel = listModel;
        notifyDataSetChanged();
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ListItemBinding binding;

        public ViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            final NewsModel data = listModel.get(position);
            binding.tvSubject.setText(data.getTopic());
            binding.tvDetail.setText(data.getIntro());
            ImageCaseUtils.load(context ,data.getPicture() , binding.imgThumb);
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
        ListItemBinding binding = ListItemBinding.inflate(layoutInflater , parent , false);
        return new NewsListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsListAdapter.ViewHolder holder1 = (NewsListAdapter.ViewHolder)holder;
        holder1.bind(position);
    }

    @Override
    public int getItemCount() {
        return (listModel == null) ?  0 : listModel.size();
    }

    public interface onItemClickListener{
        void onClick(NewsModel data);
    }
}

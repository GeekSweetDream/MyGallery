package com.dreamsofpines.mygallery.ui.adapter.delegate;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mygallery.R;
import com.dreamsofpines.mygallery.model.LoadMore;
import com.dreamsofpines.mygallery.ui.interfaces.mItemView;
import com.victor.loading.rotate.RotateLoading;

import java.util.List;

public class LoadMoreDelegate extends AdapterDelegate<List<mItemView>> {

    private LayoutInflater mInflater;

    public interface OnLoadMoreListener{
        void load();
    }

    private OnLoadMoreListener listener;

    public LoadMoreDelegate setListener(OnLoadMoreListener listener) {
        this.listener = listener;
        return this;
    }

    public LoadMoreDelegate(Activity activity) {
        mInflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(List<mItemView> items, int position) {
        return items.get(position) instanceof LoadMore;
    }

    @Override
    protected RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent) {
        return new LoadMoreHolder(mInflater.inflate(R.layout.fragment_lenta_load_item,parent,false));
    }

    @Override
    protected void onBindViewHolder(List<mItemView> items, int position, RecyclerView.ViewHolder holder) {
        LoadMoreHolder loadMoreHolder = (LoadMoreHolder) holder;
        loadMoreHolder.rotationLoad.start();
        listener.load();
    }

    public class LoadMoreHolder extends RecyclerView.ViewHolder {

        public RotateLoading  rotationLoad;

        public LoadMoreHolder(View itemView) {
            super(itemView);
            rotationLoad = itemView.findViewById(R.id.rotation_load);
            rotationLoad.setLoadingColor(itemView.getContext().getResources().getColor(R.color.color_orange));
        }

    }

}

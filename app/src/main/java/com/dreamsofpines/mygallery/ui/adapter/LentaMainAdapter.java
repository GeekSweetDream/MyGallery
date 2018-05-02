package com.dreamsofpines.mygallery.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dreamsofpines.mygallery.ui.adapter.delegate.AdapterDelegateManager;
import com.dreamsofpines.mygallery.ui.adapter.delegate.LentaItemDelegate;
import com.dreamsofpines.mygallery.ui.adapter.delegate.LoadMoreDelegate;
import com.dreamsofpines.mygallery.ui.interfaces.mItemView;

import java.util.ArrayList;
import java.util.List;

public class LentaMainAdapter extends RecyclerView.Adapter {

    public final int SMALL_IMAGE = 0;
    public final int BIG_IMAGE = 1;
    public final int LOAD_BUTTON = 2;


    private AdapterDelegateManager<List<mItemView>> mListAdapterDelegateManager;
    private List<mItemView> items;

    public interface OnClickPhotoListener{
        void OnClick(int position);
    }

    private OnClickPhotoListener photoListener;
    private LoadMoreDelegate.OnLoadMoreListener loadListener;

    public LentaMainAdapter setPhotoListener(OnClickPhotoListener photoListener) {
        this.photoListener = photoListener;
        return this;
    }

    public void setLoadListener(LoadMoreDelegate.OnLoadMoreListener loadListener) {
        this.loadListener = loadListener;
    }

    public LentaMainAdapter(Activity activity){
        items = new ArrayList<>();
        addDelegate(activity);
        setListeners();
    }

    public List<mItemView> getItems() {
        return items;
    }

    public void setItems(List<mItemView> items) {
        this.items = items;
    }

    public LentaMainAdapter(Activity activity, List<mItemView> items){
        this.items = items;
        addDelegate(activity);
        setListeners();
    }

    private void addDelegate(Activity activity){
        mListAdapterDelegateManager = new AdapterDelegateManager<>();
        mListAdapterDelegateManager.addDelegate(SMALL_IMAGE, new LentaItemDelegate(activity));
        mListAdapterDelegateManager.addDelegate(LOAD_BUTTON, new LoadMoreDelegate(activity));
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mListAdapterDelegateManager.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mListAdapterDelegateManager.onBindViewHolder(items,position,holder);
    }

    @Override
    public int getItemViewType(int position) {
        return mListAdapterDelegateManager.getItemViewType(items,position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void setListeners(){
        ((LentaItemDelegate)mListAdapterDelegateManager.getDelegateForViewType(SMALL_IMAGE))
                .setListener(((position)->photoListener.OnClick(position)));
        ((LoadMoreDelegate)mListAdapterDelegateManager.getDelegateForViewType(LOAD_BUTTON))
                .setListener(()-> loadListener.load());
    }
}

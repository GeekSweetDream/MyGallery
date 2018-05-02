package com.dreamsofpines.mygallery.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.dreamsofpines.mygallery.R;
import com.dreamsofpines.mygallery.model.Answer;
import com.dreamsofpines.mygallery.model.LentaImageItem;
import com.dreamsofpines.mygallery.model.LoadMore;
import com.dreamsofpines.mygallery.network.Network;
import com.dreamsofpines.mygallery.ui.adapter.LentaMainAdapter;
import com.dreamsofpines.mygallery.ui.custom.view.MyToolbar;
import com.dreamsofpines.mygallery.ui.interfaces.Notify;
import com.dreamsofpines.mygallery.ui.interfaces.mItemView;
import com.dreamsofpines.mygallery.util.PhotoItemUtilCalback;
import com.dreamsofpines.mygallery.util.SaveImageUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class LentaFragment extends Fragment implements Notify {


    private View view;
    private RecyclerView mRecyclerView;
    private LentaMainAdapter adapter;
    private CompositeDisposable mCompositeDisposable;
    private FragmentManager fm;
    private GalleryPagerFragment gP;
    private GridLayoutManager layoutManager;
    private MyToolbar mMyToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isRefreshData = false;
    private int offset;


    private final static int QUANTITY_DOWNLOAD = 41;
    private final static String SIZE_DOWNLOAD = "M";
    private final static String TAG = "LentaFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lenta,container,false);

        mMyToolbar = view.findViewById(R.id.toolbar);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);

        mCompositeDisposable = new CompositeDisposable();

        mMyToolbar.setOnClickLeftButton(()->{
            Toast.makeText(getContext(),R.string.menu_disable,Toast.LENGTH_SHORT).show();
        });

        mSwipeRefreshLayout.setOnRefreshListener(()->{
            mSwipeRefreshLayout.setRefreshing(true);
            offset = 0;
            isRefreshData = true;
            getInformation();
        });

        offset = 0;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SaveImageUtils.addSub(TAG,this);
    }

    private void getInformation(){
        Log.i(TAG,"Сreated GET query");
        mCompositeDisposable.clear();
        mCompositeDisposable.add(Network.getUriIntance(getActivity().getApplicationContext())
                .images(String.valueOf(QUANTITY_DOWNLOAD),String.valueOf(offset),SIZE_DOWNLOAD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(answer->updateUI(answer),err-> errorInfo(err))
        );
    }


    private void updateUI(Answer answer){
        mSwipeRefreshLayout.setRefreshing(false);
        if(null == adapter) {
            adapter = new LentaMainAdapter(getActivity());
            mRecyclerView.setAdapter(adapter);
        }
        List<mItemView> nlist = getNewList(answer);
        updateItemAdapter(nlist,adapter);
        setSpanSizeLookup(nlist.size());
        setAdapterListeners(nlist);
        offset += QUANTITY_DOWNLOAD;
        Log.i(TAG,"Data is get and adapter is update");
    }

    private void errorInfo(Throwable err){
        mSwipeRefreshLayout.setRefreshing(false);
        isRefreshData = false;
        if (err instanceof HttpException) {
            HttpException response = (HttpException) err;
            Log.i(TAG,"Http error code: "+response.code());
            Toast.makeText(getActivity().getApplicationContext(), R.string.download_error,
                    Toast.LENGTH_LONG).show();
        }else{
            err.printStackTrace();
        }
        Log.i(TAG, "get error: "+err.getMessage());
    }


    private void updateItemAdapter(List<mItemView> newList, LentaMainAdapter adapter){
        PhotoItemUtilCalback calback = new PhotoItemUtilCalback(adapter.getItems(),newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(calback,false);
        adapter.setItems(newList);
        result.dispatchUpdatesTo(adapter);
    }

    private List<mItemView> getNewList(Answer answer){
        List<mItemView> nlist = new ArrayList<>();
        if(!isRefreshData) nlist.addAll(adapter.getItems());
        if(nlist.size()>0) nlist.remove(nlist.size()-1);
        nlist.addAll(answer.getList());
        isRefreshData = false;
        mMyToolbar.setQuantityPhoto(nlist.size());
        if(answer.getList().size()>0) nlist.add(new LoadMore());
        return nlist;
    }


    private void setAdapterListeners(List<mItemView> nlist){
        adapter.setPhotoListener((position)->{
            gP = new GalleryPagerFragment();
            gP.setList(nlist).setPosition(position).setFragmentManager(fm);
            fm.beginTransaction()
                    .add(R.id.main_frame_layout, gP)
                    .addToBackStack("gP")
                    .commit();
             }).setLoadListener(()->getInformation());
    }

    private void setSpanSizeLookup(int size){
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == size-1 && adapter.getItemViewType(position) == adapter.LOAD_BUTTON)
                    return 3;
                return 1;
            }
        });

    }

    public void setFragmentManager(FragmentManager fm){
        this.fm = fm;
    }

    @Override
    public void onStart() {
        super.onStart();
        getInformation();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCompositeDisposable.clear();
        SaveImageUtils.removeSub(TAG);
    }

    @Override
    public void notify(int oldSize, int newSize) {
        if(newSize == 0) {
            mMyToolbar.showMainToolbar().setOnClickLeftButton(()->{
                Toast.makeText(getContext(),R.string.menu_disable,Toast.LENGTH_SHORT).show();
            });
            updateItemAdapter(adapter.getItems(),adapter);
        }else if(oldSize == 0) {
            mMyToolbar.showSelectToolbar()
                    .setSelectPhoto(newSize)
                    .setOnClickRightButton(()->Toast.makeText(getContext(),
                            R.string.function_disable,Toast.LENGTH_LONG).show())
                    .setOnClickLeftButton(()-> SaveImageUtils.clearAll());
        }else{
            mMyToolbar.setSelectPhoto(newSize);
        }
    }


}

package com.dreamsofpines.mygallery.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dreamsofpines.mygallery.R;
import com.dreamsofpines.mygallery.model.Answer;
import com.dreamsofpines.mygallery.model.LoadMore;
import com.dreamsofpines.mygallery.network.Network;
import com.dreamsofpines.mygallery.ui.adapter.LentaMainAdapter;
import com.dreamsofpines.mygallery.ui.interfaces.mItemView;
import com.dreamsofpines.mygallery.util.PhotoItemUtilCalback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class LentaFragment extends Fragment {


    private View view;
    private RecyclerView mRecyclerView;
    private LentaMainAdapter adapter;
    private CompositeDisposable mCompositeDisposable;
    private FragmentManager fm;
    private GalleryPagerFragment gP;
    private GridLayoutManager layoutManager;
    private int offset;

    private final static String TAG = "LentaFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lenta,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        offset = 0;
        layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        mCompositeDisposable = new CompositeDisposable();
        mRecyclerView.setLayoutManager(layoutManager);
        return view;
    }


    private void getInformation(){
        Log.i(TAG,"Сreated GET query");
        mCompositeDisposable.clear();
        mCompositeDisposable.add(Network.getUriIntance().images(String.valueOf(21),String.valueOf(offset),"M")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(answer->{
                    if(null == adapter) {
                        adapter = new LentaMainAdapter(getActivity());
                        mRecyclerView.setAdapter(adapter);
                    }
                    List<mItemView> nlist = getNewList(answer);
                    updateItemAdapter(nlist,adapter);
                    setSpanSizeLookup(nlist.size());
                    setAdapterListeners(nlist);
                    offset += 21;
                    Log.i(TAG,"Data is get and adapter is update");
                },err-> {
                    if (err instanceof HttpException) {
                        HttpException response = (HttpException) err;
                        int code = response.code();
                        Toast.makeText(getActivity().getApplicationContext(), String.valueOf(code), Toast.LENGTH_LONG).show();
                    }else{
                        err.printStackTrace();
                    }
                    Log.i(TAG, "get error: "+err.getMessage());
                })
        );
    }

    // Добавил код
    private void updateItemAdapter(List<mItemView> newList, LentaMainAdapter adapter){
        PhotoItemUtilCalback calback = new PhotoItemUtilCalback(adapter.getItems(),newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(calback,false);
        adapter.setItems(newList);
        result.dispatchUpdatesTo(adapter);
    }

    private List<mItemView> getNewList(Answer answer){
        List<mItemView> nlist = new ArrayList<>(adapter.getItems());
        if(nlist.size()>0) nlist.remove(nlist.size()-1);
        nlist.addAll(answer.getList());
        nlist.add(new LoadMore());
        return nlist;
    }

    private void setAdapterListeners(List<mItemView> nlist){
        adapter.setPhotoListener(position ->{
            gP = new GalleryPagerFragment();
            gP.setList(nlist).setPosition(position);
            fm.beginTransaction()
                    .add(R.id.main_frame_layout, gP)
                    .addToBackStack("gP")
                    .commit();
             }).setLoadListener(()-> getInformation());
    }

    private void setSpanSizeLookup(int size){
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == size-1) return 3;
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
    }
}

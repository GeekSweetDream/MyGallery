package com.dreamsofpines.mygallery.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dreamsofpines.mygallery.R;
import com.dreamsofpines.mygallery.network.Network;
import com.dreamsofpines.mygallery.ui.custom.view.MyToolbar;
import com.dreamsofpines.mygallery.ui.interfaces.ImageItem;
import com.github.chrisbanes.photoview.PhotoView;
import com.victor.loading.rotate.RotateLoading;

public class GalleryItemFragment extends Fragment {

    private PhotoView mImageView;
    private RotateLoading mRotateLoading;
    private View view;
    private OnChangeBackgroundActiveListener lisener;

    public interface OnChangeBackgroundActiveListener{
        void change();
    }

    public void setOnChangeAlphaListener(OnChangeBackgroundActiveListener lisener){
        this.lisener = lisener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery_item,container,false);

        bindView();

        mRotateLoading.setLoadingColor(getContext().getResources().getColor(R.color.color_orange));
        mRotateLoading.start();

        Bundle bundle = getArguments();

        String uri = bundle.getString("url");
        //String preview = bundle.getString("preview");
        Glide.with(getContext())
                .load(Network.getUrlForLoadImage(uri))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(getRequestListener())
                .into(mImageView);
        mImageView.setOnClickListener(view1 -> lisener.change());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void bindView(){
        mImageView = (PhotoView) view.findViewById(R.id.item_image_view);
        mRotateLoading = (RotateLoading) view.findViewById(R.id.rotation_load);
    }

    private RequestListener<GlideUrl,GlideDrawable> getRequestListener(){
        return new RequestListener<GlideUrl, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, GlideUrl model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, GlideUrl model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                mRotateLoading.stop();
                return false;
            }
        };
    }

}

package com.dreamsofpines.mygallery.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dreamsofpines.mygallery.R;
import com.dreamsofpines.mygallery.network.Network;
import com.dreamsofpines.mygallery.network.Url;
import com.dreamsofpines.mygallery.ui.custom.view.MyViewPager;
import com.dreamsofpines.mygallery.ui.interfaces.ImageItem;
import com.dreamsofpines.mygallery.ui.interfaces.mItemView;

import java.util.List;

public class GalleryPagerFragment extends Fragment {


    private MyViewPager mViewPager;

    private PagerAdapter mPagerAdapter;

    private List<mItemView> list;
    private int COUNT_PAGE = 0;
    private int currentPosition = 0;

    private int COLOR_BLACK;
    private int COLOR_WHITE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery,container,false);

        COLOR_BLACK = getResources().getColor(R.color.color_black);
        COLOR_WHITE = getResources().getColor(R.color.color_white);

        mViewPager = (MyViewPager) view.findViewById(R.id.gallery_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentPosition);
        return view;
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private boolean isBackgroundActive = true;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            GalleryItemFragment item = new GalleryItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url",((ImageItem)list.get(position)).getPath());
            item.setArguments(bundle);
            item.setOnChangeAlphaListener(() -> {
                isBackgroundActive = !isBackgroundActive;
                mViewPager.setBackgroundColor(isBackgroundActive?COLOR_WHITE:COLOR_BLACK);
            });
            return item;
        }

        @Override
        public int getCount() {
            return COUNT_PAGE;
        }

    }

    public GalleryPagerFragment setList(List<mItemView> list) {
        this.list = list;
        COUNT_PAGE = list.size();
        return this;
    }

    public GalleryPagerFragment setPosition(int position){
        currentPosition = position;
        return this;
    }

}

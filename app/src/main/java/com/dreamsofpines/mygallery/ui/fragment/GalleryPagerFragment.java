package com.dreamsofpines.mygallery.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dreamsofpines.mygallery.R;
import com.dreamsofpines.mygallery.network.Network;
import com.dreamsofpines.mygallery.network.Url;
import com.dreamsofpines.mygallery.ui.custom.view.MyToolbar;
import com.dreamsofpines.mygallery.ui.custom.view.MyViewPager;
import com.dreamsofpines.mygallery.ui.interfaces.ImageItem;
import com.dreamsofpines.mygallery.ui.interfaces.mItemView;

import java.util.List;

public class GalleryPagerFragment extends Fragment implements ViewPager.OnPageChangeListener{


    private MyViewPager mViewPager;
    private MyToolbar mMyToolbar;

    private PagerAdapter mPagerAdapter;

    private List<mItemView> list;

    private FragmentManager fm;

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

        mMyToolbar = (MyToolbar) view.findViewById(R.id.toolbar);
        mMyToolbar.initCounter(currentPosition+1,COUNT_PAGE);
        mMyToolbar.setOnClickLeftButton(()-> {
            if(fm != null) fm.popBackStack();
        });
        mViewPager = (MyViewPager) view.findViewById(R.id.gallery_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentPosition);
        mViewPager.addOnPageChangeListener(this);

        return view;
    }

    public GalleryPagerFragment setFragmentManager(FragmentManager fm){
        this.fm = fm;
        return this;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        mMyToolbar.updateCounter(position+1);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}



    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter  {

        private boolean isBackgroundActive = true;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            GalleryItemFragment item = new GalleryItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url",((ImageItem)list.get(position)).getPath());
            bundle.putString("preview",((ImageItem)list.get(position)).getPreview());
            item.setArguments(bundle);
            item.setOnChangeAlphaListener(() -> {
                isBackgroundActive = !isBackgroundActive;
                mViewPager.setBackgroundColor(isBackgroundActive?COLOR_WHITE:COLOR_BLACK);
                mMyToolbar.changeColor();
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
        COUNT_PAGE = list.size()-1;
        return this;
    }

    public GalleryPagerFragment setPosition(int position){
        currentPosition = position;
        return this;
    }

}

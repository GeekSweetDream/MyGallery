package com.dreamsofpines.mygallery.util;

import android.support.v7.util.DiffUtil;

import com.dreamsofpines.mygallery.model.LentaImageItem;
import com.dreamsofpines.mygallery.ui.interfaces.mItemView;

import java.util.List;

public class PhotoItemUtilCalback extends DiffUtil.Callback {

    private final List<mItemView> oldList;
    private final List<mItemView> newList;

    public PhotoItemUtilCalback(List<mItemView> oldList,List<mItemView> newList ){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        if(isLentaImageSmall(oldList.get(oldItemPosition))
                && isLentaImageSmall(newList.get(newItemPosition))){
            return true;
        }
        return false;
    }


    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if(((LentaImageItem) oldList.get(oldItemPosition)).getPath()
                .equals(((LentaImageItem) newList.get(newItemPosition)).getPath())){
            return true;
        }
        return false;
    }

    private static boolean isLentaImageSmall(mItemView view){
        return view instanceof LentaImageItem;
    }


}

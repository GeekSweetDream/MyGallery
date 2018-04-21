package com.dreamsofpines.mygallery.model;

import android.app.Fragment;

import com.dreamsofpines.mygallery.ui.interfaces.ImageItem;
import com.dreamsofpines.mygallery.ui.interfaces.mItemView;

public class LentaImageItem extends Fragment implements mItemView,ImageItem {

    private String path;
    private String preview;
    private String Date;
    private boolean isSelect;

    public LentaImageItem() {
        isSelect = false;
    }

    public String getPath() {
        return path;
    }

    public LentaImageItem setPath(String path) {
        this.path = path;
        return this;
    }

    public String getPreview() {
        return preview;
    }

    public LentaImageItem setPreview(String preview) {
        this.preview = preview;
        return this;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}

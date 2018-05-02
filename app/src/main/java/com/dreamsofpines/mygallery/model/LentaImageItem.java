package com.dreamsofpines.mygallery.model;

import android.app.Fragment;

import com.dreamsofpines.mygallery.ui.adapter.delegate.LentaItemDelegate;
import com.dreamsofpines.mygallery.ui.interfaces.ImageItem;
import com.dreamsofpines.mygallery.ui.interfaces.Notify;
import com.dreamsofpines.mygallery.ui.interfaces.mItemView;

public class LentaImageItem extends Fragment implements mItemView,ImageItem {

    private String path;
    private String name;
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

    public LentaImageItem setDate(String date) {
        Date = date;
        return this;
    }

    public String getName() {
        return name;
    }

    public LentaImageItem setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public LentaImageItem setSelect(boolean select) {
        isSelect = select;
        return this;
    }
}

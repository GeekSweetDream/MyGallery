package com.dreamsofpines.mygallery.model;

import com.dreamsofpines.mygallery.ui.interfaces.mItemView;

import java.util.ArrayList;
import java.util.List;

public class Answer {

    private List<mItemView> list;
    private int offset;

    public Answer() {
        list = new ArrayList<>();
        offset = 0;
    }

    public List<mItemView> getList() {
        return list;
    }

    public Answer setList(List<mItemView> list) {
        this.list = list;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public Answer setOffset(int offset) {
        this.offset = offset;
        return this;
    }
}

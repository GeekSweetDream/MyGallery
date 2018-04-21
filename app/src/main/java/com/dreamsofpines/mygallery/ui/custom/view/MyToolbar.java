package com.dreamsofpines.mygallery.ui.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamsofpines.mygallery.R;

public class MyToolbar extends RelativeLayout {

    private View rootView;
    private TextView title;
    private ImageView leftBut;

    public MyToolbar(Context context) {
        super(context);
        init(context);
    }

    public MyToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        rootView = inflate(context, R.layout.toolbar,this);
    }


}

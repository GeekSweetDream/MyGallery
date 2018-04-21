package com.dreamsofpines.mygallery.ui.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
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
    private int design;
    private int quantityPhoto = 0;
    private int currentPhoto = 0;
    private OnClickLeftButton listener;
    private boolean isDarkColor = false;


    public interface OnClickLeftButton {
        void onClick();
    }

    public void setOnClickLeftButton(OnClickLeftButton listener){
        this.listener = listener;
    }

    public MyToolbar(Context context) {
        super(context);
        design = 0;
        init(context);
    }

    public MyToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.MyToolbar);
        design = typedArray.getInt(R.styleable.MyToolbar_design,0);
        typedArray.recycle();
        init(context);
    }

    public MyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.MyToolbar);
        design = typedArray.getInt(R.styleable.MyToolbar_design,0);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        bindView(context);
        setListeners();
        updateTitle();
        setViewButton();
        setBackgroundColor(getResources().getColor(R.color.color_white));
    }

    private void bindView(Context context){
        rootView = inflate(context, (design == 0 ? R.layout.main_toolbar : R.layout.gallery_toolbar), this);
        leftBut = (ImageView) rootView.findViewById(R.id.left_but);
        title = (TextView) rootView.findViewById(R.id.title_toolbar);
    }

    private void setListeners(){
        leftBut.setOnClickListener((view)->{
            if(listener!=null) listener.onClick();});
    }

    public void initCounter(int currentPhoto, int quantityPhoto){
        this.quantityPhoto = quantityPhoto;
        this.currentPhoto = currentPhoto;
        updateTitle();
    }

    public void updateCounter(int currentPhoto){
        this.currentPhoto = currentPhoto;
        updateTitle();
    }

    public void updateTitle(){
        title.setText(getTitleRange());
    }

    private String getTitleRange(){
        if(design == 0){
            return "Фотограм";
        }else{
            return String.valueOf(currentPhoto) + " из " + String.valueOf(quantityPhoto);
        }
    }

    private void setViewButton(){
        leftBut.setImageResource(getButtonIdIcon());
    }

    private int getButtonIdIcon(){
        if(design == 0){
            return isDarkColor?R.mipmap.ic_menu_black_48dp:R.mipmap.ic_menu_black_48dp;
        }else{
            return isDarkColor?R.mipmap.ic_launcher_round:R.mipmap.ic_launcher_round;
        }
    }

    public void changeColor(){
        isDarkColor = !isDarkColor;
        setBackgroundColor(getResources().getColor(!isDarkColor ? R.color.color_white : R.color.color_dark_toolbar));
        title.setTextColor(getResources().getColor(isDarkColor ? R.color.color_white : R.color.color_grey_fonts));
        setViewButton();

    }



}

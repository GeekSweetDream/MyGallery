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
    private ImageView rightBut;
    private int design;
    private int quantityPhoto = 0;
    private int currentPhoto = 0;
    private int selectPhoto = 0;
    private OnClickLeftButton listenerLf;
    private OnClickRightButton listenerRg;
    private boolean isDarkColor = false;

    public interface OnClickLeftButton {
        void onClick();
    }

    public interface OnClickRightButton{
        void onClick();
    }



    public MyToolbar setOnClickLeftButton(OnClickLeftButton listener){
        this.listenerLf = listener;
        return this;
    }
    public MyToolbar setOnClickRightButton(OnClickRightButton listener){
        this.listenerRg = listener;
        return this;
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
        rootView = bindView(context);
        setListeners();
        updateTitle();
        setViewButton();
        setBackgroundColor(getResources().getColor(R.color.color_white));
    }

    private View bindView(Context context){
        View view = inflate(context, getLayoutToolbar(), this);
        leftBut = (ImageView) view.findViewById(R.id.left_but);
        title = (TextView) view.findViewById(R.id.title_toolbar);
        if(design == 2) rightBut = (ImageView) view.findViewById(R.id.right_but);
        return view;
    }

    private int getLayoutToolbar(){
        switch (design){
            case 0: return R.layout.main_toolbar;
            case 1: return R.layout.gallery_toolbar;
            case 2: return R.layout.main_toolbar;
        }
        return 0;
    }

    private void setListeners(){
        leftBut.setOnClickListener((view)->{
            if(listenerLf!=null) listenerLf.onClick();
        });

        if(design == 2) rightBut.setOnClickListener((view)->{
            if(listenerRg != null) listenerRg.onClick();
        });
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
        String answer = "";
        switch (design){
            case 0:{
                answer = "Фотограм";
                break;
            }
            case 1: {
                answer = String.valueOf(currentPhoto) + " из " + String.valueOf(quantityPhoto);
                break;
            }
            case 2: {
                answer = String.valueOf(selectPhoto) + " из " + String.valueOf(quantityPhoto);
                break;
            }

        }
        return answer;
    }

    private void setViewButton(){
        leftBut.setImageResource(getButtonIdIcon());
    }

    private int getButtonIdIcon(){
        if(design == 0){
            return isDarkColor?R.mipmap.ic_menu_black_48dp:R.mipmap.ic_menu_black_48dp;
        }else{
            return isDarkColor?R.mipmap.ic_arrow_back_white_48dp:R.mipmap.ic_arrow_back_black_48dp;
        }
    }

    public void changeColor(){
        isDarkColor = !isDarkColor;
        setBackgroundColor(getResources().getColor(!isDarkColor ? R.color.color_white : R.color.color_dark_toolbar));
        title.setTextColor(getResources().getColor(isDarkColor ? R.color.color_white : R.color.color_grey_fonts));
        setViewButton();
    }

    public MyToolbar setQuantityPhoto(int quantityPhoto){
        this.quantityPhoto = quantityPhoto;
        updateTitle();
        return this;
    }

    public MyToolbar setSelectPhoto(int selectPhoto){
        this.selectPhoto = selectPhoto;
        updateTitle();
        return this;
    }

    public int getSelectPhoto() {
        return selectPhoto;
    }

    public MyToolbar showSelectToolbar(){
        design = 2;
        rightBut = (ImageView) rootView.findViewById(R.id.right_but);
        rightBut.setVisibility(VISIBLE);
        leftBut.setImageResource(R.mipmap.ic_arrow_back_black_48dp);
        setListeners();
        return this;
    }

    public MyToolbar showMainToolbar(){
        design = 0;
        updateTitle();
        rightBut.setVisibility(GONE);
        leftBut.setImageResource(R.mipmap.ic_menu_black_48dp);
        return this;
    }

}

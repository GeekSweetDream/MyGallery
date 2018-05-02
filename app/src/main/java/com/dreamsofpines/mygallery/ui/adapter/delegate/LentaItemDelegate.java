package com.dreamsofpines.mygallery.ui.adapter.delegate;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.dreamsofpines.mygallery.R;
import com.dreamsofpines.mygallery.model.LentaImageItem;
import com.dreamsofpines.mygallery.network.Network;
import com.dreamsofpines.mygallery.ui.interfaces.mItemView;
import com.dreamsofpines.mygallery.util.SaveImageUtils;
import com.dreamsofpines.mygallery.util.ScreenUtils;

import java.util.List;

public class LentaItemDelegate extends AdapterDelegate<List<mItemView>> {

    private LayoutInflater inflater;
    private Context mContext;
    private Activity mActivity;

    private OnSmallImageClickListener mListener;

    public interface OnSmallImageClickListener{
        void OnClick(int position);
    }

    public LentaItemDelegate setListener(OnSmallImageClickListener listener){
        this.mListener = listener;
        return this;
    }

    public LentaItemDelegate(Activity activity){
        inflater = activity.getLayoutInflater();
        mContext = activity.getApplicationContext();
    }

    @Override
    protected boolean isForViewType(List<mItemView> items, int position) {
        return items.get(position) instanceof LentaImageItem;
    }

    @Override
    protected RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent) {
        return new LentaSmallHolder(inflater.inflate(R.layout.item_small_image,parent,false));
    }

    @Override
    protected void onBindViewHolder(List<mItemView> items, int position, RecyclerView.ViewHolder holder) {
        LentaSmallHolder lentaSmallHolder = (LentaSmallHolder) holder;
        LentaImageItem itemSmall = (LentaImageItem) items.get(position);
        Glide.with(mContext).load(Network.getUrlForLoadImage(itemSmall.getPreview())).into(lentaSmallHolder.mImageView);
        lentaSmallHolder.itemView.setOnLongClickListener((view) -> {
            if(itemSmall.isSelect()){
                SaveImageUtils.removeSelect(itemSmall);
            }else{
                SaveImageUtils.addSelect(itemSmall);
            }
            itemSmall.setSelect(!itemSmall.isSelect());
            selectImage(lentaSmallHolder,itemSmall);
            return true;
        });
        lentaSmallHolder.itemView.setOnClickListener((view)->mListener.OnClick(position));
        selectImage(lentaSmallHolder,itemSmall);
        setImageSize(lentaSmallHolder);
    }

    private void selectImage(LentaSmallHolder lentaSmallHolder, LentaImageItem itemSmall){
        lentaSmallHolder.relSelect
                .setBackgroundColor(mContext.getResources()
                        .getColor(itemSmall.isSelect()?R.color.color_orange:R.color.color_main_background));
    }

    private void setImageSize(LentaSmallHolder holder){
        holder.mImageView.getLayoutParams().width = (ScreenUtils.SCREEN_WIDTH-13)/3;
        holder.mImageView.getLayoutParams().height = (ScreenUtils.SCREEN_WIDTH-13)/3;
    }

    public class LentaSmallHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public RelativeLayout relSelect;

        public LentaSmallHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.photo_img);
            relSelect = (RelativeLayout) itemView.findViewById(R.id.select_photo);
        }

    }

}

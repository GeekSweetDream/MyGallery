package com.dreamsofpines.mygallery.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class ImageUtils {

    public static void blur(ImageView imageView) {
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                bluring(bitmap,imageView);
                return false;
            }
        });
    }

    private static void bluring(Bitmap bitmap, ImageView imageView){
        float radius = 20;
        Context context = imageView.getContext();

        Bitmap overlay = Bitmap.createBitmap((int) (imageView.getMeasuredWidth()),
                (int) (imageView.getMeasuredHeight()), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-imageView.getLeft(), -imageView.getTop());
        canvas.drawBitmap(bitmap, 0, 0, null);
        RenderScript rs= RenderScript.create(context);

        Allocation overlayAlloc = Allocation.createFromBitmap(
                rs, overlay);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
                rs, overlayAlloc.getElement());

        blur.setInput(overlayAlloc);

        blur.setRadius(radius);

        blur.forEach(overlayAlloc);

        overlayAlloc.copyTo(overlay);

        imageView.setBackground(new BitmapDrawable(
                context.getResources(), overlay));

        rs.destroy();
    }



}

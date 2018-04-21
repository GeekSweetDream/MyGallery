package com.dreamsofpines.mygallery.util;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenUtils {

    public static int SCREEN_WIDTH;

    public static void init(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SCREEN_WIDTH = displayMetrics.widthPixels;
    }

}

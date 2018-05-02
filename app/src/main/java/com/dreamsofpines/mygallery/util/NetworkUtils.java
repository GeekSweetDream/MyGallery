package com.dreamsofpines.mygallery.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkUtils {

    public static boolean hasConection(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return isConnected(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI))
                || isConnected(cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE))
                || isConnected(cm.getActiveNetworkInfo());
    }

    private static boolean isConnected(NetworkInfo info){
        return (info != null) && info.isConnected();
    }
}

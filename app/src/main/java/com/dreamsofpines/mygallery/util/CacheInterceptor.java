package com.dreamsofpines.mygallery.util;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheInterceptor implements Interceptor {

    private final Context context;

    public CacheInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        String cacheHeaderValue = (context)
//                ? "public, max-age=2419200"
//                : "public, only-if-cached, max-stale=2419200";
        return null;
    }
}

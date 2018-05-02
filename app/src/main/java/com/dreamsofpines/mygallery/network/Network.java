package com.dreamsofpines.mygallery.network;

import android.content.Context;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.dreamsofpines.mygallery.model.Answer;
import com.dreamsofpines.mygallery.model.deserializer.AnswerDeserializer;
import com.dreamsofpines.mygallery.util.CacheInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Network {

    private static Url urlIntance;
    private final static String YANDEX_TOKEN = "AQAAAAAmYFGaAADLW3lLUSFPmU0omVfULzOTssQ";
    private static final int TIMEOUT_SECOND = 10;

    private static Gson createGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Answer.class, new AnswerDeserializer());
        return gsonBuilder.create();
    }

    private static Cache getCache(Context context){
        File httpCacheDirectory = new File(context.getCacheDir(),"responses");
        int cacheSize = 10*1024*1024;
        return new Cache(httpCacheDirectory,cacheSize);
    }



    private static OkHttpClient getHttpClient(Context context){
        OkHttpClient.Builder client = new OkHttpClient().newBuilder();
        client.addNetworkInterceptor(CacheInterceptor.getOnlineInterceptor(context));
        client.addInterceptor(CacheInterceptor.getOfflineInterceptor(context));
        client.connectTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS);
        client.readTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS);
        client.cache(getCache(context));
        return client.build();
    }


    private static Retrofit createRetrofit(Context context){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Url.HOST)
                .client(getHttpClient(context))
                .build();

    }

    public static GlideUrl getUrlForLoadImage(String path){
         return new GlideUrl(path, new LazyHeaders.Builder()
                        .addHeader("Authorization","OAuth "+YANDEX_TOKEN)
                        .build());
    }

    public static Url getUriIntance(Context context){
        if(urlIntance == null){
            urlIntance = createRetrofit(context).create(Url.class);
        }
        return urlIntance;
    }

}

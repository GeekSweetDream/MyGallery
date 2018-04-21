package com.dreamsofpines.mygallery.network;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.dreamsofpines.mygallery.model.Answer;
import com.dreamsofpines.mygallery.model.deserializer.AnswerDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Network {

    private static Url urlIntance;
    private final static  String YANDEX_TOKEN = "AQAAAAADworZAADLWyEX80SXnkjCmRCa7zOEKzA";

    private static Gson createGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Answer.class, new AnswerDeserializer());
        return gsonBuilder.create();
    }

    private static Retrofit createRetrofit(){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Url.HOST)
                .build();

    }

    public static GlideUrl getUrlForLoadImage(String path){
        GlideUrl url = new GlideUrl(path,
                new LazyHeaders.Builder()
                        .addHeader("Authorization","OAuth "+YANDEX_TOKEN)
                        .build());
        return url;
    }

    public static Url getUriIntance(){
        if(urlIntance == null){
            urlIntance = createRetrofit().create(Url.class);
        }
        return urlIntance;
    }

}

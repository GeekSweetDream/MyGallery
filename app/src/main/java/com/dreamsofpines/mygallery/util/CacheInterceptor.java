package com.dreamsofpines.mygallery.util;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

public class CacheInterceptor {

    /**
     * Validate cache, return stream. Return cache if no network.
     * @param context
     * @return
     */
    public static Interceptor getOnlineInterceptor(final Context context){
        return (chain)->{
                Response response = chain.proceed(chain.request());
                String headers = response.header("Cache-Control");
                if(NetworkUtils.hasConection(context) && (headers == null ||
                        headers.contains("no-store") || headers.contains("must-revalidate") ||
                        headers.contains("no-cache") || headers.contains("max-age=0"))){
                    return response.newBuilder()
                            .header("Cache-Control", "public, max-age=600")
                            .build();
                } else{
                    return response;
                }
            };
    }

    /**
     * Get me cache.
     * @param context
     * @return
     */
    public static Interceptor getOfflineInterceptor(final Context context){
        return (chain) -> {
                Request request = chain.request();
                if(!NetworkUtils.hasConection(context)){
                    request = request.newBuilder()
                            .header("Cache-Control", "public, only-if-cached")
                            .build();
                }

                return chain.proceed(request);
            };
    }


}

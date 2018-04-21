package com.dreamsofpines.mygallery.network;

import com.dreamsofpines.mygallery.model.Answer;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Url {

    String HOST = "https://cloud-api.yandex.net/v1/disk/resources/";
    String GET_IMAGES = "files?fields=items.file%2Citems.preview%2Citems.created&media_type=image%20";

    @Headers({"Authorization:OAuth AQAAAAADworZAADLWyEX80SXnkjCmRCa7zOEKzA"})
    @GET(GET_IMAGES) Observable<Answer> images(@Query("limit") String limit, @Query("offset") String offset, @Query("preview_size") String size);


}

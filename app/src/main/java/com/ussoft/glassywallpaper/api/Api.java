package com.ussoft.glassywallpaper.api;

import com.ussoft.glassywallpaper.dataclasses.WallpaperData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Api {

    @GET("curated")
    Call<WallpaperData> getWallpaper(
            @Header("Authorization") String credientials,
            @Query("page") int pageCount,
            @Query("per_page") int perPage
    );

    @GET("https://api.pexels.com/v1/search")
    Call<WallpaperData> getCategory(
            @Header("Authorization") String credientials,
            @Query("page") int pageCount,
            @Query("per_page") int perPage,
            @Query("query") String searchQuary
    );
}

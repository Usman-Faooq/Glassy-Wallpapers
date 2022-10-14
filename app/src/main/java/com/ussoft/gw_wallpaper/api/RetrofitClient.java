package com.ussoft.gw_wallpaper.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    static String BASE_URL = "https://api.pexels.com/v1/";
    static RetrofitClient retrofitClient;
    static Retrofit retrofit;
    static Retrofit searchRetrofit;

    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    public RetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (retrofitClient == null){
            retrofitClient =new RetrofitClient();
        }
        return retrofitClient;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}

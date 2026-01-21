package com.example.capyshop.common.retrofit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// Lớp này dùng để tạo một instance duy nhất (singleton) của Retrofit.
public class RetrofitClient {
    private static Retrofit instance;

    public static Retrofit getInstance(String baseUrl) {
        if (instance == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            instance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Chuyển đổi JSON sang đối tượng Java
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // Tích hợp với RxJava
                    .build();
        }
        return instance;
    }
}
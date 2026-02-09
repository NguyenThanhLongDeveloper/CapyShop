package com.example.capyshop.common.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Lớp này dùng để tạo một instance duy nhất (singleton) của Retrofit.
public class RetrofitClientThongBao {
    private static Retrofit instance;

    public static Retrofit getInstance(OkHttpClient okHttpClient) {
        if (instance == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            instance = new Retrofit.Builder()
                    .baseUrl("https://fcm.googleapis.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Chuyển đổi JSON sang đối tượng Java
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // Tích hợp với RxJava
                    .build();
        }
        return instance;
    }
}

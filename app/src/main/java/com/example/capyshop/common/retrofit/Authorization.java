package com.example.capyshop.common.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Authorization implements Interceptor {
    private String accessTokenSend;

    public Authorization(String accessTokenSend) {
        this.accessTokenSend = accessTokenSend;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder()
                .header("Authorization", "Bearer " + accessTokenSend)
                .method(request.method(), request.body());
        Request newRequest = builder.build();
        return chain.proceed(newRequest);

    }
}

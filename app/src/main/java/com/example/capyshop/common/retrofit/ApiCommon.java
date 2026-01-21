package com.example.capyshop.common.retrofit;

import com.example.capyshop.common.taihinhanh.TaiHinhAnhModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiCommon {
    // API Upload ảnh dùng chung cho toàn hệ thống
    @Multipart
    @POST("common/images/tai_hinhanh.php")
    Observable<TaiHinhAnhModel> taiAnhLen(
            @Part MultipartBody.Part file,
            @Part("type") okhttp3.RequestBody type);
}

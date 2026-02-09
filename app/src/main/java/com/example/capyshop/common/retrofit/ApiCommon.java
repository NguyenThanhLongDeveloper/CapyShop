package com.example.capyshop.common.retrofit;

import com.example.capyshop.common.nguoidung.NguoiDungModel;
import com.example.capyshop.common.taihinhanh.TaiHinhAnhModel;
import com.example.capyshop.common.thongbao.GuiThongBao;
import com.example.capyshop.common.thongbao.NhanThongBao;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiCommon {
    // API Upload ảnh dùng chung cho toàn hệ thống
    @Multipart
    @POST("common/images/common_tai_hinhanh.php")
    Observable<TaiHinhAnhModel> taiAnhLen(
            @Part MultipartBody.Part file,
            @Part("type") okhttp3.RequestBody type);

    @POST("common/token/common_capnhap_token.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> capNhapToken(
            @Field("manguoidung") int MaNguoiDung,
            @Field("token") String token);

    @POST("common/token/common_xoa_token.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> xoaToken(
            @Field("manguoidung") int MaNguoiDung);

    @POST("common/token/common_lay_token.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> layToken(
            @Field("vaitro") String vaiTro);

    @Headers({
            "Content-Type: application/json",
            //"Authorization: Bearer ya29.c.c0AZ4bNpaR6RWL0QSx46sSidnVO-P_UxIgOu_6ogmavcU0NIxkS3e8N5O4C-8io3Ry-EYHSq_K_jbUOR09Bm8IXG7jXoA9nF9y8leDsBMK8u6Ex6lQ1YW5dv1ZBJOpvR_YDp0bzdzd1yrcg4EX-4uze4asb7pGrYoA0pi7S3fjoWkdomXWauLQ7AwAXHz9kXCbZhDrGXnCYc8-xDwYD_9QUs8uVgkLU6fVQBUCAfYmmACsONinpLGm38KJkL1Bvq1sBM7q5_SUv0NzGkqk9ElLKe0iS-aosaE1Dy15QrmApcNdMKJhapgXHGTMConG1avcklVyvZzYfvd_LfFhjocpX0pH6ugA_ye90DhTzItmx1POnJLbQ-590cWq4wT387ChbsXtetfcIc72k3ssp88sor3YcgBFO4M-uv6St1hMiRM8zlj3855fQ5qIFe1aIbnwW8um2Wmpwt9xvOznp2mO5ys_tVzup8Zk3sJVqmXuuYwb73cBhaZ3h_hQXOvpI1f0M6lOUqmkJfQoJuQXJfnzQ22aM-ZQSZ4jyJ0-4WfmkU_r8bQ8xbW6o11k3Fe3cY6d_M63oMvadQkj6e1FjIOuqbXsm3eVcqWkcxj_85fsjn0RdVgrMgyqzo-nFgj5bw6abh6h1qahtpgFlq6Wp3JnX_SmpJu6WBjM2lyRmpqhXUzteJMRQk0pR8oXgm4moX-IaW_0OOwb2c6-S-S4WSf9mc7315y7YIw43dWp0y9l-sFy3oor_0J_lBJkJ_Xcf2-FR9FUxddvc1jpFdswxYs6mUWsQxRVF15lzBlU8VfIRuFycz6F-xxU7MchOOpV5YZyqmef_maFoqWX6VFRh8JOzrBBohw_YI8FXt2bW2UYpbSe1QpinfZmR4Rkgm0a6V_shbcFt6h1RoVcoxxnYyJnrBMn3VltVyoOcB2iwS6Rsyrhgm-ujp4UxJ_gj7W31JQyuj0Sj-kXlcXBnr3j2J1RhYtotu527aaV9o59MZ9859ohWRlldnyBzf6"
    })
    @POST("projects/capyshop-5d799/messages:send")
    Observable<NhanThongBao> guiNhanThongBao(@Body GuiThongBao guiThongBao);
}

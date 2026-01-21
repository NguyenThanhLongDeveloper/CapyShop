package com.example.capyshop.common.retrofit;

import com.example.capyshop.common.danhmuc.DanhMucModel;
import com.example.capyshop.common.donhang.DonHangModel;
import com.example.capyshop.common.donvivanchuyen.DonViVanChuyenModel;
import com.example.capyshop.common.nguoidung.NguoiDungModel;
import com.example.capyshop.common.phuongthucthanhtoan.PhuongThucThanhToanModel;
import com.example.capyshop.common.quangcao.QuangCaoModel;
import com.example.capyshop.common.sanpham.SanPhamModel;
import com.example.capyshop.user.giohang.UserGioHangModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUser {

        // Lấy danh sách thông tin quảng cáo
        @GET("user/main/user_lay_quangcao.php")
        Observable<QuangCaoModel> layQuangCao();

        // Lấy danh sách các danh mục sản phẩm
        @GET("user/main/user_lay_danhmuc.php")
        Observable<DanhMucModel> layDanhMuc();

        // Lấy danh sách sản phẩm mới được cập nhật
        @GET("user/main/user_lay_sanphammoi.php")
        Observable<SanPhamModel> laySanPhamMoi();

        // Lấy danh sách sản phẩm theo danh mục
        @POST("user/sanpham/user_lay_sanpham.php")
        @FormUrlEncoded
        Observable<SanPhamModel> laySanPham(
                @Field("trang") int trang,
                @Field("madanhmuc") int maDanhMuc);

        // Lấy thông tin chi tiết của sản phẩm
        @FormUrlEncoded
        @POST("user/chitietsanpham/user_lay_chitietsanpham.php")
        Observable<SanPhamModel> layChiTietSanPham(
                @Field("masanpham") int maSanPham);

        // Tìm kiếm sản phẩm theo từ khóa tên sản phẩm
        @POST("user/timkiemsanpham/user_timkiemsanpham.php")
        @FormUrlEncoded
        Observable<SanPhamModel> timKiemSanPham(
                @Field("timkiemsanpham") String timKiemSanPham);

        // Tạo tài khoản mới cho người dùng
        @POST("user/dangki/user_dangki.php")
        @FormUrlEncoded
        Observable<NguoiDungModel> dangKi(
                @Field("email") String email,
                @Field("sodienthoai") String soDienThoai,
                @Field("hotennguoidung") String tenNguoiDung,
                @Field("matkhau") String matKhau);

        // đang nhập thông tin của tài khoản người dùng
        @POST("user/dangnhap/user_dangnhap.php")
        @FormUrlEncoded
        Observable<NguoiDungModel> dangNhap(
                @Field("email") String email,
                @Field("matkhau") String matKhau);

        // Gửi yêu cầu quên mật khẩu qua email
        @POST("user/quenmatkhau/user_quenmatkhau.php")
        @FormUrlEncoded
        Observable<NguoiDungModel> quenMatKhau(
                @Field("email") String email);

        // Cập nhật thông tin hồ sơ
        @POST("user/thongtincanhan/user_capnhap_thongtincanhan.php")
        @FormUrlEncoded
        Observable<NguoiDungModel> capNhapThongTinCaNhan(
                @Field("manguoidung") int maNguoiDung,
                @Field("hovaten") String hoVaTen,
                @Field("sodienthoai") String soDienThoai,
                @Field("diachi") String diaChi,
                @Field("hinhanh") String hinhAnh);

        // thêm sản phẩm vào giỏ hàng trên
        @POST("user/chitietsanpham/user_them_giohang.php")
        @FormUrlEncoded
        Observable<UserGioHangModel> themGioHang(
                @Field("manguoidung") int maTaiKhoan,
                @Field("masanpham") int maSanPham,
                @Field("soluong") int soLuong,
                @Field("thuoctinh") String thuocTinh);

        // Lấy toàn bộ danh sách sản phẩm đang có trong giỏ hàng
        @POST("user/giohang/user_lay_giohang.php")
        @FormUrlEncoded
        Observable<UserGioHangModel> xemGioHang(
                @Field("manguoidung") int maNguoiDung);

        // Thay đổi số lượng của sản phẩm trong giỏ hàng
        @POST("user/giohang/user_capnhap_soluonggiohang.php")
        @FormUrlEncoded
        Observable<UserGioHangModel> capNhatGioHang(
                @Field("manguoidung") int maNguoiDung,
                @Field("magiohang") int maGioHang,
                @Field("soluong") int soLuong);

        // xoá sản phẩm khỏi giỏ hàng
        @POST("user/giohang/user_xoa_sanphamgiohang.php")
        @FormUrlEncoded
        Observable<UserGioHangModel> xoaGioHang(
                @Field("manguoidung") int maNguoiDung,
                @Field("magiohang") int maGioHang);

        // Lấy danh sách các đơn vị vận chuyển khả dụng
        @GET("user/dathang/user_lay_donvivanchuyen.php")
        Observable<DonViVanChuyenModel> layDonViVanChuyen();

        // Lấy các phương thức thanh toán
        @GET("user/dathang/user_lay_phuongthucthanhtoan.php")
        Observable<PhuongThucThanhToanModel> layPhuongThucThanhToan();

        // Gửi dữ liệu đơn hàng và chi tiết các sản phẩm đặt hàng lên db
        @POST("user/dathang/user_them_donhang.php")
        @FormUrlEncoded
        Observable<DonHangModel> guiDonHang(
                @Field("manguoidung") int maNguoiDung,
                @Field("maphuongthucthanhtoan") int maPhuongThucThanhToan,
                @Field("madonvivanchuyen") int maDonViVanChuyen,
                @Field("hovatennguoidung") String hoVaTenNguoiDung,
                @Field("sodienthoai") String soDienThoai,
                @Field("diachi") String diaChi,
                @Field("tongtien") String tongTien,
                @Field("tongsoluong") int tongSoLuong,
                @Field("chitietdonhang") String chiTietDonHang // là chuỗi JSON chứa mảng sản phẩm
        );

        // Truy vấn lịch sử đơn hàng theo trạng thái
        @POST("user/donhang/user_lay_donhang.php")
        @FormUrlEncoded
        Observable<DonHangModel> layDonHang(
                        @Field("manguoidung") int maNguoiDung,
                        @Field("trangthai") String trangThai);
}